(ns fiis-api.models.historical-data
  (:require [schema.core :as s]))


(def fund-history-skeleton
  {:code s/Str
   :p-vp (s/maybe BigDecimal)
   :last-price BigDecimal
   :date java.time.LocalDate})

(s/defschema HistoricalData fund-history-skeleton)

(def fund-explorer-item-skeleton
  {:code s/Str
   :name s/Str
   :last-price BigDecimal
   :p-vp BigDecimal
   :dy BigDecimal})

(s/defschema FundExplorerItem fund-explorer-item-skeleton)
