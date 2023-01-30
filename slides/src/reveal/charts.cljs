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

(defn- generation-chart []
  (draw-chart "generation-chart"
              {:type "pie"
               :data {:labels ["Direct use" "Battery" "Export"]
                      :datasets [{:label "Generated energy"
                                  :data [1906 1446 1792]}]}}))

(defn- consumption-chart []
  (draw-chart "consumption-chart"
              {:type "pie"
               :data {:labels ["Solar" "Battery" "Grid"]
                      :datasets [{:label "Consumed energy"
                                  :data [1906 389 1490]}]}}))

(defn init []
  (js/console.log "initing charts")
  (generation-chart)
  (consumption-chart))
