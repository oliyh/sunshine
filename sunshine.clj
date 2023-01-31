(ns sunshine
  (:require [cheshire.core :as json]))

#_    {
      "eRecDay" 6, ;; solar production, kwh
      "eToUserDay" 113,  ;; consumption from grid, 0.1 kwh
      "ePv3Day" 0,
      "ePv1Day" 60, ;; solar production from string 1, 0.1 kwh
      "ePv2Day" 0,
      "eEpsDay" 0,
      "eInvDay" 53, ;; consumption from inverter
      "eChgDay" 31, ;; battery charging, 0.1kwh
      "day" 1,      ;; day of month
      "eDisChgDay" 24, ;; battery discharging, 0.1 kwh
      "eToGridDay" 1 ;; export to grid, 0.1 kwh
    }

(defn stat [month day]
  (try
    (let [to-inverter (:ePv1Day day)
          from-inverter (:eInvDay day)
          to-battery (:eChgDay day)
          from-battery (:eDisChgDay day)
          from-grid (:eToUserDay day)
          to-grid (:eToGridDay day)
          inverter-to-house (- from-inverter to-battery to-grid)
          consumed (+ from-grid from-battery inverter-to-house)
          / (fn [a b] (if (zero? b) a (float (/ a b))))]
      {:date (str "2022-"
                  (if (< 10 month)
                    month
                    (str "0" month))
                  "-"
                  (let [d (:day day)]
                    (if (< 10 d)
                      d
                      (str "0" d))))

       :to-inverter (/ to-inverter 10)
       :from-inverter (/ from-inverter 10)
       :inverter-lost (/ (- to-inverter from-inverter) 10)
       :inverter-efficiency (/ from-inverter to-inverter)
       :inverter-to-house (/ inverter-to-house 10)

       :to-battery (/ to-battery 10)
       :from-battery (/ from-battery 10)
       :battery-lost (/ (- to-battery from-battery) 10)
       :battery-efficiency (/ from-battery to-battery)

       :from-grid (/ from-grid 10)
       :to-grid (/ to-grid 10)

       :consumed (/ consumed 10)
       :grid-reliance (/ from-grid consumed)})

    (catch Exception _e
      (println "Couldn't convert" day))))

(def months ["jan" "feb" "mar" "apr" "may" "jun" "jul" "aug" "sep" "oct" "nov" "dec"])

(defn all []
  (apply concat
         (map-indexed (fn [i month-name]
                        (let [data (:data (json/decode
                                           (slurp (str "../sunshine/slides/" month-name ".json"))
                                           keyword))]
                          (map (partial stat (inc i)) data)))
                      months)))
