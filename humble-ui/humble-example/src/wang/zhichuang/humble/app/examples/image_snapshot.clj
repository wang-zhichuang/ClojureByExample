(ns wang.zhichuang.humble.app.examples.image-snapshot
  (:require
    [io.github.humbleui.ui :as ui])
  (:import
    [io.github.humbleui.skija Paint Shader]))

(def ui
  (ui/with-bounds ::bounds
    (ui/dynamic ctx [{:keys [scale] ::keys [bounds]} ctx
                     {:keys [height]} bounds] 
      (let [shader (Shader/makeLinearGradient
                     (float 0)
                     (float 0)
                     (float 0)
                     (float (* height 2 scale))
                     (int-array [(unchecked-int 0xFF277da1)
                                 (unchecked-int 0xFFffba08)]))
            paint  (-> (Paint.) (.setShader shader))]
        (ui/vscrollbar
          (ui/valign 0
            (ui/height (* height 2)
              (ui/row
                (ui/gap 10 0)
                [:stretch 1 (ui/rect paint (ui/gap 0 0))]
                (ui/gap 10 0)
                [:stretch 1
                 (ui/image-snapshot
                   (ui/rect paint (ui/gap 0 0)))]
                (ui/gap 10 0)))))))))
