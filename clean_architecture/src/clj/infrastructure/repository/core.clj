(ns infrastructure.repository.core
  (:require [malli.core :as m]))

(def repository
  [:map
   [:db any?]
   [:interfaces any?]])


(defn valid? 
  [item]
  (m/validate repository item))

(comment 
  (m/ast repository)
  repository
  (valid? {:db {}
           :interfaces []})
  )