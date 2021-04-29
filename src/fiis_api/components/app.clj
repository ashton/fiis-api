(ns fiis-api.components.app
  (:require [com.stuartsierra.component :as component]))

(defrecord App [app]
  component/Lifecycle

  (start [component]
    (assoc component :handler (app (select-keys component (keys (dissoc component :app :config))))))
  (stop [component] (assoc component :app nil)))

(defn new-app [app]
  (->App app))
