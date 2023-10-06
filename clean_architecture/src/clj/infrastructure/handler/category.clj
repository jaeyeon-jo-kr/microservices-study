(ns infrastructure.handler.category
  (:require [infrastructure.repository.category :as repos]))

(defn list-items
  [repository]
  (repos/list-items repository))