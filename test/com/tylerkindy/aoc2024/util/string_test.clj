(ns com.tylerkindy.aoc2024.util.string-test
  (:require [com.tylerkindy.aoc2024.util.string :as s]
            [clojure.test :refer [deftest is]]))

(deftest trim-indent
  (is (= (s/trim-indent "foo
                         bar
                         baz")
         "foo\nbar\nbaz")))
