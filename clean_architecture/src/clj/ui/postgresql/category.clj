(ns ui.postgresql.category
  (:require [ui.postgresql.db :as db]
            [infrastructure.repository.category :as category]
            [next.jdbc :as jdbc]))

(defn- list-items
  [{ds :data-source}]
  (jdbc/execute! ds ["SELECT * FROM category;"]))

(def repository
  {:db db/db
   :interfaces {:list list-items}})

