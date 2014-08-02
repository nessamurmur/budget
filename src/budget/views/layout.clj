(ns budget.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to budget"]
     (include-css "/css/screen.css")]
    [:body body]))
