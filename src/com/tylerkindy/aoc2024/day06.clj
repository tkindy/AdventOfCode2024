(ns com.tylerkindy.aoc2024.day06
  (:require [clojure.string :as str]
            [com.tylerkindy.aoc2024.util.vector :as vec]))

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

(defn build-path [{[width height] :dimensions
                   :keys [start obstacles]}]
  (letfn [(out-of-bounds? [[x y]]
            (or (< x 0)
                (>= x width)
                (< y 0)
                (>= y height)))
          (next-dir [dir]
            (condp = dir
              [0 -1] [1 0]
              [1 0] [0 1]
              [0 1] [-1 0]
              [-1 0] [0 -1]))
          (dir-seq-help [dir]
            (lazy-seq
             (let [dir (next-dir dir)]
               (cons dir
                     (dir-seq-help dir)))))
          (dir-seq [dir]
            (cons dir (dir-seq-help dir)))
          (candidates [loc dir]
            (->> dir
                 dir-seq
                 (map (fn [dir] [(vec/+ loc dir) dir]))))
          (move [loc dir]
            (->> (candidates loc dir)
                 (filter (fn [[loc _]] (not (obstacles loc))))
                 first))
          (help [[loc dir]]
            (if (out-of-bounds? loc)
              nil
              (lazy-seq (cons loc
                              (help (move loc dir))))))]
    (help [start [0 -1]])))

(defn all-positions [info]
  (->> info
       build-path
       (into #{})))

(defn part1 [info]
  (->> info
       all-positions
       count))

(defn -main []
  (let [info (parse-input (slurp "input/day06.txt"))]
    (println "Part 1:" (part1 info))))
