(ns reveal.slides
  (:require [reveal.stats :as stats :refer [stats-2022]]
            [reveal.styles :as styles :refer [style]]))

(def title-page
  [:section
   {:data-background-image "img/title-screen.jpg"}
   [:h1.r-fit-text (style {:color "#111"
                           :text-shadow "0 0 10px #fff, 0 0 20px #fff, 0 0 30px yellow "})
    "Sunshine"]
   [:h5 (style {:margin-top "5em"
                :color "#111"
                :text-shadow "0 0 10px #fff, 0 0 20px #fff, 0 0 30px yellow "})
    "A year of solar power"]
   [:audio
    {:controls true :data-autoplay true}
    [:source {:src "audio/sunshine-theme.mp3"
              :type "audio/mpeg"}]]])

(def intro
  [:section
   [:h2 "My home the power plant"]
   [:img.r-stretch.frame {:src "img/house.jpg"}]
   [:aside.notes
    [:ul
     [:li "One year ago we had solar panels installed"]
     [:li "I've learned a lot and been asked for advice"]
     [:li "Here are all the calculations and quantified reasoning"]]]])

(def motivation-1
  [:section
   [:h2 "Why?"]
   [:img.r-stretch.frame {:src "img/david-attenborough.jpg"}]
   [:aside.notes
    [:ul
     [:li "David Attenborough has been observing the natural world since 1952"]
     [:li "His documentaries highlight the damage humans are inflicting on the natural world"]
     [:li "Climate change is making up to 100k species extinct per year"]
     [:li "By July we have already used all the resources the Earth can sustainably produce in a year"]]]])

(def motivation-2
  [:section
   {:data-background-image "img/global-surface-area.jpg"}
   [:p [:small (style {:color "black"}) "Credit: Tim Urban"]]
   [:aside.notes
    [:ul
     [:li "The sun provides 1kW of power to every 1m² on earth"]
     [:li "This energy is free and release no greenhouse gases"]
     [:li "The total energy hitting the earth in one hour (173,000TWh) could power it for a year"]]]])

(def planning
  [:section
   [:h2 "Planning"]
   [:div (style {:margin-top "2em"
                 :display "flex"
                 :justify-content "space-evenly"
                 :gap "20px"})
    [:div
     [:div (style {:font-size "4em"}) "🌤"]
     "Sunny roof"]
    [:div
     [:div (style {:font-size "4em"}) "✍🏽"]
     "Planning permission"]
    [:div
     [:div (style {:font-size "4em"}) "💷"]
     "Quotes"]]
   [:aside.notes
    [:ul
     [:li "A south-oriented roof is obviously best"]
     [:li "If you have an east/west oriented roof it can still work but you'll need panels on both aspects"]
     [:li "Solar is a 'permitted development' meaning you don't need permission except for special cases, like conservation areas"]
     [:li "It is still worth checking your neighbours are ok with the idea"]
     [:li "As always, getting 3 quotes is a sensible idea"]]]])

(def equipment
  [:section
   [:h2 "Equipment"]
   [:div (style {:margin-top "2em"
                 :display "flex"
                 :justify-content "space-evenly"
                 :gap "20px"})
    [:div
     [:div [:img.frame {:src "img/panels.jpg"}]]
     "Solar panels & optimisers"]
    [:div
     [:div [:img.frame {:src "img/inverter.jpg"}]]
     "Inverter & batteries"]
    [:div
     [:div [:img.frame {:src "img/pigeon-proofing.jpg"}]]
     "Pigeon proofing"]]
   [:aside.notes
    [:ul
     [:li "Modern panels typically produce about 400W each"]
     [:li "The inverter converts your solar power to domestic voltage and frequency"]
     [:li "Batteries increase your utilisation of your solar power by storing for later what you don't need now"]]]])

(def sizing
  [:section
   [:h2 "Decisions"]
   [:div (style {:display "flex"
                 :justify-content "space-evenly"
                 :flex-wrap "wrap"
                 :gap "1em"})
    [:div
     [:div (style {:font-size "2em"
                   :color styles/consumption-orange})
      "7.5kWh"]
     "Average UK daily electricity use"]
    [:div
     [:div (style {:font-size "2em"
                   :color styles/export-purple})
      "3.6kW"]
     "Domestic export power cap"]
    [:div
     [:div (style {:font-size "2em"
                   :color styles/solar-green})
      "400W"]
     "Solar panel power output"]
    [:div
     [:div (style {:font-size "2em"
                   :color styles/battery-blue})
      "£500"]
     "Cost per 1kWh battery"]]
   [:aside.notes
    [:ul
     [:li "With two young children we average at least 10kWh a day"]
     [:li "Most of the time you are not at full power, so exceeding 3.6kW panel capacity is worth it"]
     [:li "You will ideally be able to charge the battery to 100% on a sunny day and it will last until the sun comes up tomorrow"]
     [:li "A typical domestic installation will be 4-5kW of panels and 5kWh of battery capacity"]]]])

(def installation
  [:section
   [:h2 "Installation"]
   [:div (style {:display "flex"
                 :justify-content "space-evenly"
                 :gap "20px"})
    [:div "📅 1 day"]
    [:div "🧗 3 people"]
    [:div "🚧 Scaffolding"]]
   [:div.r-stretch
    [:img.frame {:src "img/installation.jpg"}]]
   [:aside.notes
    [:ul
     [:li "Installation was quick and clean"]
     [:li "Mounting points slide under roof tiles and fix to rafters"]
     [:li "Rails attach to the mounting points and the panels are fixed to the rails"]
     [:li "Export meter and cutout switches added to distribution board"]
     [:li "Pigeon proofing was added later and took one person a few hours"]]]])

(def energy-section
  [:section {:data-autoslide 6000
             :data-background-image "img/section-divider.jpg"
             :data-background-opacity "0.7"}
   [:h1.r-fit-text "Energy"]
   [:audio
    {:controls false :data-autoplay true}
    [:source {:src "audio/slide-transition.mp3"
              :type "audio/mpeg"}]]])

(def headlines
  [:section
   [:h2 "Energy"]
   [:div.r-stretch
    [:canvas#headlines-chart]]
   [:aside.notes
    [:ul
     [:li "We generated enough to power "
      (.toFixed (/ (stats/total :to-inverter) stats/avg-uk-annual-consumption) 1)
      " average UK houses"]
     [:li "We generated "
      (stats/format-percent (- (stats/total :to-inverter) (stats/total :consumed))
                            (stats/total :consumed))
      " more than we used!"]
     [:li "On sunny days we exported but on dark days we imported, so let's look at a breakdown"]]]])

(def generation
  [:section
   [:h2 "Annual generation"]
   [:p "Total: " [:span (style {:color styles/solar-green}) (stats/total :to-inverter) " kWh"]
    [:div.r-stretch {:style "display: flex; justify-content: center;"}
     [:canvas#generation-chart]]]
   [:aside.notes
    [:li (stats/percent :inverter-to-house :to-inverter)
     " went straight into the plug sockets in our house"]
    [:li (stats/percent :to-battery :to-inverter)
     " went into the battery for later consumption"]
    [:li (stats/percent :to-grid :to-inverter)
     " was exported to the grid - "
     (stats/total :to-grid) " kWh, enough to run an average home for "
     (js/Math.round (/ (/ (stats/total :to-grid) 7.5) 30)) " months"]
    [:li (stats/format-percent (+ (stats/total :inverter-lost)
                                  (stats/total :battery-lost))
                               (stats/total :to-inverter))
     " was lost to inefficiency in the inverter and the battery charge/discharge cycle"]]])

(def daily-generation
  [:section
   [:h2 "Daily generation"]
   [:div {:style "width: 100%; margin: 0 auto;"}
    [:canvas#daily-generation-chart]]
   [:aside.notes
    [:ul
     [:li "We had " (count (filter #(> 0.5 %) (stats/series :to-grid))) " days when we exported power to the grid"]
     [:li "On our best day we exported " (apply max (stats/series :to-grid)) " kWh"]
     [:li "On the worst days we only generate about " (first (drop 5 (sort (filter pos? (stats/series :to-inverter))))) " kWh"]]]])

(def consumption
  [:section
   [:h2 "Annual consumption"]
   [:p "Total: " [:span (style {:color styles/consumption-orange}) (stats/total :consumed) " kWh"]]
   [:div.r-stretch {:style "display: flex; justify-content: center;"}
    [:canvas#consumption-chart]]
   [:aside.notes
    [:ul
     [:li (stats/percent :inverter-to-house :consumed)
      " of all our electricity came directly from the sun"]
     [:li (stats/percent :from-battery :consumed) " came from the batteries"]
     [:li "Only " (stats/percent :from-grid :consumed) " came from the grid"]]]])

(def daily-consumption
  [:section
   [:h2 "Daily consumption"]
   [:div.r-stretch
    [:canvas#daily-consumption-chart]]
   [:aside.notes
    [:ul
     [:li "Our consumption varied between "
      (first (drop 5 (sort (filter pos? (stats/series :consumed)))))
      " kWh and " (apply max (stats/series :consumed)) " kWh"]
     [:li "We had " (count (filter #(> 0.5 %) (stats/series :from-grid))) " days 'off-grid'"]]]])

(def impact-section
  [:section {:data-autoslide 6000
             :data-background-image "img/section-divider.jpg"
             :data-background-opacity "0.7"}
   [:h1.r-fit-text "Impact"]
   [:audio
    {:controls false :data-autoplay true}
    [:source {:src "audio/slide-transition.mp3"
              :type "audio/mpeg"}]]])

(def environmental-impact
  (let [grid-avoided (+ (stats/total :inverter-to-house)
                        (stats/total :from-battery)
                        (stats/total :to-grid))
        carbon-avoided (js/Math.round (* grid-avoided stats/uk-grid-carbon-intensity))
        carbon-avoided-tonne (/ carbon-avoided 1000)]
    [:section
     [:h2 "Environment"]
     [:h3.r-fit-text (style {:color styles/solar-green})
      carbon-avoided "kg CO₂ avoided"]

     [:p "The equivalent of..."]
     [:ul
      [:li "Driving a petrol car "
       [:span (style {:color styles/import-red}) (* carbon-avoided-tonne 7500) "km"]]
      [:li "Flying from "
       [:span (style {:color styles/import-red}) "London to Cairo"]]
      [:li "Producing "
       [:span (style {:color styles/import-red}) (js/Math.round (/ carbon-avoided 36)) "kg"]
       " of beef"]]

     [:p
      [:small "* UK grid carbon intensity was " (* 1000 stats/uk-grid-carbon-intensity) "g CO₂/kWh in 2022"]]
     [:aside.notes
      [:ul
       [:li "This is calculated from the carbon intensity of the UK grid"]
       [:li "2022 was the UK's second lowest carbon intensity score, so the grid is getting cleaner"]
       [:li "What this puts into perspective for me is how carbon intensive flying and beef are"]]]]))

(def money-impact
  (let [grid-avoided (- (stats/total-cost :import :consumed)
                        (stats/total-cost :import :from-grid))
        exported (stats/total-cost :export :to-grid)]
    [:section
     [:h2 "Money"]

     [:div (style {:display "flex"
                   :justify-content "space-evenly"
                   :flex-wrap "wrap"
                   :gap "1em"})
      [:div
       [:div (style {:font-size "2em"
                     :color styles/import-red})
        (js/Math.round (/ (stats/total-cost :import :from-grid)
                          (stats/total :from-grid)))
        "p/kWh"]
       "Average import price"]
      [:div
       [:h1 (style {:color styles/solar-green})
        "£"
        (js/Math.round (/ grid-avoided 100))]
       "Saved from import"]

      [:div (style {:flex-basis "100%"
                    :font-style "italic"})
       [:div (style {:font-size "2em"
                     :color styles/solar-green})
        "£"
        (js/Math.round
         (* (/ stats/assumed-future-import-price 100)
            (- (stats/total :consumed)
               (stats/total :from-grid))))]
       "Projected saving next year"]

      [:div
       [:div (style {:font-size "2em"
                     :color styles/export-purple})
        (js/Math.round (/ (stats/total-cost :export :to-grid)
                          (stats/total :to-grid)))
        "p/kWh"]
       "Average export price"]
      [:div
       [:h1 (style {:color styles/export-purple})
        "£" (js/Math.round (/ exported 100))]
       "Export earned"]]

     [:aside.notes
      [:ul
       [:li "Our panels generated a total value of £"
        (js/Math.round (/ (+ grid-avoided exported) 100))]
       [:li "Energy prices went crazy in 2022 but it seems like higher prices are here to stay"]]]]))

(def daily-money
  [:section
   [:h2 "Daily profit 'n' loss"]
   [:div.r-stretch
    [:canvas#daily-money-chart]]

   [:aside.notes
    [:ul
     [:li "On the worst days the panels make no difference"]
     [:li "On the best day we coined in £"
      (.toFixed
       (apply max
              (map (fn [stat]
                     (let [unit-price (/ (stats/unit-price :export stat) 100)]
                       (* unit-price (:to-grid stat))))
                   stats-2022))
       2)]]]])

(def monthly-bills
  [:section
   [:h2 "Monthly bills"]
   [:div.r-stretch
    [:canvas#monthly-bills-chart]]
   [:aside.notes
    [:ul
     [:li "Even in winter our bills are " (stats/format-percent (- 125 95) 125) " cheaper"]
     [:li "We had 8 months of zero or negative electricity bills"]]]])

(def payoff
  [:section
   [:h2 "Payoff"]
   (let [grid-avoided (* stats/assumed-future-import-price
                         (- (stats/total :consumed)
                            (stats/total :from-grid)))
         export (stats/total-cost :export :to-grid)
         total-value (/ (+ grid-avoided export) 100)]
     [:p "Investment: "
      [:span (style {:color styles/import-red})
       "£" stats/installation-cost]
      ", annual return: "
      [:span (style {:color styles/solar-green})
       "£" (js/Math.round total-value)]])
   [:div.r-stretch
    [:canvas#payoff-chart]]
   [:aside.notes
    [:ul
     [:li "Assuming inflation and energy price growth are equal"]
     [:li "Assuming the inverter needs replacing for £3k after 10 years"]
     [:li "Assuming 0.7% annual performance drop off"]]]])

;; surprisingly this says batteries aren't worth it!
#_(def batteries
  [:section
   [:h2 "Are batteries worth it?"]
   (let [export-lost (stats/total-cost :export :to-battery)
         grid-avoided (stats/total-cost :import :from-battery)]
     [:div (style {:display "flex"
                   :justify-content "space-evenly"
                   :flex-wrap "wrap"
                   :gap "1em"})
      [:div
       [:div (style {:font-size "2em"
                     :color styles/export-purple})
        "£"
        (js/Math.round (/ export-lost 100))]
       "Export lost"]
      [:div
       [:h1 (style {:color styles/import-red})
        "£"
        (js/Math.round (/ grid-avoided 100))]
       "Saved from import"]])
   [:aside.notes
    [:ul
     [:li "Assuming inflation and energy price growth are equal"]
     [:li "Assuming the inverter needs replacing for £3k after 10 years"]
     [:li "Assuming 0.7% annual performance drop off"]]]])

(def behavioural-impact
  [:section
   [:h2 "Behaviour"]
   [:img.frame {:src "img/making-hay.jpg"}]
   [:p "\"Make hay while the sun shines\""]
   [:aside.notes
    [:ul
     [:li "We aim to run our energy-intensive appliances (washing machine, dishwasher, tumble dryer) during the day"]
     [:li "This makes most efficient use of the energy"]
     [:li "We are better at turning off unused lights and equipment to save a few watts"]
     [:li "We can help ease peak pressure on the grid by priming the battery for energy saving sessions"]
     [:li "This helps ensure the grid does not need to fire up dirty coal plants"]]]])

(def stats
  [:section
   [:h2 "Stats"]
   [:img.frame {:src "img/luxpower-stats.png"}]
   [:aside.notes
    [:ul
     [:li "I have become slightly obsessed with stats"]
     [:li "I've been known to say 'quick, turn the dishwasher on'"]
     [:li "These stats are collected and provided by the manufacturer of the inverter"]
     [:li "Our inverter has an API so hacking your own automation is possible"]]]])

(def kaneda
  [:section
   ;;{:data-background-image "img/kaneda.jpg"}
   [:h2 "What do you see?"]
   [:img.frame {:src "img/kaneda.jpg"}]
   [:aside.notes
    [:ul
     [:li "Any questions?"]]]])

;; todo screenshot of luxpower during the day

(def skeleton
  [:section
   [:h2 ""]
   [:ul
    [:li ""]]
   [:aside.notes
    [:ul
     [:li]]]])

(defn all
  []
  [title-page
   intro
   motivation-1
   motivation-2
   planning
   equipment
   sizing
   installation

   energy-section
   headlines
   generation
   daily-generation
   consumption
   daily-consumption

   impact-section
   environmental-impact
   money-impact
   daily-money
   monthly-bills
   payoff
   ;;batteries
   behavioural-impact
   stats

   kaneda])
