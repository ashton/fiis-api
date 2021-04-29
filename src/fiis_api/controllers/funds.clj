(ns fiis-api.controllers.funds
  (:require [fiis-api.db.postgres.funds :as database]))

(defn list-all-funds [db]
  (database/list-all db))
