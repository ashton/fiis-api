(ns fiis-api.service
  (:require [fiis-api.controllers.funds :as controller]
            [fiis-api.schemata.out.funds :as s.out]
            [fiis-api.schemata.in.funds :as s.in]
            [reitit.coercion.schema :as schema]
            [reitit.ring :as ring]
            [reitit.ring.coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [ring.middleware.reload]
            [muuntaja.core :as m]))

(defn list-funds
  [{deps :deps}]
  (let [database (:database deps)
        result   (controller/list-all-funds (:ds database))]
    {:status 200
     :body result}))

(defn create-fund
  [{deps :deps body :body-params}]
  (let [database (:database deps)]
    (controller/create-fund body (:ds database))
    {:status 201}))

(defn- wrap-deps
  "add system dependencies to the request"
  [handler deps]
  (fn [req] (handler (assoc req :deps deps))))

(defn new-app
  [deps]
  (ring/ring-handler
   (ring/router ["/api"
                 ["/funds" {:get {:name ::funds-list
                                  :handler list-funds
                                  :responses {200 {:body [s.out/Fund]}}}
                            :post {:name ::funds-create
                                   :parameters {:body s.in/CreateFund}
                                   :handler create-fund
                                   :responses {200 {:body nil}}}}]]
                {:data {:muuntaja m/instance
                        :coercion schema/coercion
                        :middleware [muuntaja/format-middleware
                                     ring.middleware.reload/wrap-reload
                                     reitit.ring.coercion/coerce-request-middleware
                                     reitit.ring.coercion/coerce-response-middleware
                                     [wrap-deps deps]]}})))
