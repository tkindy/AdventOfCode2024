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

(defn next-dir [dir]
  (condp = dir
    [0 -1] [1 0]
    [1 0] [0 1]
    [0 1] [-1 0]
    [-1 0] [0 -1]))

(defn out-of-bounds? [[x y] [width height]]
  (or (< x 0)
      (>= x width)
      (< y 0)
      (>= y height)))

(defn build-path [{:keys [dimensions start obstacles]}]
  (letfn [(move [loc dir]
            (let [attempted-loc (vec/+ loc dir)]
              (if (obstacles attempted-loc)
                [loc (next-dir dir)]
                [attempted-loc dir])))
          (help [[loc dir]]
            (if (out-of-bounds? loc dimensions)
              nil
              (lazy-seq (cons [loc dir]
                              (help (move loc dir))))))]
    (help [start [0 -1]])))

(defn all-positions [info]
  (->> info
       build-path
       (map first)
       (into #{})))

(defn part1 [info]
  (->> info
       all-positions
       count))

(defn obstacle-candidates [{:keys [start dimensions] :as info}]
  (letfn [(loops? [path]
            (loop [path path
                   visited #{}]
              (and (seq path)
                   (let [state (first path)]
                     (or (visited state)
                         (recur (rest path) (conj visited state)))))))]
    (->> info
         build-path
         (map (fn [[loc dir :as guard]]
                [guard (vec/+ loc dir)]))
         (filter #(and (not (out-of-bounds? (nth % 1) dimensions))
                       (not= (nth % 1) start)))
         (map (fn [[_ new-obstacle]]
                [new-obstacle (build-path (update info :obstacles conj new-obstacle))]))
         (filter (comp loops? second))
         (map first)
         (into #{}))))

(defn part2 [info]
  (count (obstacle-candidates info)))

(defn -main []
  (let [info (parse-input (slurp "input/day06.txt"))]
    (println "Part 1:" (part1 info))
    (println "Part 2:" (part2 info))))
