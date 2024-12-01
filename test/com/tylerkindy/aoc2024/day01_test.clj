(ns com.tylerkindy.aoc2024.day01-test
  (:require [com.tylerkindy.aoc2024.day01 :as day01]
            [clojure.test :refer [deftest is]]))

(def example-input "3   4
4   3
2   5
1   3
3   9
3   3")

(def example ['(3 4 2 1 3 3)
              '(4 3 5 3 9 3)])

(deftest parse-input
  (is (= (day01/parse-input example-input)
         example)))

(deftest part1
  (is (= (day01/part1 example)
         11)))
