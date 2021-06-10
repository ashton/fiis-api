(ns fiis-api.schemata.out.historical-data
  (:require [schema.core :as s]))

(def HistoricalData
  {:code s/Str
   :date s/Str
   :last_price BigDecimal
   (s/optional-key :dy) BigDecimal
   (s/optional-key :p-vp) BigDecimal})
