(ns com.tylerkindy.aoc2024.day07-test
  (:require [com.tylerkindy.aoc2024.day07 :as day07]
            [clojure.test :refer [deftest is]]))

(def example-input "190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
")
(def example [{:test-value 190
               :operands [10 19]}
              {:test-value 3267
               :operands [81 40 27]}
              {:test-value 83
               :operands [17 5]}
              {:test-value 156
               :operands [15 6]}
              {:test-value 7290
               :operands [6 8 6 15]}
              {:test-value 161011
               :operands [16 10 13]}
              {:test-value 192
               :operands [17 8 14]}
              {:test-value 21037
               :operands [9 7 18 13]}
              {:test-value 292
               :operands [11 6 16 20]}])

(deftest parse-input
  (is (= (day07/parse-input example-input)
         example)))

(deftest possible-results
  (is (= (into #{} (day07/possible-results [10 19]))
         #{29 190})))

(deftest possibly-true?
  (is (true? (day07/possibly-true? {:test-value 190
                                    :operands [10 19]})))
  (is (true? (day07/possibly-true? {:test-value 3267
                                    :operands [81 40 27]})))
  (is (false? (day07/possibly-true? {:test-value 83
                                     :operands [17 5]}))))

(deftest part1
  (is (= (day07/part1 example)
         3749)))
