(ns com.tylerkindy.aoc2024.day04
  (:require [com.tylerkindy.aoc2024.util.vector :as v]
            [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       str/split-lines
       (filter (comp not str/blank?))
       (mapv (partial into []))))

(defn all-coordinates [puzzle]
  (->> (for [y (range (count puzzle))
             x (range (count (first puzzle)))]
         [x y])
       (into #{})))

(defn lookup [[x y] puzzle]
  (some-> puzzle
          (get y)
          (get x)))

(defn neighbors [coord]
  (->> (for [dx (range -1 2)
             dy (range -1 2)]
         (v/+ coord [dx dy]))
       (filter (partial not= coord))
       (into #{})))

(defn find-matches [puzzle pred]
  (->> puzzle
       all-coordinates
       (map #(pred % puzzle))
       (apply +)))

(defn xmases-from [coord puzzle]
  (letfn [(help [coord to-match dir]
            (if (= (first to-match) (lookup coord puzzle))
              (if (empty? (rest to-match))
                1
                (help (v/+ coord dir) (rest to-match) dir))
              0))]
    (if (= \X (lookup coord puzzle))
      (->> (neighbors coord)
           (map #(help % "MAS" (v/- % coord)))
           (apply +))
      0)))

(defn part1 [puzzle]
  (find-matches puzzle xmases-from))

(defn cross-words [coord puzzle]
  (->> [[[-1 -1] [1 1]] [[-1 1] [1 -1]]]
       (map (fn [deltas]
              (->> deltas
                   (map (fn [delta]
                          (lookup (v/+ coord delta) puzzle)))
                   sort)))
       (into #{})))

(defn x-mases-from [coord puzzle]
  (letfn [(check-x-mas [coord]
            (= (cross-words coord puzzle) #{[\M \S]}))]
    (if (and (= \A (lookup coord puzzle))
             (check-x-mas coord))
      1
      0)))

(defn part2 [puzzle]
  (find-matches puzzle x-mases-from))

(defn -main []
  (let [puzzle (parse-input (slurp "input/day04.txt"))]
    (println "Part 1:" (part1 puzzle))
    (println "Part 2:" (part2 puzzle))))
