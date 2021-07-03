(ns fiis-api.adapters.historical-data
  (:require [clojure.set :refer [rename-keys]]
            [fiis-api.models.historical-data :as schema.model]
            [fiis-api.schemata.in.historical-data :as schema.in]
            [fiis-api.schemata.out.historical-data :as schema.out]
            [fiis-api.schemata.postgres.historical-data :as schema.db]
            [java-time :as t]
            [schema.core :as s]))

(s/defn external->model :- schema.model/HistoricalData
  [history :- schema.in/HistoricalData]
  (-> history
      (rename-keys {:p_vp :p-vp :last_price :last-price})
      (update :date t/local-date)))

(s/defn model->external :- schema.out/HistoricalData
  [model :- schema.model/HistoricalData]
  (-> model
      (rename-keys {:p-vp :p_vp :last-price :last_price})
      (update :date t/format)))

(s/defn model->db :- schema.db/HistoricalData
  [model :- schema.model/HistoricalData]
  (-> model
      (rename-keys {:p-vp :p_vp :last-price :last_price})
      (update :date t/sql-date)))

(s/defn explore-db->explore-model :- schema.model/FundExplorerItem
  [fund :- schema.db/HistoricalData]
  (-> fund
      (rename-keys {:p_vp :p-vp :last_price :last-price})
      (update :date t/local-date)))

(s/defn explorer-model->external :- schema.out/FundExplorerItem
  [model :- schema.model/FundExplorerItem]
  (-> model
      (rename-keys {:p-vp :p_vp :last-price :last_price})
      (update :date #(t/format "yyyy-MM-dd" %))))
