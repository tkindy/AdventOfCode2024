(ns com.tylerkindy.aoc2024.day08
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [com.tylerkindy.aoc2024.util.vector :as vec]))

(defn parse-input [input]
  (let [lines (str/split-lines input)]
    {:dimensions [(count (first lines))
                  (count lines)]
     :antennas (->>
                lines
                (map-indexed vector)
                (reduce (fn [antennas [y line]]
                          (->> line
                               (map-indexed vector)
                               (reduce (fn [antennas [x c]]
                                         (if (= c \.)
                                           antennas
                                           (update antennas
                                                   c
                                                   (fn [antennas]
                                                     (conj (or antennas #{})
                                                           [x y])))))
                                       antennas)))
                        {}))}))

(defn all-pairs [antennas]
  (->> (for [l antennas, r antennas]
         (when (not= l r)
           [l r]))
       (filter some?)
       (into #{})))

(defn in-bounds? [[x y] [width height]]
  (and (>= x 0)
       (< x width)
       (>= y 0)
       (< y height)))

(defn antinodes [[l r] generator dimensions]
  (let [v (vec/- r l)]
    (->> (generator l v)
         (take-while #(in-bounds? % dimensions)))))

(defn unique-antinodes [{:keys [dimensions antennas]} generator]
  (->> antennas
       vals
       (mapcat all-pairs)
       (mapcat #(antinodes % generator dimensions))
       (into #{})))

(defn count-antinodes [world generator]
  (count (unique-antinodes world generator)))

(defn part1-generator [l v]
  [(vec/+ l (vec/* v 2))])

(defn part1 [world]
  (count-antinodes world part1-generator))

(defn part2-generator [l v]
  (lazy-seq (cons l
                  (part2-generator (vec/+ l v) v))))

(defn part2 [world]
  (count-antinodes world part2-generator))

(defn -main []
  (let [world (parse-input (slurp "input/day08.txt"))]
    (println "Part 1:" (part1 world))
    (println "Part 2:" (part2 world))))
