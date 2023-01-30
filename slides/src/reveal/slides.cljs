(ns reveal.slides)

(def title-page
  [:section
   {:data-background-image "https://camo.githubusercontent.com/d6d6cfa4e9bcfb8f2230916364bc3ee85414d74161894acdd3475513f1ef660e/68747470733a2f2f7777772e7072616775657265706f727465722e636f6d2f77702d636f6e74656e742f75706c6f6164732f323030372f30342f73756e7368696e652d6d6f7669652d7265766965772e6a7067"
    :data-background-opacity "0.7"}
   [:h1 {:style "color: black;"} "sunshine"]
   [:h5 {:style "color: black;"} "a year of solar power"]
   [:audio
    {:controls true :data-autoplay true}
    [:source {:src "audio/sunshine-theme.mp3"
              :type "audio/mpeg"}]]])

(def intro
  [:section
   [:p "A year ago my home became a power plant"]
   [:p "This is my journey"]])

(def motivation-1
  [:section
   [:h2 "Why?"]
   [:img {:src "https://www.wwf.org.uk/sites/default/files/styles/full_image/public/2019-04/david-attenborough_0.jpg?itok=Eh9xJnmM"}]
   [:aside.notes
    [:ul
     [:li "David Attenborough has been observing the natural world since 1952"]
     [:li "Climate change is making up to 100k species extinct per year"]
     [:li "By July we have already used all the resources the Earth can sustainably produce in a year"]]]])

(def motivation-2
  [:section
   [:img {:src "https://camo.githubusercontent.com/b7fdea748f90e59720578112564dbb537169120b8c255661ef4b56e737ff7cf0/68747470733a2f2f776169746275747768792e636f6d2f77702d636f6e74656e742f75706c6f6164732f323031342f30352f31386d6d3166776f36786c63316a70672e6a7067"}]
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

(def generation
  [:section
   [:h2 "Generation"]
   [:ul
    [:li "5144kWh generated"]
    [:li "3785kWh consumed domestically"]
    [:li "1792kWh exported to the grid"]
    [:li "1490kWh imported when it wasn't sunny enough"]
    [:li "1446kWh import saved by battery"]]
   ;; todo - can represent as pie chart maybe?
   ;; todo - does it all add up? looks like nearly 1000 lost in inefficiency?
   [:aside.notes
    [:ul
     [:li "We generated more than we used!"]
     [:li "We exported enough to run an average house for 2/3rds of a year"]
     ;; todo work out impact of battery
     [:li "The battery"]
     ;; todo work out our footprint on the grid as a % of what we actually used
     ]]])

(def generation-2
  [:section
   ;; todo - chart for the whole year
   ;; import, export, generation, consumption, battery saved us
   ;; maybe a chart for each?
   ;; work out how many gridless days there were
   [:h4 "5144 kWh generated"]
   [:p "Where did it go?"]
   [:div {:style "width: 800px;"}
    [:canvas#generation-chart]]
   [:aside.notes
    [:ul
     [:li "On the best days in summer we generate over 30kWh - powering us and two other homes"]
     [:li "We had X days without using the grid at all - sunny all day and battery all night"]
     [:li "On the darkest days we only generate about 0.5kWh, enough to boil the kettle a few times"]]]])

(def consumption
  [:section
   [:h4 "3785 kWh consumed"]
   [:p "Where did it come from?"]
   [:div {:style "width: 800px;"}
    [:canvas#consumption-chart]]
   [:aside.notes
    [:ul
     [:li]]]])

;; todo download all stats, looks like limited to 10 days at a time
;; https://server.luxpowertek.com/WManage/web/analyze/data/export/1332005062/2022-01-01?endDateText=2022-07-01

(def skeleton
  [:section
   [:h2 ""]
   [:ul
    [:li ""]]
   [:aside.notes
    [:ul
     [:li]]]])

(defn all
  "Add here all slides you want to see in your presentation."
  []
  [title-page
   intro
   motivation-1
   motivation-2
   planning
   equipment
   sizing
   installation
   generation
   generation-2
   consumption])
