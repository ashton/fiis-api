(ns fiis-api.controllers.historical-data
  (:require [fiis-api.adapters.historical-data :as historical-data.adapters]
            [fiis-api.db.postgres.historical-data :as database]
            [fiis-api.models.historical-data :as model]
            [schema.core :as s]))

(s/defn create-historical-data :- model/HistoricalData
  [historical-data :- model/HistoricalData
   db]
  (let [db-data (historical-data.adapters/model->db historical-data)]
    (when (database/create-historical-data db-data db)
      historical-data)))

(s/defn explorer :- [model/FundExplorerItem]
  [db]
  (database/explore-funds db))
