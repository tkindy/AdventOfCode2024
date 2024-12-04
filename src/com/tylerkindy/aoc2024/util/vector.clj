(ns com.tylerkindy.aoc2024.util.vector
  (:require [clojure.core :as core])
  (:refer-clojure :exclude [+ * -]))

(defn + [[x1 y1] [x2 y2]]
  [(core/+ x1 x2) (core/+ y1 y2)])

(defn * [[x1 y1] c]
  [(core/* x1 c) (core/* y1 c)])

(defn - [v1 v2]
  (+ v1 (* v2 -1)))
