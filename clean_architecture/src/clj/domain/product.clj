(ns domain.product
  (:require [malli.core :as m]
            [domain.category :as category]))

(def product
  [:map
   [:product/id int?]
   [:product/name string?]
   [:product/summary string?]
   [:product/description string?]
   [:product/price integer?]
   [:product/category_id category/category]])

(defn valid?
  [data]
  (m/validate product data))