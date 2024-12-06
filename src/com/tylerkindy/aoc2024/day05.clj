(ns com.tylerkindy.aoc2024.day05
  (:require [clojure.core :as core]
            [clojure.string :as str])
  (:refer-clojure :exclude [sort]))

(defn parse-rule [rule]
  (->> rule
       (re-matches #"(\d+)\|(\d+)")
       (drop 1)
       (mapv parse-long)))

(defn parse-rules [rules]
  (->> rules
       str/split-lines
       (map parse-rule)
       (reduce (fn [acc [before after]]
                 (update acc before (fn [afters]
                                      (conj (or afters #{})
                                            after))))
               {})))

(defn parse-update [update]
  (->> (str/split update #",")
       (mapv parse-long)))

(defn parse-updates [updates]
  (->> updates
       str/split-lines
       (mapv parse-update)))

(defn parse-input [input]
  (let [[rules updates] (str/split input #"\n\n")]
    {:before-rules (parse-rules rules)
     :updates (parse-updates updates)}))

(defn before-pairs [update]
  (->> (for [i (range (count update))]
         (for [j (range (inc i) (count update))]
           [(get update i) (get update j)]))
       (apply concat)))

(defn ordered-pair? [[before after] before-rules]
  (contains? (before-rules before) after))

(defn ordered? [update before-rules]
  (->> update
       before-pairs
       (every? (fn [pair]
                 (ordered-pair? pair before-rules)))))

(defn middle-page [update]
  (nth update (quot (count update) 2)))

(defn part1 [{:keys [before-rules updates]}]
  (->> updates
       (filter #(ordered? % before-rules))
       (map middle-page)
       (apply +)))

(defn sort [update before-rules]
  (->> update
       (map (fn [page]
              [page (or (before-rules page) #{})]))
       (core/sort (comparator (fn [[l l-rules] [r r-rules]]
                                (or (contains? l-rules r)
                                    (not (contains? r-rules l))))))
       (map first)))

(defn part2 [{:keys [before-rules updates]}]
  (->> updates
       (filter #(not (ordered? % before-rules)))
       (map #(sort % before-rules))
       (map middle-page)
       (apply +)))

(defn -main []
  (let [input (parse-input (slurp "input/day05.txt"))]
    (println "Part 1:" (part1 input))
    (println "Part 2:" (part2 input))))
