(ns infrastructure.repository.category
  (:require 
   [infrastructure.repository.core :as repos]
   [malli.core :as m]))

(def interfaces
  [:map [:category/list fn?]])

(def repository
  (-> (m/ast repos/repository)
      (assoc-in  [:keys :interfaces :value] (m/ast interfaces))
      (m/from-ast)))

(defn valid?
  [item]
  (m/validate repository item))

(defn list-items
  [repos]
  (let [db (:db repos)
        handler (-> repos :interfaces :list)]
    (handler db)))
