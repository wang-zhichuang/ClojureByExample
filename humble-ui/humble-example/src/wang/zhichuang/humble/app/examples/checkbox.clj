(ns wang.zhichuang.humble.app.examples.checkbox
  (:require
    [io.github.humbleui.font :as font]
    [io.github.humbleui.ui :as ui]))

(def *state-group
  (atom false))

(def *state-first
  (atom false))

(def *state-second
  (atom false))

(add-watch *state-group :watch
  (fn [_ _ old new]
    (when (and (not= old new)
            (or (true? new) (false? new)))
      (reset! *state-first new)
      (reset! *state-second new))))

(add-watch *state-first :watch
  (fn [_ _ old new]
    (when (not= old new)
      (reset! *state-group
        (cond
          (every? true? [new @*state-second])  true
          (every? false? [new @*state-second]) false
          :else                                :indeterminate)))))

(add-watch *state-second :watch
  (fn [_ _ old new]
    (when (not= old new)
      (reset! *state-group
        (cond
          (every? true? [@*state-first new])  true
          (every? false? [@*state-first new]) false
          :else                               :indeterminate)))))

(def ui
  (ui/center
    (ui/column
      (ui/dynamic ctx [{:keys [face-ui scale]} ctx]
        (ui/with-context
          {:font-ui (font/make-with-cap-height face-ui (* 20 scale))}
          (ui/checkbox *state-group (ui/label "Group state"))))
      (ui/gap 0 10)
      (ui/checkbox *state-first (ui/label "First state"))
      (ui/gap 0 10)
      (ui/checkbox *state-second (ui/label "Second state")))))
