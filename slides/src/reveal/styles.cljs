(ns reveal.styles
  (:require [clojure.string :as str]))

(def solar-green "rgb(50, 168, 82)")
(def consumption-orange "rgb(230, 151, 34)")
(def battery-blue "rgb(34, 155, 230)")
(def export-purple "rgb(230, 34, 214)")
(def import-red "rgb(230, 34, 34)")
(def inefficiency-grey "rgb(150, 150, 150)")

(defn style [m]
  {:style (str/join ";" (map (fn [[k v]] (str (name k) ": " v)) m))})
