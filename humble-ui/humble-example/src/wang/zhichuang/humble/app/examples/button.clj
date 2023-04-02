(ns wang.zhichuang.humble.app.examples.button
  (:require
    [io.github.humbleui.ui :as ui]))

(defonce *clicks (atom 0))

(defonce *selected (atom nil))

(def ui
  (ui/center
    (ui/dynamic ctx [{:keys [leading]} ctx]
      (ui/column
        (ui/halign 0
          (ui/button #(swap! *clicks inc)
            (ui/label "Increment")))
          
        (ui/gap 0 leading)
          
        (ui/halign 0
          (ui/button #(swap! *clicks inc)
            (ui/row
              (ui/image "src/wang/zhichuang/humble/app/images/add.png")
              (ui/gap 5 0)
              (ui/valign 0.5
                (ui/label "With PNG icon")))))
          
        (ui/gap 0 leading)
          
        (ui/halign 0
          (ui/button #(swap! *clicks inc)
            (ui/row
              (ui/width 14
                (ui/height 14
                  (ui/svg "src/wang/zhichuang/humble/app/images/add.svg")))
              (ui/gap 5 0)
              (ui/valign 0.5
                (ui/label "With SVG icon")))))
          
        (ui/gap 0 leading)
          
        (ui/halign 0
          (ui/button #(swap! *clicks inc)
            (ui/dynamic _ [clicks @*clicks]
              (ui/label (str "Dynamic label: " clicks)))))
          
        (ui/gap 0 leading)
          
        (ui/halign 0
          (ui/button #(reset! *clicks 0)
            (ui/label "Reset")))
          
        (ui/gap 0 leading)
          
        (ui/dynamic _ [selected @*selected]
          (ui/row
            (ui/with-context
              {:hui/active? (= selected :first)}
              (ui/button #(reset! *selected :first) (ui/label "First")))
            (ui/gap 10 0)
            (ui/with-context
              {:hui/active? (= selected :second)}
              (ui/button #(reset! *selected :second) (ui/label "Second")))
            (ui/gap 10 0)
            (ui/with-context
              {:hui/active? (= selected :third)}
              (ui/button #(reset! *selected :third) (ui/label "Third")))))))))
