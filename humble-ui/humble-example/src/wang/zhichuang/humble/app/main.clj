(ns wang.zhichuang.humble.app.main
  "The main app namespace.
  Responsible for initializing the window and app state when the app starts."
  (:require
   [io.github.humbleui.ui :as ui]
   ;; [io.github.humbleui.window :as window]
   [wang.zhichuang.humble.app.state :as state])
  (:import
   [io.github.humbleui.skija Color ColorSpace]
   [io.github.humbleui.jwm Window]
   [io.github.humbleui.jwm.skija LayerMetalSkija])
  (:gen-class))

(def app
  "Main app definition."
  (ui/default-theme ; we must wrap our app in a theme
   {}
   ;; just some random stuff
   (ui/center
    (ui/label "Hello from Humble UI ! 👋"))))

;; reset current app state on eval of this ns
(reset! state/*app app)

(defn -main
  "Run once on app start, starting the humble app."
  [& _args]
  (ui/start-app!
   (reset! state/*window
           (ui/window
            {:title    "Humble 🐝 UI"
             :bg-color 0xFFFFFFFF}
            state/*app)))
  (state/redraw!))
