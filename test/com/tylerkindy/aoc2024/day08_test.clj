(ns com.tylerkindy.aoc2024.day08-test
  (:require [com.tylerkindy.aoc2024.day08 :as day08]
            [clojure.test :refer [deftest is]]))

(def example-input "............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............
")
(def example {:dimensions [12 12]
              :antennas {\0 #{[8 1] [5 2] [7 3] [4 4]}
                         \A #{[6 5] [8 8] [9 9]}}})

(deftest parse-input
  (is (= (day08/parse-input example-input)
         example)))

(deftest all-pairs
  (is (= (day08/all-pairs #{[8 1] [5 2] [7 3] [4 4]})
         #{#{[8 1] [5 2]} #{[8 1] [7 3]} #{[8 1] [4 4]}
           #{[5 2] [7 3]} #{[5 2] [4 4]}
           #{[7 3] [4 4]}})))

(deftest unique-antinodes
  (is (= (day08/unique-antinodes example)
         #{[6 0] [11 0]
           [3 1]
           [4 2] [10 2]
           [2 3]
           [9 4]
           [1 5] [6 5]
           [3 6]
           [0 7] [7 7]
           [10 10]
           [10 11]})))

(deftest part1
  (is (= (day08/part1 example)
         14)))
