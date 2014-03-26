(ns {{name}}.web
  (:require [cemerick.austin.repls :refer (browser-connected-repl-js)]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as params]
            [compojure.core :refer (GET defroutes)]
            [compojure.route :as route]
            [hiccup.page :as p]
            [clojure.tools.logging :as log]))

(defn- hello-world []
  (p/html5
   [:head
    [:title "Simple-cljs App"]
    (p/include-js "js/app.js")]
   [:body
    [:div {:id "content"}
     [:H1 "Hello, World!"]]
    [:script (browser-connected-repl-js)]]))

(defroutes main-routes
  (GET "/" [] (hello-world))
  (route/resources "/"))

(def handler
  (-> main-routes
      params/wrap-params))

(defn create-server [port]
  (log/debug (format "creating server on port %s" port))
  (jetty/run-jetty handler {:port port :join? false}))

(defn shutdown-server [this]
  (log/debug "shutting down server")
  (.stop this))
