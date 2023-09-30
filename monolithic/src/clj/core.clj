(ns core
  (:require [reitit.ring :as ring] 
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.ring.middleware.parameters :as parameters] 
            [reitit.ring.coercion :as rrc]
            [reitit.coercion.spec :as rcs]
            [ring.adapter.jetty :as jetty] 
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [next.jdbc :as jdbc]))

(def db {:dbtype "h2" :dbname "shopping_mall"})

(def ds (jdbc/get-datasource db))

(def app
  (ring/ring-handler
   (ring/router
    [""
     ["/swagger.json" {:get (swagger/create-swagger-handler)}]
     ["/api-docs/*" {:get (swagger-ui/create-swagger-ui-handler)}]
     ["/api"
      ["/product"
       {:get {:handler (fn [_]
                         (jdbc/execute! ds ["select * from product"])
                         {:status 200
                          :body (jdbc/execute! ds ["select * from product"])})}}]]]
    {:data {:coercion   reitit.coercion.spec/coercion
            :muuntaja   muuntaja.core/instance
            :middleware [muuntaja/format-middleware
                         parameters/parameters-middleware
                         rrc/coerce-exceptions-middleware
                         rrc/coerce-response-middleware
                         rrc/coerce-request-middleware]}})))

(app {:request-method :get :uri "/swagger.json"})

(app {:request-method :get :uri "/api/product"
      :body-params {:a 1}
      })

(defn- main
  []
  (jetty/run-jetty #'app {:port 3001, :join? false})
  (println "hello world"))