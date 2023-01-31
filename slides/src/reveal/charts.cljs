(ns reveal.charts
  (:require [reveal.macros :refer-macros [defdata]]))

(defn- draw-chart [id opts]
  (when-let [old-chart (js/Chart.getChart id)]
    (.destroy old-chart))

  (let [chart-opts (clj->js opts)
        canvas (js/document.getElementById id)]
    (js/Chart. canvas chart-opts)))

  ;;- Generated: 5144
  ;;- Consumed: 3785
  ;;- Imported: 1490
  ;;- Exported: 1792
  ;;- Battery: 1446 vs 389!!!

(def solar-green "rgb(50, 168, 82)")
(def consumption-orange "rgb(230, 151, 34)")
(def battery-blue "rgb(34, 155, 230)")
(def export-purple "rgb(230, 34, 214)")
(def import-red "rgb(230, 34, 34)")
(def inefficiency-grey "rgb(150, 150, 150)")

(defn- headlines-chart []
  (draw-chart "headlines-chart"
              {:type "bar"
               :data {:labels ["Consumed"
                               "Generated"]
                      :datasets [{:label "Total energy"
                                  :indexAxis "y"
                                  :data [3785 5144]
                                  :backgroundColor [consumption-orange
                                                    solar-green]}]}}))

(defn- generation-chart []
  (draw-chart "generation-chart"
              {:type "pie"
               :data {:labels ["Direct use" "Battery" "Export"]
                      :datasets [{:label "Generated energy"
                                  :data [1906 1446 1792]
                                  :backgroundColor [consumption-orange
                                                    battery-blue
                                                    export-purple]}]}}))

(defn- consumption-chart []
  (draw-chart "consumption-chart"
              {:type "pie"
               :data {:labels ["Solar" "Battery" "Grid"]
                      :datasets [{:label "Consumed energy"
                                  :data [1906 389 1490]
                                  :backgroundColor [solar-green
                                                    battery-blue
                                                    import-red]}]}}))

(defdata stats-2022 "stats-2022.edn")

(defn- annual-generation-chart []
  (let [day-count 365]
    (draw-chart "annual-generation-chart"
                {:type "bar"
                 :options {:scales {:x {:stacked true}
                                    :y {:stacked true}}}
                 :data {:labels (take day-count (map :date stats-2022));;["Jan" "Feb" "Mar" "Apr" "May" "Jun"]
                        :datasets [{:label "Consumed directly"
                                    :data (take day-count (map :inverter-to-house stats-2022))
                                    :backgroundColor consumption-orange}
                                   {:label "Battery"
                                    :data (take day-count (map :to-battery stats-2022))
                                    :backgroundColor battery-blue}
                                   {:label "Export"
                                    :data (take day-count (map :to-grid stats-2022))
                                    :backgroundColor export-purple}
                                   {:label "Inefficiency"
                                    :data (take day-count (map #(+ (:inverter-lost %)
                                                                   (:battery-lost %))
                                                               stats-2022))
                                    :backgroundColor inefficiency-grey}]}})))

(defn- annual-consumption-chart []
  (let [day-count 365]
    (draw-chart "annual-consumption-chart"
                {:type "bar"
                 :options {:scales {:x {:stacked true}
                                    :y {:stacked true}}}
                 :data {:labels (take day-count (map :date stats-2022));;["Jan" "Feb" "Mar" "Apr" "May" "Jun"]
                        :datasets [{:label "Direct use"
                                    :data (take day-count (map :inverter-to-house stats-2022))
                                    :backgroundColor solar-green}
                                   {:label "Battery"
                                    :data (take day-count (map :from-battery stats-2022))
                                    :backgroundColor battery-blue}
                                   {:label "Grid"
                                    :data (take day-count (map :from-grid stats-2022))
                                    :backgroundColor import-red}]}})))

(defn init []
  (js/console.log "initing charts")
  (headlines-chart)
  (generation-chart)
  (consumption-chart)
  (annual-generation-chart)
  (annual-consumption-chart))
