(ns com.tylerkindy.aoc2024.day06-test
  (:require [com.tylerkindy.aoc2024.day06 :as day06]
            [com.tylerkindy.aoc2024.util.string :as s]
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

(def example-obstacles-index (day06/index-obstacles (example :obstacles)))

(deftest index-obstacles
  (is (= example-obstacles-index
         {:rows {0 [4]
                 1 [9]
                 3 [2]
                 4 [7]
                 6 [1]
                 7 [8]
                 8 [0]
                 9 [6]}
          :columns {0 [8]
                    1 [6]
                    2 [3]
                    4 [0]
                    6 [9]
                    7 [4]
                    8 [7]
                    9 [1]}})))

(deftest guard->segment
  (is (= (day06/guard->segment [(example :start) [0 -1]]
                               example-obstacles-index)
         {:start [4 1], :dir [0 -1]}))
  (is (= (day06/guard->segment [[2 1] [0 1]]
                               example-obstacles-index)
         {:start [2 0], :dir [0 1]}))
  (is (= (day06/guard->segment [[2 8] [0 1]]
                               example-obstacles-index)
         {:start [2 4], :dir [0 1]}))
  (is (= (day06/guard->segment [[2 8] [1 0]]
                               example-obstacles-index)
         {:start [1 8], :dir [1 0]})))

(defn parse-example [example]
  (-> example
      s/trim-indent
      day06/parse-input))

(deftest obstacle-candidates
  (is (= (day06/obstacle-candidates example)
         #{[3 6] [6 7] [7 7] [1 8] [3 8] [7 9]}))

  (is (empty? (day06/obstacle-candidates
               (parse-example "##..
                               ...#
                               ^...
                               ..#."))))
  (is (= (day06/obstacle-candidates
          (parse-example "....
                          ...#
                          #^..
                          ..#."))
         #{[1 0]}))
  (is (= (day06/obstacle-candidates
          (parse-example ".#..
                          ...#
                          .^..
                          ..#."))
         #{[0 2]})))

(deftest part2
  (is (= (day06/part2 example)
         6)))
