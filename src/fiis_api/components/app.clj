(ns fiis-api.components.app
  (:require [com.stuartsierra.component :as component]))

(defrecord App [handler]
  component/Lifecycle

  (start [component] (assoc component :app handler))
  (stop [component] (assoc component :app nil)))

(defn new-app [handler]
  (->App handler))
