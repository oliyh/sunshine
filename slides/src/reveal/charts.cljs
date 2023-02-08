(ns reveal.charts
  (:require [reveal.stats :as stats :refer [stats-2022]]
            [reveal.styles :as styles]))

(set! (.. js/Chart -defaults -color) "white")
(set! (.. js/Chart -defaults -font -size) 20)
(.register js/Chart js/ChartDataLabels)

(defn- draw-chart [id opts]
  (when-let [old-chart (js/Chart.getChart id)]
    (.destroy old-chart))

  (let [chart-opts (clj->js opts)
        canvas (js/document.getElementById id)]
    (js/Chart. canvas chart-opts)))

(def legend {:labels {:padding 20}
             :position "bottom"})

(def percent-datalabels
  {:borderColor "white"
   :borderWidth 2
   :borderRadius 25
   :anchor "end"
   :padding 12
   :backgroundColor (fn [context]
                      (.. context -dataset -backgroundColor))
   :formatter (fn [value context]
                (let [total (apply +  (.. context -dataset -data))]
                  (stats/format-percent value total)))})

(defn- headlines-chart []
  (draw-chart "headlines-chart"
              {:type "bar"
               :options {:plugins {:legend {:display false}
                                   :datalabels {:display false}}
                         :indexAxis "y"
                         :scales {:x {:title {:display true
                                              :text "kWh"}}}}
               :data {:labels ["Average UK consumption" "Our consumption" "Generated"]
                      :datasets [{:label "Total energy"
                                  :data [stats/avg-uk-annual-consumption
                                         (apply + (map :consumed stats-2022)) ;;3785
                                         (apply + (map :to-inverter stats-2022))]
                                  :backgroundColor [styles/inefficiency-grey
                                                    styles/consumption-orange
                                                    styles/solar-green]}]}}))

(defn- generation-chart []
  (draw-chart "generation-chart"
              {:type "pie"
               :options {:responsive true
                         :resizeDelay 10
                         :plugins {:legend legend
                                   :datalabels percent-datalabels}}
               :data {:labels ["Consumed directly" "Battery" "Export" "Inefficiency"]
                      :datasets [{:label "Generated energy"
                                  :data [(apply + (map :inverter-to-house stats-2022)) ;; 1906
                                         (apply + (map :to-battery stats-2022)) ;; 1446
                                         (apply + (map :to-grid stats-2022)) ;; 1792
                                         (apply + (map #(+ (:battery-lost %)
                                                           (:inverter-lost %))
                                                       stats-2022))]
                                  :backgroundColor [styles/consumption-orange
                                                    styles/battery-blue
                                                    styles/export-purple
                                                    styles/inefficiency-grey]}]}}))

(defn- consumption-chart []
  (draw-chart "consumption-chart"
              {:type "pie"
               :options {:plugins {:legend legend
                                   :datalabels percent-datalabels}}
               :data {:labels ["Solar" "Battery" "Grid"]
                      :datasets [{:label "Consumed energy"
                                  :data [(apply + (map :inverter-to-house stats-2022))
                                         (apply + (map :from-battery stats-2022))
                                         (apply + (map :from-grid stats-2022))]
                                  :backgroundColor [styles/solar-green
                                                    styles/battery-blue
                                                    styles/import-red]}]}}))

(def consumption-scales
  {:x {:type "time"
       :stacked true
       :title {:text "Date"}}
   :y {:stacked true
       :min 0
       :title {:text "kWh"
               :display true}}})

(defn- daily-generation-chart []
  (let [day-count 365]
    (draw-chart "daily-generation-chart"
                {:type "bar"
                 :options {:plugins {:legend legend
                                     :datalabels {:display false}}
                           :scales consumption-scales}
                 :data {:labels (take day-count (map #(js/Date. (:date %)) stats-2022))
                        :datasets [{:label "Consumed directly"
                                    :data (take day-count (map :inverter-to-house stats-2022))
                                    :backgroundColor styles/consumption-orange}
                                   {:label "Battery"
                                    :data (take day-count (map :to-battery stats-2022))
                                    :backgroundColor styles/battery-blue}
                                   {:label "Export"
                                    :data (take day-count (map :to-grid stats-2022))
                                    :backgroundColor styles/export-purple}
                                   {:label "Inefficiency"
                                    :data (take day-count (map #(+ (:inverter-lost %)
                                                                   (:battery-lost %))
                                                               stats-2022))
                                    :backgroundColor styles/inefficiency-grey}]}})))

(defn- daily-consumption-chart []
  (let [day-count 365]
    (draw-chart "daily-consumption-chart"
                {:type "bar"
                 :options {:plugins {:legend legend
                                     :datalabels {:display false}}
                           :scales consumption-scales}
                 :data {:labels (take day-count (map #(js/Date. (:date %)) stats-2022))
                        :datasets [{:label "Solar"
                                    :data (take day-count (map :inverter-to-house stats-2022))
                                    :backgroundColor styles/solar-green}
                                   {:label "Battery"
                                    :data (take day-count (map :from-battery stats-2022))
                                    :backgroundColor styles/battery-blue}
                                   {:label "Grid"
                                    :data (take day-count (map :from-grid stats-2022))
                                    :backgroundColor styles/import-red}]}})))

(defn- daily-money-chart []
  (let [day-count 365]
    (draw-chart "daily-money-chart"
                {:type "bar"
                 :options {:plugins {:legend legend
                                     :datalabels {:display false}}
                           :scales {:x {:type "time"
                                        :stacked true
                                        :title {:text "Date"}}
                                    :y {:stacked true
                                        :title {:text "£"
                                                :display true}}}}
                 :data {:labels (take day-count (map #(js/Date. (:date %)) stats-2022))
                        :datasets
                        [{:label "Export"
                          :order 1
                          :data (take day-count
                                      (map (fn [stat]
                                             (let [unit-price (/ (stats/unit-price :export stat) 100)]
                                               (* unit-price (:to-grid stat))))
                                           stats-2022))
                          :backgroundColor styles/export-purple}
                         {:label "Import"
                          :order 1
                          :data (take day-count
                                      (map (fn [stat]
                                             (let [unit-price (/ (stats/unit-price :import stat) 100)]
                                               (* -1 unit-price (:from-grid stat))))
                                           stats-2022))
                          :backgroundColor styles/import-red}]}})))

(defn monthly-bills-chart []
  (draw-chart "monthly-bills-chart"
              {:type "line"
               :options {:plugins {:legend legend
                                   :datalabels {:display false}}
                         :scales {:x {:type "time"
                                      :title {:text "Date"}}
                                  :y {:title {:text "£"
                                              :display true}}}}
               :data
               (let [month-groups (->> stats-2022
                                       (group-by #(.getMonth (js/Date. (:date %))))
                                       (sort-by key)
                                       (map val))]
                 {:labels (map #(js/Date. (:date (first %))) month-groups)
                  :datasets [{:label "With solar"
                              :fill true
                              :data (map (fn [stats]
                                           (let [import-price (/ (stats/unit-price :import (first stats)) 100)
                                                 export-price (/ (stats/unit-price :export (first stats)) 100)]
                                             (- (* import-price (apply + (map :from-grid stats)))
                                                (* export-price (apply + (map :to-grid stats))))))
                                         month-groups)
                              :backgroundColor styles/solar-green}
                             {:label "Without solar"
                              :fill true
                              :data (map (fn [stats]
                                           (let [unit-price (/ (stats/unit-price :import (first stats)) 100)]
                                             (* unit-price (apply + (map :consumed stats)))))
                                         month-groups)
                              :backgroundColor styles/consumption-orange}]})}))

(defn payoff-chart []
  (draw-chart "payoff-chart"
              {:type "line"
               :options {:plugins {:legend legend
                                   :datalabels {:display false}}
                         :scales {:x {:title {:text "Year"
                                              :display true}}
                                  :y {:title {:text "£"
                                              :display true}}}}
               :data
               (let [grid-avoided (* stats/assumed-future-import-price
                                     (- (stats/total :consumed)
                                        (stats/total :from-grid)))
                     export (stats/total-cost :export :to-grid)
                     annual-value (/ (+ grid-avoided export) 100)
                     years-to-show 20]
                 {:labels (range years-to-show)
                  :datasets [{:label "Payoff"
                              :fill true
                              :data (map (fn [year]
                                           (- (* year annual-value 0.993)
                                              9000
                                              (if (<= 10 year)
                                                3000
                                                0)))
                                         (range years-to-show))
                              :backgroundColor styles/solar-green}]})}))

(defn init []
  (headlines-chart)
  (generation-chart)
  (consumption-chart)
  (daily-generation-chart)
  (daily-consumption-chart)
  (daily-money-chart)
  (monthly-bills-chart)
  (payoff-chart))
