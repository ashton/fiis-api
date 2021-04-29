(ns fiis-api.db.postgres.funds
  (:require [honeysql.format :refer [format] :rename {format build}]
            [honeysql.helpers :as sql]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as result-set]))

(defn list-all
  [db]
  (let [execute! (partial jdbc/execute! (db))]
    (-> (sql/select :name :code :dy)
        (sql/from :funds)
        build
        (execute! {:builder-fn result-set/as-unqualified-lower-maps}))))
