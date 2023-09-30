(ns migration
  (:require [next.jdbc :as jdbc]))

(def db {:dbtype "h2" :dbname "shopping_mall"})

(def ds (jdbc/get-datasource db))

(defn create-cart-table
  []
  (jdbc/execute-one!
   ds
   ["create table cart (
     id int auto_increment primary key,
     user_name varchar(32),
     item_id int)"]))

(defn create-cart-item
  []
  (jdbc/execute-one!
   ds
   ["create table cart_item (
     id int auto_increment primary key,
     quantity int,
     color varchar(32),
     price int,
     product_id int)"]))

(defn create-category
  []
  (jdbc/execute-one!
   ds
   ["create table category (
     id int auto_increment primary key,
     name varchar(50),
     description varchar(900),
     image_name varchar(256))"]))

(defn create-contact
  []
  (jdbc/execute-one!
   ds 
   ["create table contact (
     id int auto_increment primary key,
     name varchar(50),
     phone varchar(20),
     email varchar(60),
     message varchar(256))"]))

(defn create-order
  []
  (jdbc/execute-one!
   ds
   ["create table `order` (
     id int auto_increment primary key,
     user_name varchar(30),
     total_price bigint,
     first_name varchar(10),
     last_name varchar(20),
     email_address varchar(40),
     address_line varchar(50),
     country varchar(50),
     state varchar(20),
     zipcode varchar(10),
     card_name varchar(30),
     card_number varchar(40),
     expiration varchar(10),
     payment_method tinyint)"]))

(defn create-product
  []
  (jdbc/execute-one!
   ds
   ["create table product (
     id int auto_increment primary key,
     name varchar(50),
     summary varchar(512),
     description varchar(2086),
     price bigint,
     category_id int)"]))

(defn insert-sample-product
  []
  (jdbc/execute! 
   ds 
   ["INSERT INTO `product` 
     (name, summary, description, price, category_id)
     VALUES
     ('product1', 'product1s summary','product1s description', 100, 1);"]))

  
(comment 
  (jdbc/execute-one! ds ["drop table shopping_cart"])
  (jdbc/execute! ds ["show tables"])
  ds
  )
  