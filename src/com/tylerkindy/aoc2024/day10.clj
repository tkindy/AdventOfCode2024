(ns com.tylerkindy.aoc2024.day10
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (->> line
       (mapv (comp parse-long str))))

(defn parse-input [input]
  (->> input
       str/split-lines
       (mapv parse-line)))

(defn -main []
  (let [topo (parse-input (slurp "input/day10.txt"))]))
