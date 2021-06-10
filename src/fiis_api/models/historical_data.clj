(ns fiis-api.models.historical-data
  (:require [schema.core :as s]))


(def fund-history-skeleton
  {:code s/Str
   :p-vp (s/maybe BigDecimal)
   :last-price BigDecimal
   :date java.time.LocalDate})

(s/defschema HistoricalData fund-history-skeleton)
