(ns com.tylerkindy.aoc2024.day07
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (letfn [(parse-line [line]
            (let [[test-value operands] (str/split line #":")]
              {:test-value (parse-long test-value)
               :operands (as-> operands v
                           (str/trim v)
                           (str/split v #"\s+")
                           (mapv parse-long v))}))]
    (->> input
         str/split-lines
         (map parse-line))))

(defn possible-results [operands]
  (letfn [(help [acc operands]
            (if (empty? operands)
              [acc]
              (concat (help (+ acc (first operands)) (rest operands))
                      (help (* acc (first operands)) (rest operands)))))]
    (help (first operands) (rest operands))))

(defn possibly-true? [{:keys [test-value operands]}]
  (->> operands
       possible-results
       (some #(= test-value %))
       boolean))

(defn part1 [equations]
  (->> equations
       (filter possibly-true?)
       (map :test-value)
       (apply +)))

(defn -main []
  (let [equations (parse-input (slurp "input/day07.txt"))]
    (println "Part 1:" (part1 equations))))
