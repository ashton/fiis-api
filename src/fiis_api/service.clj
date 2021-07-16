(ns fiis-api.service
  (:require [clojure.string :as str]
            [fiis-api.diplomat.http-in :as http.in]
            [fiis-api.schemata.in.funds :as funds.schema.in]
            [fiis-api.schemata.out.funds :as funds.schema.out]
            [fiis-api.schemata.in.historical-data :as historical-data.schema.in]
            [fiis-api.schemata.out.historical-data :as historical-data.schema.out]
            [fiis-api.schemata.in.revenues :as revenues.schema.in]
            [muuntaja.core :as m]
            [reitit.coercion :as coercion]
            [reitit.coercion.schema :as schema]
            [reitit.ring :as ring]
            [reitit.ring.coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [ring.middleware.reload]
            [ring.middleware.cors :refer [wrap-cors]]
            [schema.core :as s]))

(defn- wrap-deps
  "add system dependencies to the request"
  [handler deps]
  (fn [req] (handler (assoc req :deps deps))))

(def muuntaja-instance (m/create
                        (-> m/default-options
                            (assoc-in [:formats "application/json" :decoder-opts]
                                      {:bigdecimals true
                                       :decode-key-fn #(-> %
                                                           str/lower-case
                                                           (str/replace "_" "-")
                                                           keyword)})
                            (assoc-in [:formats "application/json" :encoder-opts]
                                      {:encode-key-fn #(-> %
                                                           name
                                                           (str/replace "-" "_"))}))))

(defn new-app
  [deps]
  (ring/ring-handler
   (ring/router ["/api"
                 ["/funds" {:get {:name ::funds-list
                                  :handler http.in/list-funds
                                  :responses {200 {:body [funds.schema.out/Fund]}}}
                            :post {:name ::funds-create
                                   :parameters {:body funds.schema.in/CreateFund}
                                   :handler http.in/create-fund
                                   :responses {200 {:body nil}}}}]
                 ["/funds/history", {:post {:name ::fund-historical-data
                                            :parameters {:body historical-data.schema.in/HistoricalData}
                                            :responses {201 {:body historical-data.schema.out/HistoricalData}}
                                            :handler http.in/add-fund-historical-data}
                                     :conflicting true}]
                 ["/funds/explorer" {:get {:name ::funds-explorer
                                           :responses {200 {:body [historical-data.schema.out/FundExplorerItem]}}
                                           :handler http.in/funds-explorer}
                                     :conflicting true}]
                 ["/funds/:code" {:patch {:name ::update-fund
                                          :parameters {:body funds.schema.in/UpdateFund :path {:code s/Str}}
                                          :responses {204 {:body nil}}
                                          :handler http.in/update-fund}
                                  :conflicting true}]
                 ["/funds/:code/revenues" {:put {:name ::set-fund-revenues
                                                 :parameters {:body revenues.schema.in/CreateFundRevenueList
                                                              :path {:code s/Str}}
                                                 :responses {204 {:body nil}}
                                                 :handler http.in/set-fund-revenues}}]]

                {:data {:muuntaja muuntaja-instance
                        :coercion schema/coercion
                        :compile coercion/compile-request-coercers
                        :middleware [muuntaja/format-middleware
                                     ring.middleware.reload/wrap-reload
                                     reitit.ring.coercion/coerce-request-middleware
                                     reitit.ring.coercion/coerce-response-middleware
                                     [wrap-deps deps]
                                     [wrap-cors
                                      :access-control-allow-origin [#".*"]
                                      :access-control-allow-methods [:get :post :patch :put]]]}})))
