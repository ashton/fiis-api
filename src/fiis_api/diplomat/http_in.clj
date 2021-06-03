(ns fiis-api.diplomat.http-in
  (:require  [schema.core :as s]
             [fiis-api.controllers.funds :as funds.controller]
             [fiis-api.controllers.revenues :as revenues.controller]
             [fiis-api.adapters.funds :as funds.adapter]
             [fiis-api.adapters.revenues :as revenues.adapter]
             [fiis-api.schemata.out.funds :as schema.out]))

(s/defn list-funds :- [schema.out/Fund]
  [{deps :deps}]
  (let [database (:database deps)
        result   (funds.controller/list-all-funds (:ds database))]
    {:status 200
     :body (map funds.adapter/model->external result)}))

(defn create-fund
  [{deps :deps body :body-params}]
  (let [database (:database deps)]
    (funds.controller/create-fund body (:ds database))
    {:status 201}))

(defn update-fund
  [{deps :deps body :body-params path :path-params}]
  (let [database (:database deps)]
    (funds.controller/update-fund (:code path) body (:ds database))
    {:status 204}))

(defn set-fund-revenues
  [{deps :deps body :body-params path :path-params}]
  (let [database (:database deps)
        model-data (map revenues.adapter/external->model body)]
    (revenues.controller/set-fund-revenues (:code path) model-data (:ds database))
    {:status 204}))
