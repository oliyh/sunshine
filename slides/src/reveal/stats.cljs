(ns reveal.stats
  (:require [reveal.macros :refer-macros [defdata]]))

(defdata stats-2022 "stats-2022.edn")

(def total
  (memoize
   (fn [k]
     (int (apply + (map k stats-2022))))))

(defn format-percent [num denom]
  (str (int (* 100 (/ num denom))) "%"))

(defn percent [num denom]
  (format-percent (total num) (total denom)))

(defn series [k]
  (map k stats-2022))
