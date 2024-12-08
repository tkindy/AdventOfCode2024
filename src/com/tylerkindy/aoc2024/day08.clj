(ns com.tylerkindy.aoc2024.day08
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [lines (str/split-lines input)]
    {:dimensions [(count (first lines))
                  (count lines)]
     :antennas (->>
                lines
                (map-indexed vector)
                (reduce (fn [antennas [y line]]
                          (->> line
                               (map-indexed vector)
                               (reduce (fn [antennas [x c]]
                                         (if (= c \.)
                                           antennas
                                           (update antennas
                                                   c
                                                   (fn [antennas]
                                                     (conj (or antennas #{})
                                                           [x y])))))
                                       antennas)))
                        {}))}))

(defn -main []
  (let [world (parse-input (slurp "input/day08.txt"))]))
