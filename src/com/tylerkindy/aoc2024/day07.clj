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

(defn possible-results [operands operators]
  (letfn [(help [acc operands]
            (if (empty? operands)
              [acc]
              (apply concat
                     (map (fn [operator]
                            (help (operator acc (first operands))
                                  (rest operands)))
                          operators))))]
    (help (first operands) (rest operands))))

(defn possibly-true? [{:keys [test-value operands]} operators]
  (as-> operands v
    (possible-results v operators)
    (some #(= test-value %) v)
    (boolean v)))

(defn solve [equations operators]
  (->> equations
       (filter #(possibly-true? % operators))
       (map :test-value)
       (apply +)))

(defn part1 [equations]
  (solve equations [+ *]))

(defn concat-op [l r]
  (parse-long (str l r)))

(defn part2 [equations]
  (solve equations [+ * concat-op]))

(defn -main []
  (let [equations (parse-input (slurp "input/day07.txt"))]
    (println "Part 1:" (part1 equations))
    (println "Part 2:" (part2 equations))))
