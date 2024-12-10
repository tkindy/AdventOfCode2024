(ns com.tylerkindy.aoc2024.day09
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       str/trim
       (map (comp parse-long str))
       (reduce (fn [[disk id free?] length]
                 (let [value (if free? nil id)]
                   [(vec (concat disk (repeat length value)))

                    (if free? id (inc id))
                    (not free?)]))
               [[] 0 false])
       first))

(defn defrag [disk]
  (loop [disk disk
         to-i 0
         from-i (dec (count disk))]
    (cond
      (>= to-i from-i) disk
      (some? (nth disk to-i)) (recur disk (inc to-i) from-i)
      (nil? (nth disk from-i)) (recur disk to-i (dec from-i))
      :else (recur (-> disk
                       (assoc to-i (nth disk from-i))
                       (assoc from-i nil))
                   (inc to-i)
                   (dec from-i)))))

(defn calc-checksum [disk]
  (->> disk
       (map-indexed (fn [i id]
                      (if (nil? id)
                        0
                        (* i id))))
       (apply +)))

(defn part1 [disk]
  (-> disk
      defrag
      calc-checksum))

(defn -main []
  (let [disk (parse-input (slurp "input/day09.txt"))]
    (println "Part 1:" (part1 disk))))
