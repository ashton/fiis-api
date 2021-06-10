(ns fiis-api.models.funds
  (:require [schema.core :as s]))

(def fund-skeleton
  {:code s/Str
   :name s/Str
   :dy (s/maybe BigDecimal)
   :document (s/maybe s/Str)
   :quota-amount (s/maybe s/Int)})

(s/defschema Fund fund-skeleton)
