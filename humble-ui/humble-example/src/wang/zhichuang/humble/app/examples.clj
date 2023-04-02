(ns wang.zhichuang.humble.app.examples
  (:require
    [clojure.core.server :as server]
    [wang.zhichuang.humble.app.examples.7guis-converter :as examples.7guis-converter]
    [wang.zhichuang.humble.app.examples.align :as examples.align]
    [wang.zhichuang.humble.app.examples.animation :as examples.animation]
    [wang.zhichuang.humble.app.examples.backdrop :as examples.backdrop]
    [wang.zhichuang.humble.app.examples.bmi-calculator :as examples.bmi-calculator]
    [wang.zhichuang.humble.app.examples.button :as examples.button]
    [wang.zhichuang.humble.app.examples.calculator :as examples.calculator]
    [wang.zhichuang.humble.app.examples.canvas :as examples.canvas]
    [wang.zhichuang.humble.app.examples.checkbox :as examples.checkbox]
    [wang.zhichuang.humble.app.examples.container :as examples.container]
    [wang.zhichuang.humble.app.examples.effects :as examples.effects]
    [wang.zhichuang.humble.app.examples.errors :as examples.errors]
    [wang.zhichuang.humble.app.examples.event-bubbling :as examples.event-bubbling]
    [wang.zhichuang.humble.app.examples.grid :as examples.grid]
    [wang.zhichuang.humble.app.examples.image-snapshot :as examples.image-snapshot]
    [wang.zhichuang.humble.app.examples.label :as examples.label]
    [wang.zhichuang.humble.app.examples.scroll :as examples.scroll]
    [wang.zhichuang.humble.app.examples.settings :as examples.settings]
    [wang.zhichuang.humble.app.examples.state :as state]
    [wang.zhichuang.humble.app.examples.slider :as examples.slider]
    [wang.zhichuang.humble.app.examples.stack :as examples.stack]
    [wang.zhichuang.humble.app.examples.svg :as examples.svg]
    [wang.zhichuang.humble.app.examples.text-field :as examples.text-field]
    [wang.zhichuang.humble.app.examples.text-field-debug :as examples.text-field-debug]
    [wang.zhichuang.humble.app.examples.todomvc :as examples.todomvc]
    [wang.zhichuang.humble.app.examples.toggle :as examples.toggle]
    [wang.zhichuang.humble.app.examples.tooltip :as examples.tooltip]
    [wang.zhichuang.humble.app.examples.tree :as examples.tree]
    [wang.zhichuang.humble.app.examples.treemap :as examples.treemap]
    [wang.zhichuang.humble.app.examples.wordle :as examples.wordle]
    [io.github.humbleui.app :as app]
    [io.github.humbleui.debug :as debug]
    [io.github.humbleui.paint :as paint]
    [io.github.humbleui.window :as window]
    [io.github.humbleui.ui :as ui]))

(defn set-floating! [window floating]
  (when window
    (if floating
      (window/set-z-order window :floating)
      (window/set-z-order window :normal))))

(add-watch state/*floating ::window
  (fn [_ _ _ floating]
    (set-floating! @state/*window floating)))

(def examples
  (sorted-map
    "7 GUIs: Converter" examples.7guis-converter/ui
    "Align" examples.align/ui
    "Animation" examples.animation/ui
    "Backdrop" examples.backdrop/ui
    "BMI Calculator" examples.bmi-calculator/ui
    "Button" examples.button/ui
    "Calculator" examples.calculator/ui
    "Canvas" examples.canvas/ui
    "Checkbox" examples.checkbox/ui
    "Container" examples.container/ui
    "Effects" examples.effects/ui
    "Errors" examples.errors/ui
    "Event Bubbling" examples.event-bubbling/ui
    "Grid" examples.grid/ui
    "Image Snapshot" examples.image-snapshot/ui
    "Label" examples.label/ui
    "Scroll" examples.scroll/ui
    "Settings" examples.settings/ui
    "Slider" examples.slider/ui
    "Stack" examples.stack/ui
    "SVG" examples.svg/ui
    "Text Field" examples.text-field/ui
    "Text Field Debug" examples.text-field-debug/ui
    "Todo MVC" examples.todomvc/ui
    "Toggle" examples.toggle/ui
    "Tooltip" examples.tooltip/ui
    "Tree" examples.tree/ui
    "Treemap" examples.treemap/ui
    "Wordle" examples.wordle/ui
    ))

(def light-grey
  0xffeeeeee)   

(def border-line
  (ui/rect (paint/fill light-grey)
    (ui/gap 1 0)))
      
(def app
  (ui/default-theme {}; :font-size 13
    ; :cap-height 10
    ; :leading 100
    ; :fill-text (paint/fill 0xFFCC3333)
    ; :hui.text-field/fill-text (paint/fill 0xFFCC3333)
    (ui/row
      (ui/vscrollbar
          (ui/column
            (for [[name _] (sort-by first examples)]
              (ui/clickable
                {:on-click (fn [_] (reset! state/*example name))}
                (ui/dynamic ctx [selected? (= name @state/*example)
                                 hovered?  (:hui/hovered? ctx)]
                  (let [label (ui/padding 20 10
                                (ui/label name))]
                    (cond
                      selected? (ui/rect (paint/fill 0xFFB2D7FE) label)
                      hovered?  (ui/rect (paint/fill 0xFFE1EFFA) label)
                      :else     label)))))))
      border-line
      [:stretch 1
       (ui/clip
         (ui/dynamic _ [name @state/*example]
           (examples name)))])))

(reset! state/*app app)

(defn -main [& args]
  (ui/start-app!
    (let [screen (app/primary-screen)]
      (reset! state/*window 
        (ui/window
          {:title    "Humble 🐝 UI"
           :mac-icon "dev/images/icon.icns"
           :screen   (:id screen)
           :width    600
           :height   600
           :x        :center
           :y        :center}
          state/*app))))
  (set-floating! @state/*window @state/*floating)
  #_(reset! debug/*enabled? true)
  (let [{port "--port"
         :or {port "5555"}} (apply array-map args)
        port (parse-long port)]
    (println "Started Server Socket REPL on port" port)
    (server/start-server
      {:name          "repl"
       :port          port
       :accept        'clojure.core.server/repl
       :server-daemon false})))
