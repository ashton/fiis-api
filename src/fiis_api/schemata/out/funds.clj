(ns fiis-api.schemata.out.funds
  (:require [schema.core :as s]))

(def Fund {:code s/Str
           :name s/Str
           :document s/Str
           :dy s/Num})
