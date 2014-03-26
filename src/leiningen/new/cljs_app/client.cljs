(ns {{name}}.client
  (:use-macros [dommy.macros :only [sel1]])
  (:require [clojure.browser.repl]
            [dommy.core :as dommy]
            [goog.string :as gstring]
            [goog.string.format :as gformat]))

(defn hello
  []
  (js/alert "hello"))

(defn set-new-text [content]
  (set! (.-innerText (.getElementById js/document "content")) content))

(defn set-new-html [content]
  (set! (.-innerHTML (.getElementById js/document "content"))
        (gstring/format "<h1>%s</h1>" content)))

(defn append-new-html [content]
  (let [p (.createElement js/document "p")]
    (set! (.-innerText p) content)
    (.insertBefore
     (.item (.getElementsByTagName js/document "body") 0)
     p
     (.getElementById js/document "content"))))


; dommy
(defn add-class-to-content [class]
  (dommy/add-class! (sel1 :#content) (keyword class)))


(defn dommy-set-new-html [content]
  (dommy/set-html! (sel1 :#content) (gstring/format "<h1>%s</h1>" content)))

(defn dommy-append-div [content]
  (dommy/append! (sel1 :#content) [:. content]))

;; Add classes to el by using '.'; i.e. p.active will create a 'p' element with class 'active'.
;; Additional classes are appended as follows p.class1.class2.classn
;; To add an id, el.#<id-name>, e.g. "button.#play-music" => <button id="play-music"/>
(defn dommy-append-element [el content]
  (dommy/append! (sel1 :#content) [(keyword el) content]))

(defn clickEvent [event]
  (.log js/console "button clicked!"))

(defn create-button-with-click-handler [id text]
  (dommy-append-element (gstring/format "button.#%s" id) text)
  (dommy/listen! (sel1 (keyword (gstring/format "#%s" id))) :click clickEvent))
