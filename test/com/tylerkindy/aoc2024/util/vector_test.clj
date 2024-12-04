(ns com.tylerkindy.aoc2024.util.vector-test
  (:require [com.tylerkindy.aoc2024.util.vector :as v]
            [clojure.test :refer [deftest is]]))

(deftest v+
  (is (= (v/+ [2 3] [4 5]) [6 8]))
  (is (= (v/+ [2 -3] [-4 5]) [-2 2])))

(deftest v*
  (is (= (v/* [2 3] 3) [6 9]))
  (is (= (v/* [2 3] -2) [-4 -6])))

(deftest v-
  (is (= (v/- [12 10] [4 3]) [8 7]))
  (is (= (v/- [-8 3] [3 -4]) [-11 7])))
