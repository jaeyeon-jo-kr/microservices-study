(ns ui.api.app
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
            [ui.api.category :as category]))

(def app
  (ring/ring-handler
    (ring/router
     [["/swagger.json" {:get (swagger/create-swagger-handler)}]
      ["/api-docs/*" {:get (swagger-ui/create-swagger-ui-handler)}]
      ["/api"
       category/routes]]
     {:data {:coercion   reitit.coercion.spec/coercion
             :muuntaja   muuntaja.core/instance
             :middleware [muuntaja/format-middleware
                          parameters/parameters-middleware
                          rrc/coerce-exceptions-middleware
                          rrc/coerce-response-middleware
                          rrc/coerce-request-middleware]}})))