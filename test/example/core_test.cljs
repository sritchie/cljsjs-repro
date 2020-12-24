(ns example.core-test
  (:require [clojure.test :refer [deftest is]]
            [example.core :as core]))

(deftest complex
  (is (= 1 (.-re (core/complex 1 2)))))

(deftest fraction
  (is (= (core/fraction 50 100)
         (core/fraction 1 2))))

(deftest bigfraction
  (println (type (.-n (core/bigfraction 50 100))))
  (is (zero? (type (.-n (core/bigfraction 50 100)))))
  (is (= (core/bigfraction 50 100)
         (core/bigfraction 1 2))))

(deftest solver
  (is (< (- 2.7182817799042955
            (first (.-y (core/solve-test))))
         1e-5)))

(deftest decimal
  (let [x (core/decimal 123.4567)
        y (core/decimal "123456.7e-3")
        z (core/decimal x)]
    (is (= x y z))))

(deftest bignumber
  (let [x (core/bignum 123.4567)
        y (core/bignum "123456.7e-3")
        z (core/bignum x)]
    (is (= x y z))))
