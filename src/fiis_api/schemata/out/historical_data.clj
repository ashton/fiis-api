(ns fiis-api.schemata.out.historical-data
  (:require [schema.core :as s]))

(def HistoricalData
  {:code s/Str
   :date s/Str
   :last_price BigDecimal
   (s/optional-key :dy) BigDecimal
   (s/optional-key :p_vp) BigDecimal})

(def FundExplorerItem {:code s/Str
                       :date s/Str
                       :dy BigDecimal
                       :last_price BigDecimal
                       :p_vp (s/maybe BigDecimal)})
