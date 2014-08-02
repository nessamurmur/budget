(ns budget.routes.home
  (:require [compojure.core :refer :all]
            [budget.views.layout :as layout]
            [hiccup.form :refer :all]
            [budget.models.db :as sql]))

(defn home [& [flash-message total]]
  (layout/common [:h1 "Monthly Budget"]
                 [:br]
                 (if flash-message
                   [:div {:class "alert alert-notice"} flash-message])
                 (form-to {:role "form"} [:post "/"]
                          [:div {:class "form-group has-success"}
                           [:label {:class "control-label"
                                    :for "add"} "Add Amount  "]
                           (text-field {:class "form-control"
                                        :type "number"
                                        :min 0} "add")]
                          [:br]
                          [:br]
                          [:div {:class "form-group has-error"}
                           [:label {:class "control-label"
                                    :for "subtract"} "Subtract Amount  "]
                           (text-field {:class "form-control"
                                         :type "number"
                                         :min 0} "subtract")]
                          [:br]
                          [:br]
                          (submit-button {:class "btn btn-primary btn-large btn-block"} "Submit"))))

(defn update-amount [add subtract]
  (cond
   (empty? subtract) (sql/make-transaction {:amount (Integer. add)})
   (empty? add) (sql/make-transaction {:amount (- (Integer. subtract))})
   :else (sql/make-transaction {:amount (- (Integer. add) (Integer. subtract))}))
  (let [flash (str "You now have $" (sql/total) " in your account!")]
    (home flash)))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [add subtract] (update-amount add subtract)))
