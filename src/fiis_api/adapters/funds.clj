(ns fiis-api.adapters.funds
  (:require [schema.core :as s]
            [fiis-api.schemata.out.funds :as schema.out]
            [fiis-api.models.funds :as schema.model]))

(s/defn model->external :- schema.out/Fund
  [fund] :- schema.model/Fund
  (assoc fund :quota_amount (:quota-amount fund)))
