(ns fiis-api.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [fiis-api.service :refer [new-app]]))

(deftest test-app
  (testing "main route"
    (let [response (new-app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))

  (testing "not-found route"
    (let [response (new-app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
