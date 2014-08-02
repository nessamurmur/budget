(ns budget.models.db
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager))

(def ^:dynamic *db* {:classname "org.sqlite.JDBC"
                     :subprotocol "sqlite"
                     :subname "transactions.sq3"})

(defn create-transactions-table []
  (sql/with-connection *db*
    (sql/create-table
     :transactions
     [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
     [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
     [:memo "TEXT"]
     [:amount "INTEGER"]
     [:person "TEXT"])
    (sql/do-commands "CREATE INDEX timestamp_index ON transactions (timestamp)")
    (sql/do-commands "CREATE INDEX person_index ON transactions (person)")))

(defn drop-transactions-table []
  (sql/with-connection *db*
   (sql/drop-table :transactions)))

(defn total []
  (sql/with-connection *db*
   (sql/with-query-results result
     ["SELECT SUM(amount) AS total FROM transactions"]
     (:total (first result)))))

(defn make-transaction [transaction]
  (sql/with-connection *db*
   (sql/insert-values
    :transactions
    [:amount :memo :person]
    [(:amount transaction) (:memo transaction) (:person transaction)])))
