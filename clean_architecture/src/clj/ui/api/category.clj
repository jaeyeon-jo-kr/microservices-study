(ns ui.api.category
  (:require [ui.postgresql.category :as category]))

(def routes
  ["/category"
   ["/list"
    {:get
     {:handler
      (fn [_request]
        {:get 200
         :body (category/list)})}}]])