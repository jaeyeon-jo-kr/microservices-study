(ns ui.postgresql.db
  (:require [next.jdbc :as jdbc]))

(def db 
  {:data-source
   (jdbc/get-datasource {:dbtype "h2"
                         :dbname "shopping_mall"})})