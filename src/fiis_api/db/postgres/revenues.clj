(ns fiis-api.db.postgres.revenues
  (:require [schema.core :as s]
            [fiis-api.schemata.postgres.revenues :as schema]
            [next.jdbc.sql :as jdbc-sql]))

(s/defn create-all :- s/Bool
  [code :- s/Str
   revenues :- [schema/Revenue]
   db]
  (let [insert! (partial jdbc-sql/insert-multi! (db))
        get-other-values (juxt :base_price :date :dy :value)
        get-vals (fn [rev] (cons code (get-other-values rev)))
        values (map get-vals revenues)]
    (insert! :revenues [:code :base_price :date :dy :value] values)))
