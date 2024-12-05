(ns com.tylerkindy.aoc2024.day05-test
  (:require [com.tylerkindy.aoc2024.day05 :as day05]
            [clojure.test :refer [deftest is]]))

(def example-input "47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
")
(def example {:before-rules {47 #{53 13 61 29}
                             97 #{13 61 47 29 53 75}
                             75 #{29 53 47 61 13}
                             61 #{13 53 29}
                             29 #{13}
                             53 #{29 13}}
              :updates [[75 47 61 53 29]
                        [97 61 53 29 13]
                        [75 29 13]
                        [75 97 47 61 53]
                        [61 13 29]
                        [97 13 75 29 47]]})

(deftest parse-input
  (is (= (day05/parse-input example-input)
         example)))

(deftest before-pairs
  (is (= (day05/before-pairs [75 47 61 53 29])
         [[75 47] [75 61] [75 53] [75 29]
          [47 61] [47 53] [47 29]
          [61 53] [61 29]
          [53 29]])))

(deftest ordered?
  (is (true? (day05/ordered? [75 47 61 53 29] (example :before-rules))))
  (is (true? (day05/ordered? [97 61 53 29 13] (example :before-rules))))
  (is (true? (day05/ordered? [75 29 13] (example :before-rules))))
  (is (false? (day05/ordered? [75 97 47 61 53] (example :before-rules))))
  (is (false? (day05/ordered? [61 13 29] (example :before-rules))))
  (is (false? (day05/ordered? [97 13 75 29 47] (example :before-rules)))))

(deftest middle-page
  (is (= (day05/middle-page [75 47 61 53 29]) 61))
  (is (= (day05/middle-page [75 29 13]) 29)))

(deftest part1
  (is (= (day05/part1 example)
         143)))
