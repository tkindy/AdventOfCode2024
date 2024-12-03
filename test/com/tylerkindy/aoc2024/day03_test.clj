(ns com.tylerkindy.aoc2024.day03-test
  (:require [com.tylerkindy.aoc2024.day03 :as day03]
            [clojure.test :refer [deftest is]]))

(def example "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(deftest find-muls
  (is (= (day03/find-muls example)
         (list [2 4] [5 5] [11 8] [8 5]))))

(deftest part1
  (is (= (day03/part1 example)
         161)))
