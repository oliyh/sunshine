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

(def avg-uk-annual-consumption
  (* 7.5 365))

(def uk-grid-carbon-intensity
  0.182 ;; 182 grams co2 per kwh
  )

(def installation-cost (+ 9220 400))

(def import-prices
  {"01" 20.35
   "02" 20.35
   "03" 20.35
   "04" 20.35
   "05" 28.08
   "06" 28.08
   "07" 28.08
   "08" 28.08
   "09" 28.08
   "10" 33.63
   "11" 33.63
   "12" 33.63})

(def export-prices
  {"01" 20
   "02" 18
   "03" 19.6
   "04" 28
   "05" 12.37
   "06" 15.49
   "07" 21.85
   "08" 29.97
   "09" 34.44
   "10" 12.11
   "11" 13.21
   "12" 34.67})

(def prices
  {:import import-prices
   :export export-prices})

(def assumed-future-import-price 35)

(defn unit-price [price-kind {:keys [date]}]
  (let [month (subs date 5 7)]
    (get-in prices [price-kind month])))

(def total-cost
  (memoize (fn [price-kind k]
             (apply + (map (fn [stat]
                             (* (unit-price price-kind stat)
                                (get stat k)))
                           stats-2022)))))
