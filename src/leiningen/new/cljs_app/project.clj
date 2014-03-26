(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]

                 ;; web
                 [compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [ring/ring-jetty-adapter "1.1.0"]

                 ;; cljs
                 [org.clojure/clojurescript "0.0-2138"]
                 [prismatic/dommy "0.1.1"]

                 ;; utils
                 [org.clojure/tools.nrepl "0.2.3"]
                 [org.clojure/tools.logging "0.2.6"]]
  :main {{name}}.app
  :profiles {:dev {:source-paths ["dev"]
                   :repl-options {:init-ns user}
                   :dependencies [[org.clojure/tools.namespace "0.2.4"]]
                   :plugins [[com.cemerick/austin "0.1.3"]
                             [lein-cljsbuild "1.0.1"]]
                   :cljsbuild {:builds [{:source-paths ["src-cljs"]
                                         :compiler {:output-to "resources/public/js/app.js"
                                                    :optimizations :simple
                                                    :pretty-print true}}]}}})
