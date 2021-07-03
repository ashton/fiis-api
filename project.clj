(defproject fiis-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [metosin/reitit-core "0.5.13"]
                 [metosin/reitit-ring "0.5.13"]
                 [ring "1.9.3"]
                 [metosin/reitit-schema "0.5.13"]
                 [metosin/reitit-middleware "0.5.13"]
                 [metosin/reitit-swagger-ui "0.5.13"]
                 [ring/ring-jetty-adapter "1.8.2"]
                 [com.stuartsierra/component "1.0.0"]
                 [prismatic/schema "1.1.12"]
                 [metosin/schema-tools "0.12.3"]
                 [com.github.seancorfield/honeysql "2.0.0-rc3"]
                 [com.github.seancorfield/next.jdbc "1.1.646"]
                 [org.postgresql/postgresql "42.2.10"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3"]
                 [clojure.java-time "0.3.2"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler fiis-api.handler/app}
  :main fiis-api.server
  :profiles
  {:dev {:source-paths ["dev"]
         :dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
