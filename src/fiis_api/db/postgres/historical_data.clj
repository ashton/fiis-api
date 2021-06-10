(ns fiis-api.db.postgres.historical-data
  (:require [schema.core :as s]
            [honeysql.format :refer [format] :rename {format build}]
            [honeysql.helpers :as sql]
            [next.jdbc :as jdbc]
            [fiis-api.schemata.postgres.historical-data :as schema]))

(s/defn create-historical-data :- s/Bool
  [data :- schema/HistoricalData
   db]
  (let [execute! (partial jdbc/execute! (db))
        vals (juxt :code :p_vp :last_price :date)]
    (-> (sql/insert-into :historical_data)
        (sql/columns :code :p_vp :last_price :date)
        (sql/values [(vals data)])
        build
        execute!
        (doto println)
        first
        :next.jdbc/update-count
        (> 0))))
