(ns reveal.core
  (:require-macros [hiccups.core :refer [html]])
  (:require [clojure.string :refer [join]]
            [goog.dom :as gdom]
            [hiccups.runtime]
            [reveal.slides :as slides]
            [reveal.charts :as charts]))

;; When changing comments, you manually need to refresh your browser
(def options (clj->js {:hash true
                       :controls true
                       :controlsTutorial true
                       :progress false
                       :transition "fade"                   ; e.g. none/fade/slide/convex/concave/zoom
                       :center false
                       :slideNumber "c"
                       :plugins [js/RevealNotes]}))


;; -----------------------------------------------------------------------------
;; You do not need to change anything below this comment

(defn convert
  "Get list of all slides and convert them to html strings."
  []
  (let [slides (slides/all)]
    (join (map #(html %) slides))))

(defn main
  "Get all slides, set them as innerHTML and reinitialize Reveal.js"
  []
  (set! (.. (gdom/getElement "slides") -innerHTML) (convert))
  (let [state (and (.isReady js/Reveal) (.getState js/Reveal))]
    (-> (.initialize js/Reveal options)
        (.then #(when state (.setState js/Reveal state)))
        (.then #(if (.isSpeakerNotes js/Reveal)
                  ;; disable figwheel connection for speaker notes
                  (set! (.-connect js/figwheel.repl) (constantly "Disabled for speaker notes"))

                  ;; push updated speaker notes into the speaker notes window, if open
                  (when-let [window (js/window.open "", "reveal.js - Notes")]
                    (.postMessage window (js/JSON.stringify (clj->js {:namespace "reveal-notes"
                                                                      :type "state"
                                                                      :state (.getState js/Reveal)
                                                                      :notes (.getSlideNotes js/Reveal)}))
                                  "*"))))
        (.then (fn [] (charts/init))))))

(main)
