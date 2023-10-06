(ns domain.category
  (:require [malli.core :as m]
            [domain.category :as category]))

(def category
  [:map 
   [:category/id int?]
   [:category/name string?]
   [:category/description string?]
   [:category/image-name string?]])

(defn valid?
  [item]
  (m/validate category item))