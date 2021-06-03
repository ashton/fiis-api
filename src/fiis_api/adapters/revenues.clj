(ns fiis-api.adapters.revenues
  (:require [schema.core :as s]
            [fiis-api.models.revenues :as model]
            [fiis-api.schemata.in.revenues :as schema.in]
            [fiis-api.schemata.postgres.revenues :as schema.db]
            [java-time :as t]))

(s/defn external->model :- model/FundRevenue
  [revenue :- schema.in/CreateFundRevenue]
  (-> revenue
      (update :date t/local-date)
      (assoc :base-price (:base_price revenue))
      (dissoc :base_price)))

(s/defn model->postgres :- schema.db/Revenue
  [revenue :- model/FundRevenue]
  (-> revenue
      (update :date t/sql-date)
      (assoc :base_price (:base-price revenue))
      (dissoc :base-price)))
