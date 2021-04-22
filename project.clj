(defproject fiis-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]]
                 [honeysql "1.0.461"]
                 [com.github.seancorfield/next.jdbc "1.1.646"]
                 [org.postgresql/postgresql "42.2.10"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler fiis-api.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
