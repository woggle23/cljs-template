# Clojurescript template

A Leiningen template for projects using Clojurescript.

This template uses Stuart Sierra's [Reloaded][1] project structure. The ns generated under ```<project-dir>/dev/project_repl/``` provides some helper functions which create a simple Ring/Jetty application, and a browser-connected-repl using Chas Emerick's [Austin][2].

[1]: http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded
[2]: https://github.com/cemerick/austin

## Installation
Clone the Project then install it in your local repo:

```lein install```

## Usage

Run the following command to create a new cljs-app project:

	lein new cljs-app <your project name>

## Development

**Compile the clojurescript**

```bash
lein clsbuild auto
```
Leiningen will auto-compile any saved clojurescript updates (use ```lein cljsbuild once``` for single compilation).

**launch app**

When launching the repl, it will start up in the ```user``` namespace, providing some useful helper functions.

From the repl run
```
(create-and-start-system)
```
This creates the simple jetty server, listening on port 3001 by default.

**Create a browser-connected-repl**

From the repl run
```
(create-cljs-repl)
```

Once the browser-connected-repl (BCR) has been created the browser must be [launched][3] to use Austin.

[3]: http://localhost:3001

**Using the BCR**

From the BCR enter ```(js/alert "hello")``` - an alert window should be displayed in the browser.

In order to access functions within a clojurescript file, the appropriate namespace ```(ns)``` needs to be loaded. However, unlike clojure, when loading a namespace in the repl, cljs doesn't load all dependencies. As such, all dependencies will need to be referenced in the ```(ns)``` function call. For example, in the case of the generated ```<project-dir>/src-clj/<project-named-dir>/simple_client.cljs```:

```
(ns simple-cljs.simple-client
  (:use-macros [dommy.macros :only [sel1]])
  (:require [clojure.browser.repl]
            [dommy.core :as dommy]
            [goog.string :as gstring]
            [goog.string.format :as gformat]))
```
The above is a little cumbersome, however, Emacs users are in luck; Cider has a built in function to load the current buffer's ```ns``` declaration into the repl - ```(cider-insert-ns-form-in-repl)```.

All functions in the above file can now be used at the repl.

**Refresh the BCR**

Leiningen will auto-compile any updates made to the cljs files. The BCR will need to be refreshed to use the new functions:

 * **Quit the current BCR**
```
:cljs/quit
```
 * **Create new BCR**
```
(create-cljs-repl)
```
 * **Refresh the browser**

 New functions are now available.

 Refreshing the BCR can be automated for Emacs users by adding the following to ```~/.emacs.d/init.el```:
```
(defun cider-reset-cljs ()
  (interactive)
  (save-some-buffers)
  (cider-switch-to-current-repl-buffer)
  (goto-char (point-max))
  (insert (concat ":cljs/quit"))
  (cider-repl-return)
  (sit-for 1)
  (goto-char (point-max))
  (insert (concat "(create-cljs-repl)"))
  (cider-repl-return))
```

 Add key-binding
```
(global-set-key "\C-xcr" 'cider-reset-cljs)
```
Please note, there is probably a better way of doing this (note the use of (sit-for 1)). Any suggests (i.e. better solutions) will be greatly appreciated!

## Brief notes on clojurescript
The functions defined in ```<project-dir>/src-cjls/<project-named-dir>simple_client.cljs``` may not be partiularly useful for your given project, however, they serve to demonstrate to the beginner how to use clojurescript for basic DOM manipulation (using javascript-interop and dommy), and simple event listener/handlers (using dommy).

In terms of javascript-interop, all javascript browser/dom/built-in objects (document, body, alert, console etc) reside under the ```js``` namespace.

Object function calls are completed in the same manner as java-interop:

```
(.function-name js-object args)
```
e.g
```
(.getElementById js/document "content")
```

To access a property of a js object, use the ```.-``` accessor. E.g:
```
(.-innerText js-element)
```

To set a property of a js object, use the ```(set!)``` method, E.g:
```
(set! (.-innerText js-element) "Hello, World!")
```

## Dommy
[Dommy][4] - Cracking DOM manipulation library.

[4]: [https://github.com/prismatic/dommy]

## Austin
[Austin][2] is a fine piece of work by Chas Emerick to allow familiar repl-based development for clojurescript.

The key things to note (as used by this project) are as follows:

 * Require ```clojure.browser.repl``` namespace in your clojurescript file.

 * Create the repl environment using:
```
(def repl-env (reset! cemerick.austin.repls/browser-repl-env (cemerick.austin/repl-env)))
```
```
(cemerick.austin.repls/cljs-repl repl-env)
```

 Note, this last step is completed by ```<project-dir>/dev/project_repl/<project-file-name>/create-cljs-repl```
 * Ensure the last element in your html page is a script tag that pulls in ```cemerick.austin.repls/browser-connected-repl-js```
