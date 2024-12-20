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

(defn accessible-trails [loc topo]
  (letfn [(help [loc path]
            (let [height (height-at loc topo)
                  path (conj path loc)]
              (if (= height 9)
                #{path}
                (->> (neighbors loc)
                     (filter #(= (height-at % topo)
                                 (inc height)))
                     (map #(help % path))
                     (apply set/union)))))]
    (help loc [])))

(defn accessible-peaks [loc topo]
  (->> (accessible-trails loc topo)
       (map last)
       (into #{})))

(defn part1 [topo]
  (->> topo
       find-trailheads
       (map #(count (accessible-peaks % topo)))
       (apply +)))

(defn part2 [topo]
  (->> topo
       find-trailheads
       (map #(count (accessible-trails % topo)))
       (apply +)))

(defn -main []
  (let [topo (parse-input (slurp "input/day10.txt"))]
    (println "Part 1:" (part1 topo))
    (println "Part 2:" (part2 topo))))
