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

(defn -main []
  (let [equations (parse-input (slurp "input/day07.txt"))]))
