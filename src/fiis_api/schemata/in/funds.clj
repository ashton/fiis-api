(ns fiis-api.schemata.in.funds
  (:require [schema.core :as s]))

(def CreateFund
  {:name s/Str
   :code s/Str})
