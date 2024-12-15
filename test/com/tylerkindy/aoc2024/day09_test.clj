(ns com.tylerkindy.aoc2024.day09-test
  (:require [com.tylerkindy.aoc2024.day09 :as day09]
            [clojure.test :refer [deftest is]]))

(def example-input "2333133121414131402")
(def example [0 0 nil nil nil 1 1 1 nil nil nil
              2 nil nil nil 3 3 3 nil 4 4 nil
              5 5 5 5 nil 6 6 6 6 nil 7 7 7 nil
              8 8 8 8 9 9])

(deftest parse-input
  (is (= (day09/parse-input example-input)
         example)))

(deftest defrag
  (is (= (day09/defrag example)
         [0 0 9 9 8 1 1 1 8 8 8 2 7 7 7 3 3 3 6
          4 4 6 5 5 5 5 6 6 nil nil nil nil nil
          nil nil nil nil nil nil nil nil nil])))

(deftest part1
  (is (= (day09/part1 example)
         1928)))

(deftest open-spaces
  (is (= (day09/open-spaces example)
         (list {:start 2, :length 3}
               {:start 8, :length 3}
               {:start 12, :length 3}
               {:start 18, :length 1}
               {:start 21, :length 1}
               {:start 26, :length 1}
               {:start 31, :length 1}
               {:start 35, :length 1}))))

(deftest swap
  (is (= (day09/swap [7 8 9 10 11 12 13 14 15 16 17 18] 2 7 3)
         [7 8 14 15 16 12 13 9 10 11 17 18])))

(deftest defrag-contiguous
  (is (= (day09/defrag-contiguous example)
         [0 0 9 9 2 1 1 1 7 7 7 nil 4 4 nil 3 3 3
          nil nil nil nil 5 5 5 5 nil 6 6 6 6 nil
          nil nil nil nil 8 8 8 8 nil nil])))

(deftest part2
  (is (= (day09/part2 example)
         2858)))
