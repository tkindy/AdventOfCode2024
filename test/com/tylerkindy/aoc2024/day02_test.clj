(ns com.tylerkindy.aoc2024.day02-test
  (:require [com.tylerkindy.aoc2024.day02 :as day02]
            [clojure.test :refer [deftest is]]))

(def example-input "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9")

(def example '([7 6 4 2 1]
               [1 2 7 8 9]
               [9 7 6 2 1]
               [1 3 2 4 5]
               [8 6 4 4 1]
               [1 3 6 7 9]))

(deftest parse-input
  (is (= (day02/parse-input example-input)
         example)))

(deftest deltas
  (is (= (day02/deltas [7 6 4 2 1])
         [1 2 2 1])))

(deftest all-same-sign?
  (is (true? (day02/all-same-sign? [1 2 3 4 5])))
  (is (false? (day02/all-same-sign? [1 2 -3 4 5])))

  (is (true? (day02/all-same-sign? [-1 -2 -3 -4 -5])))
  (is (false? (day02/all-same-sign? [-1 -2 -3 -4 5]))))

(deftest all-within-range?
  (is (true? (day02/all-within-range? [1 2 2 1])))
  (is (true? (day02/all-within-range? [-1 -2 -2 -1])))
  (is (false? (day02/all-within-range? [-1 -2 -3 -4 -2]))))

(deftest part1
  (is (= (day02/part1 example)
         2)))
