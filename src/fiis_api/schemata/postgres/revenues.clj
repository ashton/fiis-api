(ns fiis-api.schemata.postgres.revenues
  (:require [schema.core :as s]))

(def revenue-skeleton
  {:code s/Str
   :base_price BigDecimal
   :dy BigDecimal
   :value BigDecimal
   :date java.sql.Date})

(s/defschema Revenue (-> revenue-skeleton
                         (update :base_price s/maybe)
                         (update :dy s/maybe)))
