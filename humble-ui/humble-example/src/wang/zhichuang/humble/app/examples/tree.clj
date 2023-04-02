(ns wang.zhichuang.humble.app.examples.tree
  (:require
    [io.github.humbleui.core :as core]
    [io.github.humbleui.paint :as paint]
    [io.github.humbleui.ui :as ui]))

(defn random-green []
  (let [r (+ 32  (rand-int 32))
        g (+ 192 (rand-int 32))
        b (+ 32  (rand-int 32))]
    (unchecked-int
      (bit-or
        (unchecked-int 0xFF000000)
        (bit-shift-left r 16)
        (bit-shift-left g 8)
        (bit-shift-left b 0)))))

(def letters
  (map str (cycle "HappyNew2022!")))

(def ui
  (ui/dynamic ctx [{:keys [leading]} ctx]
    (ui/clip
      (ui/halign 0.5
        (ui/padding 0 10 0 0
          (ui/with-bounds ::bounds
            (ui/dynamic ctx [rows  (quot (:height (::bounds ctx)) (+ 11 leading))
                             _time (quot (core/now) 1000)] ;; TODO real timer
              (ui/column
                (interpose
                  (ui/gap 0 1)
                  (for [y (range rows)]
                    (ui/halign 0.5
                      (ui/row
                        (interpose
                          (ui/gap 1 0)
                          (for [x (range (inc y))]
                            (if (= x y 0)
                              (ui/rect (paint/fill 0xFFCC3333)
                                (ui/padding 5 5
                                  (ui/label "★")))
                              (ui/rect (paint/fill (random-green))
                                (ui/padding 5 5
                                  (let [idx (+ x (* y (+ y 1) 1/2) -1)]
                                    (ui/label (nth letters idx))))))))))))))))))))
