(ns fiis-api.components.systems
  (:require [com.stuartsierra.component :as component]
            [fiis-api.components.config :refer [new-config-component]]
            [fiis-api.components.database :refer [new-database-component]]
            [fiis-api.components.server :refer [http-server]]
            [fiis-api.components.app :refer [new-app]]
            [fiis-api.handler :refer [app]]))

(def prd-system (component/system-map
                 :config (new-config-component (System/getenv))
                 :database (component/using (new-database-component) [:config])
                 :app (component/using (new-app app) [:database])
                 :server (component/using (http-server {:port 8080}) [:app])))

(def dev-system (component/system-map
                 :config (new-config-component {"fii_api_database_name" "fiis"
                                                "fii_api_database_host" "localhost"
                                                "fii_api_database_user" "api"
                                                "fii_api_database_password" "pass"})
                 :database (component/using (new-database-component) [:config])
                 :app (component/using (new-app app) [:database])
                 :server (component/using (http-server {:port 3000}) [:app])))

(def systems-map {:dev dev-system
                  :prod prd-system})
