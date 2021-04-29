(ns fiis-api.adapters.funds
  (:require [schema.coerce :as s]
            [fiis-api.schemata.out.funds :as s.out]))

(def db->internal (s/coercer s.out/Fund s/json-coercion-matcher))
