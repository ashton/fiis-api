(ns fiis-api.components.main
  (:require [com.stuartsierra.component :as component]
            [fiis-api.components.config :refer [new-config-component]]
            [fiis-api.components.database :refer [new-database-component]]))

(def prd-system (component/system-map
                 :config (new-config-component (System/getenv))
                 :database (component/using
                            (new-database-component)
                            [:database])))

(def dev-system (component/system-map
                 :config (new-config-component {:database-name "fiis"
                                                :database-host "localhost"
                                                :database-user "api"
                                                :database-password "pass"})
                 :database (component/using
                            (new-database-component)
                            [:database])))

(def systems-map {:dev dev-system
                  :prod prd-system})
