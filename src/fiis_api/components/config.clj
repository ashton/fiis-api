(ns fiis-api.components.config
  (:require [clojure.string :as s]
            [com.stuartsierra.component :as component]))

(defn- normalize-map
  "normalize keys in a map lowercasing them and transforming into kebab-case"
  [subject]
  (into {} (map
            (fn [[key val]]
              (let [key (-> key
                            s/lower-case
                            (s/replace #"[ _]" "-")
                            (s/replace "fii-api-", "")
                            keyword)]
                [key val]))
            subject)))

(defrecord Config [config data]
  component/Lifecycle

  (start [component]
    (->> (:config component)
         (filter (fn [[key _]]
                   (-> key
                       (s/lower-case)
                       (s/starts-with? "fii_api"))))
         (normalize-map)
         (assoc component :data)))

  (stop [component]
    (assoc component :data nil)))

(defn new-config-component
  [config-map]
  (map->Config {:config config-map}))
