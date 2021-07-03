(ns fiis-api.db.postgres.historical-data
            [honey.sql :refer [format] :rename {format build}]
            [honey.sql.helpers :as sql]
            [next.jdbc :as jdbc]
            [fiis-api.schemata.postgres.historical-data :as schema]))

(s/defn create-historical-data :- s/Bool
  [data :- schema/HistoricalData
   db]
  (let [execute! (partial jdbc/execute! (db))
        vals (juxt :code :p_vp :last_price :dy :date)]
    (-> (sql/insert-into :historical_data)
        (sql/columns :code :p_vp :last_price :dy :date)
        (sql/values [(vals data)])
        build
        execute!
        first
        :next.jdbc/update-count
        (> 0))))
