(ns example.core
  (:require [cljsjs.bn]
            ["bignumber.js" :as BigNumber]
            ["complex.js" :as Complex]
            ["decimal.js" :as d]
            ["fraction.js" :as frac]
            ["fraction.js/bigfraction.js" :as bf]
            [quaternion :as Quaternion]
            [odex :as o]))

(extend-protocol IEquiv
  d
  (-equiv [this other]
    (.equals this other))

  ;; frac
  ;; (-equiv [this other]
  ;;   (.equals this other))

  bf
  (-equiv [this other]
    (.equals this other))

  BigNumber
  (-equiv [this other]
    (.isEqualTo this other))

  Complex
  (-equiv [this other]
    (.equals this other))

  Quaternion
  (-equiv [this other]
    (.equals this other)))

(defn complex
  [re im]
  (Complex. re im))

(defn bignum [x]
  (BigNumber. x))

(defn quaternion
  [w x y z]
  (Quaternion. w x y z))

;; (defn fraction
;;   [num denom]
;;   (frac. num denom))

(= (quaternion 2 4 6 8)
   (.add (quaternion 1 2 3 4)
         (quaternion 1 2 3 4)))

(defn bigfraction
  [num denom]
  (bf. num denom))

(defn decimal [x]
  (d. x))

(defn routine [x y] y)

(defn solve-test []
  (let [s (o/Solver. 1)]
    (.solve s routine 0 #js [1] 1)))
