(ns reveal.charts)

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

(defn init []
  (js/console.log "initing charts")
  (headlines-chart)
  (generation-chart)
  (consumption-chart))

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
    },

;; consumed = eToUserDay [grid import] + eDisChgDay [from battery] + (eInvDay - eChgDay) [whatever the inverter output was, minus what it used to charge the battery]
