(ns fiis-api.models.funds
  (:require [schema.core :as s]
            [schema-tools.core :as st]))

(def fund-skeleton
  {:code s/Str
   :name s/Str
   :dy (s/maybe BigDecimal)
   :document (s/maybe s/Str)
   :quota-amount (s/maybe s/Int)})

(s/defschema Fund fund-skeleton)

(def fund-revenue-skeleton
  {:code s/Str
   :date java.time.LocalDate
   :base_price BigDecimal
   :dy BigDecimal
   :value BigDecimal})

(s/defschema FundRevenue
  (st/assoc fund-revenue-skeleton
    :date Long))
