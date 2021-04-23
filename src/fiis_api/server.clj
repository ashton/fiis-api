(ns fiis-api.server
  (:require [com.stuartsierra.component :as component]
            [fiis-api.components.systems :refer [systems-map]]))

(defn -main []
  (let [env (get (System/getenv) "APP_ENV" "dev")
        system ((keyword env) systems-map)]
    (component/start system)))
