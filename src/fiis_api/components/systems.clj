(ns fiis-api.components.systems
  (:require [com.stuartsierra.component :as component]
            [fiis-api.components.config :refer [new-config-component]]
            [fiis-api.components.database :refer [new-database-component]]
            [fiis-api.components.server :refer [http-server]]))

(def  prd-system (component/system-map
                   :config (new-config-component (System/getenv))
                   :database (component/using (new-database-component) [:config])
                   :server (component/using (http-server {:port 8080}) [:database])))

(def dev-system (component/system-map
                  :config (new-config-component {"fii_api_database_name" "fiis"
                                                 "fii_api_database_host" "localhost"
                                                 "fii_api_database_user" "fiis_api"
                                                 "fii_api_database_password" "password"})
                  :database (component/using (new-database-component) [:config])
                  :server (component/using (http-server {:port 3000}) [:database])))

(def systems-map {:dev dev-system
                  :prod prd-system})
