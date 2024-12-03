(ns com.tylerkindy.aoc2024.day03)

(defn find-muls [memory]
  (->> memory
       (re-seq #"mul\((\d+),(\d+)\)")
       (map (fn [[_ l r]] (->> [l r]
                               (mapv parse-long))))))

(defn part1 [memory]
  (->> memory
       find-muls
       (map (partial apply *))
       (apply +)))

(defn -main []
  (let [memory (slurp "input/day03.txt")]
    (println "Part 1:" (part1 memory))))
