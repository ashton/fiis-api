(ns fiis-api.schemata.postgres.funds
  (:require [schema.core :as s]
            [fiis-api.models.funds :as model]))

(s/defschema Fund model/fund-skeleton)

(s/defschema FundRevenue model/fund-revenue-skeleton)
