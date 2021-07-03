(ns fiis-api.db.postgres.historical-data
  (:require [fiis-api.adapters.historical-data :as historical-data.adapters]
            [fiis-api.models.historical-data :as model]
            [fiis-api.schemata.postgres.historical-data :as schema]
            [honey.sql :refer [format] :rename {format build}]
            [honey.sql.helpers :as sql]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as result-set]
            [schema.core :as s]))

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

(s/defn explore-funds :- [model/FundExplorerItem]
  [db]
  (let [execute! (partial jdbc/execute! (db))]
    (-> (sql/select-distinct-on [:code] :code :last_price :date :dy :p_vp)
        (sql/from :historical_data)
        (sql/order-by [:code] [:date :desc])
        build
        (execute! {:builder-fn result-set/as-unqualified-lower-maps})
        (#(map historical-data.adapters/explore-db->explore-model %)))))
