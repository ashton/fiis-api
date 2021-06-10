(ns fiis-api.schemata.postgres.funds
  (:require [clojure.set :refer [rename-keys]]
            [fiis-api.models.funds :as model]
            [schema.core :as s]))

(s/defschema Fund
  (rename-keys model/fund-skeleton {:quota-amount :quota_amount}))
