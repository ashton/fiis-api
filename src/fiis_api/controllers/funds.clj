(ns fiis-api.controllers.funds
  (:require [fiis-api.db.postgres.funds :as database]
            [fiis-api.schemata.in.funds :as s.in]
            [schema.core :as s]))

(defn list-all-funds [db]
  (database/list-all db))

(s/defn create-fund
  [fund :- s.in/CreateFund
   db]
  (database/create fund db))
