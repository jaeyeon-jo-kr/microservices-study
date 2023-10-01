(ns core
  (:require [reitit.ring :as ring] 
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.ring.middleware.parameters :as parameters] 
            [reitit.ring.coercion :as rrc]
            [reitit.coercion.spec :as rcs]
            [ring.adapter.jetty :as jetty] 
            [clojure.tools.logging :as log]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [next.jdbc :as jdbc]))

(def db {:dbtype "h2" :dbname "shopping_mall"})

(def ds (jdbc/get-datasource db))

(def app
  (ring/ring-handler
   (ring/router
    [["/swagger.json" {:get (swagger/create-swagger-handler)}]
     ["/api-docs/*" {:get (swagger-ui/create-swagger-ui-handler)}]
     ["/api"
      ["/ping" {:get
                {:handler
                 (fn [request]
                   {:status 200
                    :body
                    (with-out-str
                      (clojure.pprint/pprint request))})}
                :post
                {:handler
                 (fn [request]
                   {:status 200
                    :body
                    (with-out-str
                      (clojure.pprint/pprint request))})}}]
      ["/product"
       {:get {:handler (fn [_]
                         {:status 200
                          :body (jdbc/execute! ds ["SELECT * FROM product;"])})}}]
      ["/product/find"
       {:get {:handler
              (fn [{{category "category" :as q} :params}]
                {:status 200
                 :body
                 (->> (jdbc/execute! ds ["SELECT * FROM product;"])
                      (filter (comp #{(read-string category)} :PRODUCT/CATEGORY_ID)))})}}]
      ["/card-item"
       {:post {:handler
               (fn [{{:strs [quantity color price product_id]} :params}]
                 (if (and quantity color price product_id)
                   {:status 200
                    :body (jdbc/execute!
                           ds
                           ["INSERT INTO CART_ITEM(QUANTITY, COLOR, PRICE, PRODUCT_ID) 
                                                            VALUES(?,?,?,?)"
                            quantity color price product_id])}
                   {:status 500
                    :body "Parameter is not enough"}))}}]
      ["/user/cart"
       {:get
        {:handler
         (fn [{{:strs [username]} :params}]
           {:status 200
            :body (jdbc/execute!
                   ds
                   ["SELECT id, quentity, color, price, product_id
                     FROM CART_ITEM
                    LEFT JOIN CART ON CART_ITEM.id = CART.item_id
                    WHERE user_name = *" username])})}}]]]
    {:data {:coercion   reitit.coercion.spec/coercion
            :muuntaja   muuntaja.core/instance
            :middleware [muuntaja/format-middleware
                         parameters/parameters-middleware
                         rrc/coerce-exceptions-middleware
                         rrc/coerce-response-middleware
                         rrc/coerce-request-middleware]}})))


(defonce server (atom nil))

(defn start-server
  []
  (jetty/run-jetty #'app {:port 3001, :join? false}))

(defn stop-server
  [server]
  (doto server
    (.stop)))

(defn- main
  []
  (start-server))

(comment
  (jdbc/execute!
   ds
   ["INSERT INTO CART_ITEM(QUANTITY, COLOR, PRICE, PRODUCT_ID) 
                                                              VALUES(?,?,?,?)"
    2 "red" 10 11])
  (reset! server (start-server))
  (stop-server @server)
  (app {:request-method :get :uri "/swagger.json"})
  (app {:request-method :get :uri "/api/ping"})
  (app {:request-method :post :uri "/api/ping" :body-params
        {"quantity" 1
         "color" 2
         "price" 10
         "product_id" 1}
        })
  
  (app {:request-method :get :uri "/api/product/find?category=1"})
  (app {:request-method :post :uri "/api/card-item"
        :params
        {"quantity" 1 
         "color" 2 
         "price" 10
         "product_id" 1}})


  )