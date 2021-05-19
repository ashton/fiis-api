(ns fiis-api.schemata.out.funds
  (:require [schema.core :as s]))

(def Fund {:code s/Str
           :name s/Str
           :document (s/maybe s/Str)
           :dy (s/maybe s/Num)
           :quota_amount (s/maybe s/Int)})
