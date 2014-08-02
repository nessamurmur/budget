(ns budget.routes.home
  (:require [compojure.core :refer :all]
            [budget.views.layout :as layout]
            [hiccup.form :refer :all]))

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
                          (submit-button {:class "btn btn-primary"} "Submit"))))

(defn update-amount [add subtract]
  (let [flash "Hi"]
    (home flash)))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [add subtract] (update-amount add subtract)))
