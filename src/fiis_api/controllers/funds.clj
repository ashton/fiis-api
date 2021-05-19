(ns fiis-api.controllers.funds
  (:require [fiis-api.db.postgres.funds :as database]
            [fiis-api.schemata.in.funds :as s.in]
            [fiis-api.models.funds :as model]
            [schema.core :as s]))

(s/defn list-all-funds [db] :- [model/Fund]
  (database/list-all db))

(s/defn create-fund :- s/Bool
  [fund :- s.in/CreateFund
   db]
  (database/create fund db))

(s/defn update-fund :- s/Bool
  [code :- s/Str
   data :- s.in/UpdateFund
   db]
  (database/update code data db))

(s/defn set-fund-revenues :- s/Bool
  [code :- s/Str
   data :- [s.in/FundRevenue]
   db]
  (database/set-revenues code data db))
