(ns ui.api.category
  (:require [ui.postgresql.category :as category]
            [infrastructure.handler.category :as handler]))

(def routes
  ["/category"
   ["/list"
    {:get
     {:handler
      (fn [_request]
        {:get 200
         :body (handler/list-items category/repository)})}}]])