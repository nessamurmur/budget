(ns budget.views.layout
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all])
  (:use [hiccup.bootstrap.page]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to budget"]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1,
                       maximum-scale=1, user-scalable=no"}]
     (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js")
     (include-bootstrap)
     (include-css "/css/screen.css")]
    [:body
     [:div {:class "container-fluid"} body]]))
