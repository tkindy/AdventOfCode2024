(ns com.tylerkindy.aoc2024.day09)

(defn parse-input [input]
  (->> input
       (map (comp parse-long str))
       (reduce (fn [[disk id free?] length]
                 (let [value (if free? nil id)]
                   [(concat disk (repeat length value))
                    (if free? id (inc id))
                    (not free?)]))
               [[] 0 false])
       first
       vec))

(defn -main []
  (let [disk (parse-input (slurp "input/day09.txt"))]))
