(ns fiis-api.components.database
  (:require [com.stuartsierra.component :as component]
            [next.jdbc.connection :as connection])

  (:import (com.mchange.v2.c3p0 ComboPooledDataSource)))

(defrecord Database [config next-component ds]
  component/Lifecycle

  (start [component]
    (let [config (:config component)
          host (get-in config [:data :database-host])
          user (get-in config [:data :database-user])
          database (get-in config [:data :database-name])
          password (get-in config [:data :database-password])
          db-component (connection/component ComboPooledDataSource {:dbtype "postgresql"
                                                                    :dbname database
                                                                    :host host
                                                                    :user user
                                                                    :password password})]
      (assoc component :next-component db-component)
      (assoc component :ds (component/start db-component))))

  (stop [component]
    (component/stop (:next-component component))))

(defn new-database-component
  []
  (map->Database {}))
