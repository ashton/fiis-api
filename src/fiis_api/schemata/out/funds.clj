(ns fiis-api.schemata.out.funds
  (:require [schema.core :as s]))

(def Fund {:code s/Str
           :name s/Str
           (s/optional-key :document) s/Str
           (s/optional-key :dy) s/Num})
