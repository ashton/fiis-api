(ns fiis-api.schemata.postgres.historical-data
  (:require [clojure.set :refer [rename-keys]]
            [fiis-api.models.historical-data :as model]
            [schema-tools.core :as st]
            [schema.core :as s]))

(s/defschema HistoricalData
  (-> model/fund-history-skeleton
      (rename-keys {:p-vp :p_vp :last-price :last_price})
      (st/assoc :date java.sql.Date)))
