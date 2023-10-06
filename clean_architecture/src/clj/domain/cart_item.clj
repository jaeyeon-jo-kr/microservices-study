(ns domain.cart-item
  (:require [malli.core :as m]
            [domain.product :as product]))

(def cart-item
  [:map
   [:cart-item/id int?]
   [:cart-item/quantity int?]
   [:cart-item/color string?]
   [:cart-item/unit-price decimal?]
   [:cart-item/total-price decimal?]
   [:cart-item/product product/product]])