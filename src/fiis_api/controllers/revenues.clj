(ns fiis-api.controllers.revenues
  (:require [fiis-api.db.postgres.revenues :as database]
            [fiis-api.models.revenues :as schema.model]
            [fiis-api.adapters.revenues :as adapter]
            [schema.core :as s]))

(s/defn set-fund-revenues :- s/Bool
  [code :- s/Str
   data :- [schema.model/FundRevenue]
   db]
  (let [db-data (map adapter/model->postgres data)]
    (database/create-all code db-data db)))
