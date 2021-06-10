(ns fiis-api.db.postgres.funds
  (:require [fiis-api.schemata.postgres.funds :as schema]
            [fiis-api.adapters.funds :as funds.adapters]
            [honeysql.format :refer [format] :rename {format build}]
            [honeysql.helpers :as sql]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as result-set]
            [next.jdbc.sql :as jdbc-sql]
            [schema.core :as s]))

(s/defn list-all :- [schema/Fund]
  [db]
  (let [execute! (partial jdbc/execute! (db))]
    (-> (sql/select :name :code :dy :document :quota_amount)
        (sql/from :funds)
        build
        (execute! {:builder-fn result-set/as-unqualified-lower-maps})
        (#(map funds.adapters/db->model %)))))

(s/defn create :- s/Bool
  [fund :- schema/Fund
   db]
  (let [execute! (partial jdbc/execute! (db))
        vals (juxt :name :code :document :dy)]
    (-> (sql/insert-into :funds)
        (sql/columns :name :code :document :dy)
        (sql/values [(vals fund)])
        build
        execute!
        first
        :next.jdbc/update-count
        (> 0))))

(s/defn update :- s/Bool
  [code :- s/Str
   data :- schema/Fund
   db]
  (let [update! (partial jdbc-sql/update! (db))]
    (update! :funds data {:code code})))
