(ns fiis-api.adapters.funds
  (:require [clojure.set :refer [rename-keys]]
            [fiis-api.models.funds :as schema.model]
            [fiis-api.schemata.out.funds :as schema.out]
            [fiis-api.schemata.postgres.funds :as schema.db]
            [schema.core :as s]))

(s/defn model->external :- schema.out/Fund
  [fund :- schema.model/Fund]
  (rename-keys fund {:quota-amount :quota_amount}))

(s/defn db->model :- schema.model/Fund
  [fund :- schema.db/Fund]
  (rename-keys fund {:quota_amount :quota-amount}))
