(ns fiis-api.db.postgres.funds
  (:require [honeysql.format :refer [format] :rename {format build}]
            [honeysql.helpers :as sql]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as result-set]
            [schema.core :as s]
            [fiis-api.schemata.out.funds :refer [Fund]]
            [fiis-api.adapters.funds :as adapter]))

(s/defn list-all :- [Fund]
  [db]
  (let [execute! (partial jdbc/execute! (db))
        result (-> (sql/select :name :code :dy)
                   (sql/from :funds)
                   build
                   (execute! {:builder-fn result-set/as-unqualified-lower-maps})
                   (doto println))]
    (map adapter/db->internal result)))
