(ns budget.test.handler
  (:use clojure.test
        ring.mock.request
        budget.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200)))))
