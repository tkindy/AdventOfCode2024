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
