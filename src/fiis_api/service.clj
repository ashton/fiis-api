(ns fiis-api.service
  (:require [fiis-api.controllers.funds :as controller]
            [compojure.core :refer [defroutes context GET]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

(defn list-funds
  [{deps :deps}]
  (let [database (:database deps)
        result   (controller/list-all-funds (:ds database))]
    {:status 200
     :body result}))

(defroutes app-routes
  (context "/api" []
    (context "/funds" []
      (GET "/" request (list-funds request)))))

(defn- assoc-deps-middleware
  [handler deps]
  (fn [req] (handler (assoc req :deps deps))))

(defn app
  [deps]
  (-> app-routes
      (assoc-deps-middleware deps)
      (wrap-reload #'app-routes)
      (wrap-json-body {:keywords? true :bigdecimals true})
      wrap-json-response
      (wrap-defaults api-defaults)))
