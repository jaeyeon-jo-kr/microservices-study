(ns domain.cart
  (:require [malli.core :as m]
            [domain.cart-item :as cart-item]))

(def cart 
  [:map
   [:cart/id int?]
   [:cart/user_name string?]
   [:cart/items [:sequential cart-item/cart-item]]])

(defn valid?
  [data]
  (m/validate cart data))
