(ns fiis-api.schemata.in.revenues
  (:require [schema.core :as s]
            [schema-tools.core :as st]
            [fiis-api.models.revenues :as model]))

(s/defschema CreateFundRevenue
  (-> model/revenue-skeleton
      (st/dissoc :code)
      (st/assoc :date s/Str)
      (st/assoc (s/optional-key :dy) BigDecimal)
      (st/assoc (s/optional-key :base-price) BigDecimal)))

(s/defschema CreateFundRevenueList [CreateFundRevenue])
