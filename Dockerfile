FROM clojure:openjdk-11-lein AS builder

COPY . .

RUN lein uberjar

CMD ["java", "-cp", "target/fiis-api-0.1.0-SNAPSHOT-standalone.jar", "clojure.main", "-m", "fiis-api.server"]
