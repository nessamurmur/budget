(ns budget.layout
  (:require [selmer.parser :as parser]
            [clojure.string :as s]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [ring.util.response :refer [content-type response]]
            [compojure.response :refer [Renderable]]
            [environ.core :refer [env]])
  (:use [hiccup.bootstrap.page]))

(def template-path "templates/")

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

(deftype RenderableTemplate [template params]
  Renderable
  (render [this request]
    (content-type
      (->> (assoc params
                  (keyword (s/replace template #".html" "-selected")) "active"
                  :dev (env :dev)
                  :servlet-context
                  (if-let [context (:servlet-context request)]
                    ;; If we're not inside a serlvet environment (for
                    ;; example when using mock requests), then
                    ;; .getContextPath might not exist
                    (try (.getContextPath context)
                         (catch IllegalArgumentException _ context))))
        (parser/render-file (str template-path template))
        response)
      "text/html; charset=utf-8")))

(defn render [template & [params]]
  (RenderableTemplate. template params))
