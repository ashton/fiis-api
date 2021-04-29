(ns fiis-api.components.server
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]))

(defrecord HttpServer [options handler server]
  component/Lifecycle

  (start [component]
    (if (:server component)
      component
      (let [options (-> options (assoc :join? false))
            app (:app component)
            handler (atom (delay (:handler app)))
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
