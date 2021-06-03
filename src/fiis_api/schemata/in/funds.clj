(ns fiis-api.schemata.in.funds
  (:require [schema.core :as s]
            [schema-tools.core :as st]))

(def CreateFund
  {:name s/Str
   :code s/Str})

(def UpdateFund
  (st/optional-keys {:name  s/Str
                     :code s/Str
                     :document s/Str
                     :dy   BigDecimal}))
