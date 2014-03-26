(ns leiningen.new.cljs-app
  (:use [leiningen.new.templates :only [renderer sanitize year ->files]]
        [leinjacker.utils :only [lein-generation]]))

(defn check-lein-version []
  (if (< (lein-generation) 2)
    (throw (new Exception "Leiningen v2 is required..."))))

(defn cljs-app
  "Create a new Compojure project"
  [name]
  (check-lein-version)
  (let [data {:name name
              :sanitized (sanitize name)
              :year (year)}
        render #((renderer "cljs_app") % data)]
    (println "Generating a cljs project named" (str name "..."))
    (->files data
             [".gitignore"  (render "gitignore")]
             ["project.clj" (render "project.clj")]
             ["README.md"   (render "README.md")]
             ["dev/user.clj" (render "user.clj")]
             ["dev/project_repl/{{sanitized}}.clj" (render "project_repl.clj")]
             ["src/{{sanitized}}/app.clj" (render "app.clj")]
             ["src/{{sanitized}}/bootstrap.clj" (render "bootstrap.clj")]
             ["src/{{sanitized}}/system.clj" (render "system.clj")]
             ["src/{{sanitized}}/web.clj" (render "web.clj")]
             ["src-cljs/{{sanitized}}/client.cljs" (render "client.cljs")])))
