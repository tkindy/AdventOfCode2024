(ns com.tylerkindy.aoc2024.day02
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (as-> line v
    (str/split v #" ")
    (mapv parse-long v)))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)))

(defn deltas [report]
  (->> report
       (partition 2 1)
       (mapv #(apply - %))))

(defn all-same-sign? [deltas]
  (let [pred (if (pos? (first deltas))
               pos?
               neg?)]
    (every? pred deltas)))

(defn all-within-range? [deltas]
  (every? #(let [delta (abs %)]
             (<= 1 delta 3))
          deltas))

(defn safe? [deltas]
  (and (all-same-sign? deltas)
       (all-within-range? deltas)))

(defn part1 [reports]
  (->> reports
       (map deltas)
       (filter safe?)
       count))

(defn -main []
  (let [reports (parse-input (slurp "input/day02.txt"))]
    (println "Part 1:" (part1 reports))))
