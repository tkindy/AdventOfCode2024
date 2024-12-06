(ns com.tylerkindy.aoc2024.day06-test
  (:require [com.tylerkindy.aoc2024.day06 :as day06]
            [clojure.test :refer [deftest is]]))

(def example-input "....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...
")
(def example {:dimensions [10 10]
              :start [4 6]
              :obstacles #{[4 0] [9 1] [2 3]
                           [7 4] [1 6] [8 7]
                           [0 8] [6 9]}})

(deftest parse-input
  (is (= (day06/parse-input example-input)
         example)))

(deftest part1
  (is (= (day06/part1 example)
         41)))
