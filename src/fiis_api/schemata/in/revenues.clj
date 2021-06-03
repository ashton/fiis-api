(ns fiis-api.schemata.in.revenues
  (:require [schema.core :as s]
            [schema-tools.core :as st]
            [fiis-api.models.revenues :as model]))

(s/defschema CreateFundRevenue
  (-> model/revenue-skeleton
      (st/dissoc :code)
      (st/assoc :date s/Str)))

(s/defschema CreateFundRevenueList [CreateFundRevenue])
