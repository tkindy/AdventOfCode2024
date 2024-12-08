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
  (if (< (count antennas) 2)
    #{}
    (letfn [(help [l rs]
              (->> rs
                   (map (fn [r] #{l r}))
                   (into #{})))]
      (set/union (help (first antennas) (rest antennas))
                 (all-pairs (rest antennas))))))

(defn antinodes [pair]
  (let [l (first pair)
        r (second pair)
        v (vec/- r l)]
    #{(vec/- l v)
      (vec/+ l (vec/* v 2))}))

(defn in-bounds? [[x y] [width height]]
  (and (>= x 0)
       (< x width)
       (>= y 0)
       (< y height)))

(defn unique-antinodes [{:keys [dimensions antennas]}]
  (->> antennas
       vals
       (mapcat all-pairs)
       (mapcat antinodes)
       (filter #(in-bounds? % dimensions))
       (into #{})))

(defn part1 [world]
  (count (unique-antinodes world)))

(defn -main []
  (let [world (parse-input (slurp "input/day08.txt"))]
    (println "Part 1:" (part1 world))))
