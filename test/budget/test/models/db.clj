(ns budget.test.models.db
  (:use clojure.test
        ring.mock.request
        budget.models.db))

(def test-db {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname "transactions-test.sq3"})

(use-fixtures :each
  (fn [test]
    (binding [*db* test-db]
      (create-transactions-table)
      (test)
      (drop-transactions-table))))


(deftest test-make-transaction
  (testing "inserting a transaction"
    (is (:last_insert_rowid() (make-transaction {:amount 5 :memo "test" :person "Nate"})))))

(deftest test-total
  (testing "total of transactions"
    (let [transaction {:amount 10 :memo "add" :person "Nate"}
          transaction2 {:amount -5 :memo "subtract" :person "Mollie"}]
      (make-transaction transaction)
      (make-transaction transaction2)
      (is (= 5 (total))))))
