(ns fiis-api.service
  (:require [reitit.ring :as ring]
            [reitit.coercion.schema :as schema]
            [reitit.ring.coercion :as coercion]
            [fiis-api.controllers.funds :as controller]
            [fiis-api.schemata.out.funds :as s.out]))

(defn list-funds
  [{deps :deps}]
  (let [database (:database deps)
        result   (controller/list-all-funds (:ds database))]
    {:status 200
     :body result}))

(defn- wrap-deps
  "add system dependencies to the request"
  [handler deps]
  (fn [req] (handler (assoc req :deps deps))))

(defn new-app
  [deps]
  (ring/ring-handler
   (ring/router ["/api"
                 ["/funds" {:name ::funds-list
                            :get list-funds
                            :response [s.out/Fund]}]]
                {:data {:middleware [[wrap-deps deps]]
                        :coercion schema/coercion}})))
