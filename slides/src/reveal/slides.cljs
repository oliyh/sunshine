(ns reveal.slides
  (:require [reveal.stats :as stats :refer [stats-2022]]
            [reveal.styles :as styles :refer [style]]))

(def title-page
  [:section
   {:data-background-image "img/title-screen.jpg"
    :data-background-opacity "0.7"}
   [:h1 {:style "color: black;"} "sunshine"]
   [:h5 {:style "color: black;"} "a year of solar power"]
   [:audio
    {:controls true :data-autoplay true}
    [:source {:src "audio/sunshine-theme.mp3"
              :type "audio/mpeg"}]]])

(def intro
  [:section
   [:p "A year ago my home became a power plant"]])

(def motivation-1
  [:section
   [:h2 "Why?"]
   [:img {:src "img/david-attenborough.jpg"}]
   [:aside.notes
    [:ul
     [:li "David Attenborough has been observing the natural world since 1952"]
     [:li "Climate change is making up to 100k species extinct per year"]
     [:li "By July we have already used all the resources the Earth can sustainably produce in a year"]]]])

(def motivation-2
  [:section
   [:img {:src "img/global-surface-area.jpg"}]
   [:p [:small "Credit: Tim Urban"]]
   [:aside.notes
    [:ul
     [:li "The sun provides 1kW of power to every 1m² on earth"]
     [:li "This energy is free and release no greenhouse gases"]
     [:li "The total energy hitting the earth in one hour (173,000TWh) could power it for a year"]]]])

(def planning
  [:section
   [:h2 "Planning"]
   [:ul
    [:li "South-facing roof"]
    [:li "Planning permission"]
    [:li "Quotes"]]
   ;; todo photo of roof with panels
   [:aside.notes
    [:ul
     [:li "If you have an east/west oriented roof it can still work but you'll need panels on both aspects"]
     [:li "Solar is 'permitted development' meaning you don't need permission except for special cases, like conservation areas"]
     [:li "As always, getting 3 quotes is a sensible idea"]]]])

(def equipment
  [:section
   [:h2 "Equipment"]
   [:ul
    [:li "Panels"]
    [:li "Inverter"]
    [:li "Batteries"]
    [:li "Pigeon proofing!"]]
   ;; todo photos and/or diagram
   [:aside.notes
    [:ul
     [:li "Modern panels can typically produce about 400W each"]
     [:li "The inverter converts your solar power to domestic voltage and frequency"]
     [:li "Batteries increase your utilisation of your solar power by storing for later what you don't need now"]]]])

(def sizing
  [:section
   [:h2 "Decisions, decisions..."]
   [:ul
    [:li "A typical UK home uses 7.5kWh of electricity per day"]
    [:li "A domestic export rate capped at 3.6kW"]
    [:li "Sweet spot between 3.5kW (9 panels) and 5.5kW (14 panels)"]
    [:li "Battery size should be commensurate with generation capacity and domestic usage"]]
   [:aside.notes
    [:ul
     [:li "With two young children we average at least 10kWh a day"]
     [:li "On the sunniest days your output will be capped at 3.6kW, known as clipping"]
     [:li "Most of the time you are not at full power, so exceeding 3.6kW capacity is still worth it"]
     [:li "You will ideally be able to charge the battery to 100% on a sunny day and it will last until the sun comes up tomorrow"]]]])

(def installation
  [:section
   [:h2 "Installation"]
   [:ul
    [:li "One day"]
    [:li "Two on the roof and one electrician"]
    [:li "Mounting points slide under roof tiles and fix to rafters"]
    [:li "Inverter and batteries in the attic"]
    [:li "Export meter and cutout switches added to distribution board"]]
   ;; todo photos
   [:aside.notes
    [:ul
     [:li "Installation was quick and clean"]
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
     [:li "We generated enough to power " (.toFixed (/ (stats/total :to-inverter) stats/avg-uk-annual-consumption) 1) " average UK houses"]
     [:li "We generated " (stats/format-percent (- (stats/total :to-inverter) (stats/total :consumed))
                                                (stats/total :consumed))
      " more than we used!"]
     [:li "On sunny days we exported, and on dark days we imported, so let's look at a breakdown"]]]])

(def generation
  [:section
   [:h2 "Annual generation"]
   [:p "Total: " [:span (style {:color styles/solar-green}) (stats/total :to-inverter) " kWh"]
    [:div.r-stretch {:style "display: flex; justify-content: center;"}
     [:canvas#generation-chart]]]
   [:aside.notes
    [:li (stats/percent :inverter-to-house :to-inverter) " went straight into the plug sockets in our house"]
    [:li (stats/percent :to-battery :to-inverter) " went into the battery for later consumption"]
    [:li (stats/percent :to-grid :to-inverter) " was exported to the grid - "
     (stats/total :to-grid) " kWh, enough to run an average home for " (js/Math.round (/ (/ (stats/total :to-grid) 7.5) 30)) " months"]
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
     [:li (stats/percent :inverter-to-house :consumed) " of all our electricity came directly from the sun"]
     [:li (stats/percent :from-battery :consumed) " came from the batteries"]
     [:li "Only " (stats/percent :from-grid :consumed) " came from the grid"]]]])

(def daily-consumption
  [:section
   [:h2 "Daily consumption"]
   [:div.r-stretch
    [:canvas#daily-consumption-chart]]
   [:aside.notes
    [:ul
     [:li "Our consumption varied between " (first (drop 5 (sort (filter pos? (stats/series :consumed))))) " kWh and " (apply max (stats/series :consumed))]
     [:li "We had " (count (filter #(> 0.5 %) (stats/series :from-grid))) " days off-grid"]]]])

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
     [:p (style {:color styles/solar-green})
      carbon-avoided "kg of CO₂ avoided"]

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
       [:li "What this puts into context for me is how energy intensive flying and beef are"]
       [:li "2022 was the UK's second lowest carbon intensity score"]]]]))

(def money-impact
  [:section
   [:h2 "Money"]
   [:h4 (style {:color styles/import-red}) "Import"]
   [:ul
    [:li "Electricity prices averaged "
     [:span (style {:color styles/import-red})
      (js/Math.round (/ (stats/total-cost :import :from-grid)
                        (stats/total :from-grid)))
      "p/kWh"]]
    (let [grid-avoided (- (stats/total-cost :import :consumed)
                          (stats/total-cost :import :from-grid))]
      [:li "We saved "
       [:span (style {:color styles/solar-green})
        "£" (js/Math.round (/ grid-avoided 100))]
       " from our grid consumption"])
    [:li "With prices now at "
     [:span (style {:color styles/import-red})
      stats/assumed-future-import-price
      "/kWh"]
     " this would be "
     [:span (style {:color styles/solar-green})
      "£" (js/Math.round
           (* (/ stats/assumed-future-import-price 100)
              (- (stats/total :consumed)
                 (stats/total :from-grid))))]]]
   [:h4 (style {:color styles/export-purple}) "Export"]
   [:ul
    [:li "Export rate varies per hour; averaged "
     [:span (style {:color styles/export-purple})
      (js/Math.round (/ (stats/total-cost :export :to-grid)
                        (stats/total :to-grid)))
      "p/kWh"]]
    [:li "We were paid "
     [:span (style {:color styles/export-purple})
      "£" (js/Math.round (/ (stats/total-cost :export :to-grid) 100))] " for export"]]])

(def daily-money
  [:section
   [:h2 "Daily profit 'n' loss"]
   [:div.r-stretch
    [:canvas#daily-money-chart]]])

(def monthly-bills
  [:section
   [:h2 "Monthly bills"]
   [:div.r-stretch
    [:canvas#monthly-bills-chart]]])

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

(def behavioural-impact
  [:section
   [:h2 "Behaviour"]])

#_[:ul
   [:li "On the best days in summer we generate over 30kWh - powering us and two other homes"]
   [:li "We had X days without using the grid at all - sunny all day and battery all night"]
   [:li "On the darkest days we only generate about 0.5kWh, enough to boil the kettle a few times"]]

#_[:ul
 [:li "5144kWh generated"]
 [:li "3785kWh consumed domestically"]
 [:li "1792kWh exported to the grid"]
 [:li "1490kWh imported when it wasn't sunny enough"]
 [:li "1446kWh import saved by battery"]]

;; todo
;; styling - use stylesheet / classes to pick out key numbers on slides
;; font, especially for headlines
;; speaker notes everywhere

;; efficiency - between 70-80% overall?

;; todo
;; value that the batteries saved us, vs their cost
;; photos everywhere

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
   behavioural-impact])
