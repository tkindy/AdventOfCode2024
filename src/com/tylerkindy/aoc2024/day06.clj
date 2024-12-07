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

(defn build-path [{[width height] :dimensions
                   :keys [start obstacles]}]
  (letfn [(out-of-bounds? [[x y]]
            (or (< x 0)
                (>= x width)
                (< y 0)
                (>= y height)))
          (move [loc dir]
            (let [attempted-loc (vec/+ loc dir)]
              (if (obstacles attempted-loc)
                [loc (next-dir dir)]
                [attempted-loc dir])))
          (help [[loc dir]]
            (if (out-of-bounds? loc)
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

(defn index-obstacles [obstacles]
  (letfn [(build-index [key-fn val-fn]
            (-> (group-by key-fn obstacles)
                (update-vals #(->> %
                                   (map val-fn)
                                   sort
                                   (into [])))))]
    {:rows (build-index second first)
     :columns (build-index first second)}))

(defn guard->segment [[[x y] [dx _ :as dir]] obstacle-index]
  (let [[index-key first-coord second-coord build-start]
        (if (= dx 0)
          [:columns x y (fn [y] [x y])]
          [:rows y x (fn [x] [x y])])]
    {:start (build-start (as-> (obstacle-index index-key) v
                           (get v first-coord [])
                           (take-while #(< % second-coord) v)
                           (or (last v) -1)
                           (inc v)))
     :dir dir}))

(defn could-loop? [[loc dir] segments obstacle-index]
  (contains? segments
             (guard->segment [loc (next-dir dir)] obstacle-index)))

(defn obstacle-candidates [{:keys [obstacles] :as info}]
  (let [obstacle-index (index-obstacles obstacles)]
    (->> info
         build-path
         (reduce (fn [[candidates segments] [loc dir :as guard]]
                   [(if (could-loop? guard segments obstacle-index)
                      (conj candidates (vec/+ loc dir))
                      candidates)
                    (conj segments (guard->segment guard obstacle-index))])
                 [#{} #{}])
         first)))

(defn part2 [info]
  (count (obstacle-candidates info)))

(defn -main []
  (let [info (parse-input (slurp "input/day06.txt"))]
    (println "Part 1:" (part1 info))
    (println "Part 2:" (part2 info))))
