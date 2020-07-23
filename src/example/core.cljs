(ns example.core
  (:require [cljsjs.bn]
            [cljsjs.complex]
            [cljsjs.decimal]
            [cljsjs.odex]
            [cljsjs.fraction]))

(extend-protocol IEquiv
  js/Decimal
  (-equiv [this other]
    (.equals this other))

  js/Complex
  (-equiv [this other]
    (.equals this other)))

(defn complex
  [re im]
  (js/Complex. re))

(defn fraction
  [num denom]
  (js/Fraction. num denom))

(defn decimal [x]
  (js/Decimal. x))

(defn routine [x y] y)

(defn solve-test []
  (let [s (js/odex.Solver. 1)]
    (.solve s routine 0 #js [1] 1)))
