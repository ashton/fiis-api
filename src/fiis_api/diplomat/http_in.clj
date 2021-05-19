(ns fiis-api.diplomat.http-in
  (:require  [schema.core :as s]
             [fiis-api.controllers.funds :as controller]
             [fiis-api.adapters.funds :as adapter]
             [fiis-api.schemata.out.funds :as schema.out]))

(s/defn list-funds :- [schema.out/Fund]
  [{deps :deps}]
  (let [database (:database deps)
        result   (controller/list-all-funds (:ds database))]
    {:status 200
     :body (map adapter/model->external result)}))

(defn create-fund
  [{deps :deps body :body-params}]
  (let [database (:database deps)]
    (controller/create-fund body (:ds database))
    {:status 201}))

(defn update-fund
  [{deps :deps body :body-params path :path-params}]
  (let [database (:database deps)]
    (controller/update-fund (:code path) body (:ds database))
    {:status 204}))

(defn set-fund-revenues
  [{deps :deps body :body-params path :path-params}]
  (let [database (:database deps)]
    (controller/set-fund-revenues (:code path) body (:ds database))
    {:status 204}))
