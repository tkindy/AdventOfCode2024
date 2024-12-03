(ns com.tylerkindy.aoc2024.day03
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str]))

(defn find-muls [memory]
  (->> memory
       (re-seq #"mul\((\d+),(\d+)\)")
       (map (fn [[_ l r]] (->> [l r]
                               (mapv parse-long))))))

(defn part1 [memory]
  (->> memory
       find-muls
       (map (partial apply *))
       (apply +)))

(defn parse-instructions [memory]
  (->> memory
       (re-seq #"mul\((\d+),(\d+)\)|do\(\)|don't\(\)")
       (map (fn [[instruction & args]]
              (cond
                (str/starts-with? instruction "mul")
                (->> args
                     (map parse-long)
                     (concat [:mul])
                     (into []))

                (str/starts-with? instruction "don't")
                [:dont]

                :else [:do])))))

(defn part2 [memory]
  (->> memory
       parse-instructions
       (reduce (fn [[acc on] [instruction & args]]
                 (match [instruction on]
                   [:mul true] [(+ acc (apply * args)) on]
                   [:do _] [acc true]
                   [:dont _] [acc false]
                   :else [acc on]))
               [0 true])
       first))

(defn -main []
  (let [memory (slurp "input/day03.txt")]
    (println "Part 1:" (part1 memory))
    (println "Part 2:" (part2 memory))))
