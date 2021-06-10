(ns fiis-api.schemata.in.historical-data
  (:require [schema.core :as s]))

(def HistoricalData
  {:code s/Str
   :date s/Str
   :last-price BigDecimal
   (s/optional-key :dy) BigDecimal
   (s/optional-key :p-vp) BigDecimal})
