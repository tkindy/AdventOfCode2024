(ns com.tylerkindy.aoc2024.day09
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       str/trim
       (map (comp parse-long str))
       (map-indexed vector)
       (mapcat (fn [[i length]]
                 (repeat length
                         (if (even? i)
                           (/ i 2)
                           nil))))
       vec))

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

(defn file-stats [disk]
  (loop [i 0
         result []]
    (cond
      (>= i (count disk)) result
      (nil? (nth disk i)) (recur (inc i) result)
      :else (let [id (nth disk i)
                  length (->> disk
                              (drop i)
                              (take-while (partial = id))
                              count)]
              (recur (+ i length)
                     (conj result {:id id
                                   :start i
                                   :length length}))))))

(defn open-spaces [disk]
  (letfn [(help [disk start-i]
            (lazy-seq
             (if (empty? disk)
               nil
               (let [nil-i (->> disk
                                (map-indexed vector)
                                (filter (fn [[_ value]] (nil? value)))
                                ffirst)]
                 (if nil-i
                   (let [length (->> disk
                                     (drop nil-i)
                                     (take-while nil?)
                                     count)]
                     (cons {:start (+ start-i nil-i)
                            :length length}
                           (help (subvec disk (+ nil-i length))
                                 (+ start-i nil-i length))))
                   nil)))))]
    (help disk 0)))

(defn find-space [disk req-length]
  (->> disk
       open-spaces
       (filter (fn [{:keys [length]}]
                 (>= length req-length)))
       first
       :start))

(defn swap [disk i j length]
  (letfn [(set-region [disk start region]
            (loop [disk disk
                   i 0]
              (if (>= i (count region))
                disk
                (recur (assoc disk
                              (+ start i)
                              (nth region i))
                       (inc i)))))]
    (let [i-region (subvec disk i (+ i length))
          j-region (subvec disk j (+ j length))]
      (-> disk
          (set-region i j-region)
          (set-region j i-region)))))

(defn defrag-contiguous [disk]
  (->> disk
       file-stats
       (sort-by :id >)
       (reduce (fn [disk {:keys [start length]}]
                 (let [new-i (find-space (subvec disk 0 start) length)]
                   (if (some? new-i)
                     (swap disk start new-i length)
                     disk)))
               disk)))

(defn part2 [disk]
  (-> disk
      defrag-contiguous
      calc-checksum))

(defn -main []
  (let [disk (parse-input (slurp "input/day09.txt"))]
    (println "Part 1:" (part1 disk))
    (println "Part 2:" (part2 disk))))
