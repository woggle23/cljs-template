(ns {{name}}.app
    (:gen-class))

(defn -main [& args]
  (require '{{name}}.bootstrap)
  ((resolve '{{name}}.bootstrap/init)
   {:http-port (Integer/parseInt (or (first args) "3001"))
    :nrepl-server true}))
