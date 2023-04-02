(ns wang.zhichuang.humble.app.examples.animation
  (:require
    [io.github.humbleui.ui :as ui]))

(def ui
  (ui/center
    (ui/row
      (ui/column
        (ui/animation "src/wang/zhichuang/humble/app/images/animated.gif")
        (ui/gap 0 10)
        (ui/label "GIF"))
      (ui/gap 10 0)
      (ui/column
        (ui/animation "src/wang/zhichuang/humble/app/images/animated.webp")
        (ui/gap 0 10)
        (ui/label "WebP")))))
