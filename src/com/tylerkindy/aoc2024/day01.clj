(ns com.tylerkindy.aoc2024.day01
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map (fn [line] (as-> line val
                         (str/split val #"\s+")
                         (mapv parse-long val))))
       (reduce (fn [[list1 list2] [e1 e2]]
                 [(conj list1 e1) (conj list2 e2)]) 
               ['() '()])
       (mapv reverse)))

(defn part1 [lists]
  (->> lists
       (mapv sort)
       (apply map (fn [e1 e2] (abs (- e1 e2))))
       (apply +)))

(defn -main []
  (let [lists (parse-input (slurp "input/day01.txt"))]
    (println "Part 1:" (part1 lists))))
