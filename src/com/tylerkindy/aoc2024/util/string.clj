(ns com.tylerkindy.aoc2024.util.string
  (:require [clojure.string :as str]))

(defn trim-indent [s]
  (let [lines (str/split-lines s)
        indented-lines (drop 1 lines)
        indent (->> indented-lines
                    (map (fn [line]
                           (re-find #"^\s*" line)))
                    (map count)
                    (apply min))]
    (str/join "\n"
              (concat [(first lines)]
                      (map (fn [line]
                             (subs line indent))
                           indented-lines)))))
