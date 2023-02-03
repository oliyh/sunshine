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
        ;; prevent figwheel sharing the same session id / name with the speaker notes view
        ;; todo: it doesn't actually need the figwheel reload, if the updates get pushed from
        ;; the main presentation - is there a way to disable?
        (.then #(when (.isSpeakerNotes js/Reveal)
                  (js/console.log "Setting new figwheel session for speaker view")
                  (.removeItem js/sessionStorage "figwheel.repl:::figwheel.repl/session-id" #_"speaker-view")
                  (.removeItem js/sessionStorage "figwheel.repl:::figwheel.repl/session-name" #_"speaker-view")))
        (.then #(when state (.setState js/Reveal state)))
        ;; todo work out if this does anything
        ;;(.then #(.sync js/Reveal))
        ;; post a message to the speaker notes to get it to pick up the updates
        #_(.then #(when-not (.isSpeakerNotes js/Reveal)
                  (when-let [window (js/window.open "", "reveal.js - Notes")]
                    (.postMessage window (js/JSON.stringify (clj->js {:namespace "reveal-notes"
                                                                      :type "state"
                                                                      :state (.getState js/Reveal)
                                                                      :notes (.getSlideNotes js/Reveal)}))
                                  "*"))))
        (.then (fn [] (charts/init))))))

(main)
