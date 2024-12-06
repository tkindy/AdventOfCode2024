(ns com.tylerkindy.aoc2024.day06
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [lines (str/split-lines input)]
    (as-> lines v
      (map-indexed vector v)
      (reduce (fn [info [y line]]
                (->> line
                     (map-indexed vector)
                     (reduce (fn [info [x c]]
                               (let [coord [x y]]
                                 (case c
                                   \# (update info :obstacles conj coord)
                                   \^ (assoc info :start coord)
                                   info)))
                             info)))
              {:obstacles #{}}
              v)
      (assoc v :dimensions [(count (first lines)) (count lines)]))))

(defn -main []
  (let [info (parse-input (slurp "input/day06.txt"))]))
