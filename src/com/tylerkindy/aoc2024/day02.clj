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

(defn safe-deltas? [deltas]
  (and (all-same-sign? deltas)
       (all-within-range? deltas)))

(defn safe? [report]
  (-> report
      deltas
      safe-deltas?))

(defn part1 [reports]
  (->> reports
       (filter safe?)
       count))

(defn dampened-candidates [report]
  (let [len (count report)
        all-dampened (map (fn [i] (concat (take i report)
                                          (take-last (- len i 1) report)))
                          (range len))]
    (conj all-dampened report)))

(defn safe-dampened? [report]
  (->> report
       dampened-candidates
       (some safe?)))

(defn part2 [reports]
  (->> reports
       (filter safe-dampened?)
       count))

(defn -main []
  (let [reports (parse-input (slurp "input/day02.txt"))]
    (println "Part 1:" (part1 reports))
    (println "Part 2:" (part2 reports))))
