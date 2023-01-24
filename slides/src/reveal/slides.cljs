(ns reveal.slides)

(def title-page
  [:section
   {:data-background-image "https://camo.githubusercontent.com/d6d6cfa4e9bcfb8f2230916364bc3ee85414d74161894acdd3475513f1ef660e/68747470733a2f2f7777772e7072616775657265706f727465722e636f6d2f77702d636f6e74656e742f75706c6f6164732f323030372f30342f73756e7368696e652d6d6f7669652d7265766965772e6a7067"
    :data-background-opacity "0.7"}
   [:h1 {:style "color: black;"} "sunshine"]
   [:h5 {:style "color: black;"} "a year of solar power"]])

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
     [:li "The sun provides 1kW of power to every 1m² on earth"]
     [:li "This energy is free and release no greenhouse gases"]
     [:li "The total energy hitting the earth in one hour (173,000TWh) could power it for a year"]]]])

(def motivation-2
  [:section
   [:img {:src "https://camo.githubusercontent.com/b7fdea748f90e59720578112564dbb537169120b8c255661ef4b56e737ff7cf0/68747470733a2f2f776169746275747768792e636f6d2f77702d636f6e74656e742f75706c6f6164732f323031342f30352f31386d6d3166776f36786c63316a70672e6a7067"}]
   [:p [:small "Credit: Tim Urban"]]])

(defn all
  "Add here all slides you want to see in your presentation."
  []
  [title-page
   intro
   motivation-1
   motivation-2])
