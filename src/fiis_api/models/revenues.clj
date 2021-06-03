(ns fiis-api.models.revenues
  (:require [schema.core :as s]))

(def revenue-skeleton
  {:code s/Str
   :date java.time.LocalDate
   :base-price BigDecimal
   :dy BigDecimal
   :value BigDecimal})

(s/defschema FundRevenue revenue-skeleton)
