(ns reveal.charts)

(defn draw-gen2-chart []
  (when-let [old-chart (js/Chart.getChart "generation-chart")]
    (.destroy old-chart))

  (let [data [{:year 2010 :count 10}
              {:year 2011 :count 30}
              {:year 2012 :count 15}
              {:year 2013 :count 14}
              {:year 2014 :count 22}
              {:year 2015 :count 30}
              {:year 2016 :count 28}]
        chart-opts (clj->js {:type "bar"
                             :data {:labels (map :year data)
                                    :datasets [{:label "Generated energy"
                                                :data (map :count data)}]}})
        canvas (js/document.getElementById "generation-chart")]
    (js/Chart. canvas chart-opts)))

(defn init []
  (js/console.log "initing charts")
  (draw-gen2-chart))
