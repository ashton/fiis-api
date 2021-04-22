(ns fiis-api.components.database
  (:require [com.stuartsierra.component :as component]
            [next.jdbc.connection :as connection])

  (:import (com.mchange.v2.c3p0 ComboPooledDataSource)))

(defrecord Database [config next-component database]
  component/Lifecycle

  (start [component]
    (let [config (:config component)
          host (:database-host config)
          user (:database-user config)
          database (:database-name config)
          password (:database-password config)
          db-component (connection/component ComboPooledDataSource {:dbtype "postgresql"
                                                                    :dbname database
                                                                    :host host
                                                                    :user user
                                                                    :password password})]
      (assoc component :next-component db-component)
      (assoc component :database (component/start db-component))))

  (stop [component]
    (component/stop (:next-component component))))

(defn new-database-component
  []
  (map->Database {}))
