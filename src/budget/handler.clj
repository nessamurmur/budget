(ns budget.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [budget.routes.home :refer [home-routes]]
            [budget.models.db :as db :refer [create-transactions-table]])
  (:use [hiccup.bootstrap.middleware]))

(defn init []
  (println "budget is starting")
  (if-not (.exists (java.io.File. "./transactions.sq3"))
    (db/create-transactions-table)))

(defn destroy []
  (println "budget is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-bootstrap-resources
   (-> (routes home-routes app-routes)
       (handler/site)
       (wrap-base-url))))
