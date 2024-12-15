(ns com.tylerkindy.aoc2024.day10-test
  (:require [com.tylerkindy.aoc2024.day10 :as day10]
            [clojure.test :refer [deftest is]]))

(def example-input "89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732
")
(def example [[8 9 0 1 0 1 2 3]
              [7 8 1 2 1 8 7 4]
              [8 7 4 3 0 9 6 5]
              [9 6 5 4 9 8 7 4]
              [4 5 6 7 8 9 0 3]
              [3 2 0 1 9 0 1 2]
              [0 1 3 2 9 8 0 1]
              [1 0 4 5 6 7 3 2]])

(def small-example [[0 1 2 3]
                    [1 2 3 4]
                    [8 7 6 5]
                    [9 8 7 6]])

(deftest parse-input
  (is (= (day10/parse-input example-input)
         example)))

(deftest find-trailheads
  (is (= (day10/find-trailheads example)
         #{[2 0] [4 0]
           [4 2]
           [6 4]
           [2 5] [5 5]
           [0 6] [6 6]
           [1 7]})))

(deftest score-from
  (is (= (day10/score-from [0 0] small-example)
         1))

  (is (= (day10/score-from [2 0] example)
         5)))

(deftest part1
  (is (= (day10/part1 small-example)
         1))

  (is (= (day10/part1 example)
         36)))

(deftest part2
  (is (= (day10/part2 example)
         81)))
