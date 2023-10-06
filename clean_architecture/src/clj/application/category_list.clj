(ns application.category_list
  (:require [domain.category :as category]))


(defn categories
  [items]
  (sort-by :category/id items))
