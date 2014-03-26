(ns project-repl.{{name}}
    (:require [{{name}}.bootstrap :as bootstrap]
              [clojure.reflect :refer (reflect)]
              [clojure.tools.namespace.repl :refer :all]))

(defn create-and-start-system []
  (bootstrap/init {:http-port 3001}))

(defn stop-system []
  (bootstrap/shutdown!))

(defn reset-dev []
  (stop-system)
  (refresh :after 'project-repl.{{name}}/create-and-start-system))

(defn create-cljs-repl []
  (def repl-env (reset! cemerick.austin.repls/browser-repl-env (cemerick.austin/repl-env)))
  (cemerick.austin.repls/cljs-repl repl-env))
