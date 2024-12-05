(ns com.tylerkindy.aoc2024.day04-test
  (:require [com.tylerkindy.aoc2024.day04 :as day04]
            [clojure.test :refer [deftest is]]))

(def example-input "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
")
(def example [[\M \M \M \S \X \X \M \A \S \M]
              [\M \S \A \M \X \M \S \M \S \A]
              [\A \M \X \S \X \M \A \A \M \M]
              [\M \S \A \M \A \S \M \S \M \X]
              [\X \M \A \S \A \M \X \A \M \M]
              [\X \X \A \M \M \X \X \A \M \A]
              [\S \M \S \M \S \A \S \X \S \S]
              [\S \A \X \A \M \A \S \A \A \A]
              [\M \A \M \M \M \X \M \M \M \M]
              [\M \X \M \X \A \X \M \A \S \X]])

(deftest parse-input
  (is (= (day04/parse-input example-input)
         example)))

(deftest all-coordinates
  (is (= (day04/all-coordinates [[\A \B \C]
                                 [\D \E \F]])
         #{[0 0] [1 0] [2 0]
           [0 1] [1 1] [2 1]})))

(deftest neighbors
  (is (= (day04/neighbors [0 1])
         #{[-1 0] [0 0] [1 0]
           [-1 1]       [1 1]
           [-1 2] [0 2] [1 2]})))

(deftest part1
  (is (= (day04/part1 example)
         18)))

(deftest part2
  (is (= (day04/part2 example)
         9)))
