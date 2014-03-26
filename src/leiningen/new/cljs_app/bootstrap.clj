(ns {{name}}.bootstrap
    (:require [{{name}}.system :as system]
             [{{name}}.web :as web]
             [clojure.tools.logging :as log]))

(defn init [args]
  (alter-var-root #'system/system
                  (fn [system] (assoc system :http-server (web/create-server (:http-port args 3001))))))

(defn shutdown! []
  (alter-var-root #'system/system
                  (fn [system] (web/shutdown-server (:http-server system)))))
