(ns infrastructure.handler.category
  (:require [domain.category :as category]
            [application.category-list :as categories]
            [infrastructure.repository.category :as repos]))

(defn list-items
  [repository]
  (repos/list-items repository))