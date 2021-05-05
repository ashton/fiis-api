(ns fiis-api.db.postgres.funds
  (:require [honeysql.format :refer [format] :rename {format build}]
            [honeysql.helpers :as sql]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as result-set]
            [schema.core :as s]
            [fiis-api.schemata.out.funds :as s.out]
            [fiis-api.schemata.in.funds :as s.in]
            [fiis-api.adapters.funds :as adapter]))

(s/defn list-all :- [s.out/Fund]
  [db]
  (let [execute! (partial jdbc/execute! (db))
        result (-> (sql/select :name :code :dy)
                   (sql/from :funds)
                   build
                   (execute! {:builder-fn result-set/as-unqualified-lower-maps}))]
    (map adapter/db->internal result)))

(s/defn create :- s/Bool
  [fund :- s.in/CreateFund
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
