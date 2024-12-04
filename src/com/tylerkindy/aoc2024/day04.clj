(ns com.tylerkindy.aoc2024.day04
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       str/split-lines
       (filter (comp not str/blank?))
       (mapv (partial into []))))

(defn -main []
  (let [puzzle (parse-input (slurp "input/day04.txt"))]))
