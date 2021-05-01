(ns fiis-api.components.server
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [fiis-api.service :refer [new-app]]))

(defn- get-system-dependencies
  "get all system dependencies to be injected inside the application"
  [component]
  (select-keys component (keys (dissoc component :options :handler :server))))

(defrecord HttpServer [options handler server]
  component/Lifecycle

  (start [component]
    (if (:server component)
      component
      (let [options (-> options (assoc :join? false))
            handler (-> component
                        get-system-dependencies
                        new-app
                        delay
                        atom)
            server (jetty/run-jetty @@handler options)]
        (assoc component
          :server server
          :handler handler))))

  (stop [component]
    (if-let [server (:server component)]
      (do (.stop server)
          (.join server)
          (dissoc component :server :handler)))))

(defn http-server [options]
  (map->HttpServer {:options options}))
