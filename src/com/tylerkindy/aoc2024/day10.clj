(ns com.tylerkindy.aoc2024.day10
  (:require [clojure.string :as str]
            [com.tylerkindy.aoc2024.util.vector :as vec]
            [clojure.set :as set]))

(defn parse-line [line]
  (->> line
       (mapv (comp parse-long str))))

(defn parse-input [input]
  (->> input
       str/split-lines
       (mapv parse-line)))

(defn height-at [[x y] topo]
  (get-in topo [y x]))

(defn find-trailheads [topo]
  (->> (for [x (range (count (first topo)))
             y (range (count topo))]
         [x y])
       (filter (fn [loc]
                 (zero? (height-at loc topo))))
       (into #{})))

(defn neighbors [loc]
  (->> [[0 1] [0 -1] [-1 0] [1 0]]
       (map (partial vec/+ loc))))

(defn accessible-peaks [loc topo]
  (let [height (height-at loc topo)]
    (if (= height 9)
      #{loc}
      (->> (neighbors loc)
           (filter #(= (height-at % topo)
                       (inc height)))
           (map #(accessible-peaks % topo))
           (apply set/union)))))

(defn score-from [loc topo]
  (count (accessible-peaks loc topo)))

(defn part1 [topo]
  (->> topo
       find-trailheads
       (map #(score-from % topo))
       (apply +)))

(defn -main []
  (let [topo (parse-input (slurp "input/day10.txt"))]
    (println "Part 1:" (part1 topo))))
