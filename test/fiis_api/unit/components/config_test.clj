(ns fiis-api.unit.components.config-test
  (:require [fiis-api.components.config :refer [new-config-component]]
            [com.stuartsierra.component :as c]
            [clojure.test :refer [deftest testing is]]))

(deftest config-component-test
  (testing "component should be constructed"
    (is (seq (new-config-component {}))))

  (testing "should consider only keys starting with 'fii_api_'"
    (let [component (new-config-component {"fii_api_valid_key" "valid value" "invalid key" "invalid value"})
          config (-> (c/start component)
                     :data)]
      (is (= "valid value"
             (-> config
                 vals
                 last)))))

  (testing "should remove config prefix"
    (let [component (new-config-component {"fii_api_key" "valid value"})
          config (-> (c/start component)
                     :data)]
      (is (= "valid value"
             (:key config)))))

  (testing "should turn config to kebab-case"
    (let [component (new-config-component {"fii_api_valid_key" "valid value" "invalid key" "invalid value"})
          config (-> (c/start component)
                     :data)]
      (is (contains? config :valid-key))))

  (testing "should lowercase all keys"
    (let [component (new-config-component {"FII_API_VALID_KEY" "valid value" "invalid key" "invalid value"})
          config (-> (c/start component)
                     :data)]
      (is (contains? config :valid-key))
      (is (not (contains? config :invalid-key)))))

  (testing "should remove data on stop"
    (let [component (new-config-component {"fii_api_valid_key" "valid value" "invalid key" "invalid value"})
          config (-> (c/start component)
                     c/stop)]
      (is (= nil
             (:data config))))))
