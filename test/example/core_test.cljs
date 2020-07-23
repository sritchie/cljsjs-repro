(ns example.core-test
  (:require [cljs.test :refer-macros [deftest is]]
            [example.core :as core]))

(deftest complex
  (is (= 1 (.-re (core/complex 1 2)))))

(deftest fraction
  (is (= 50 (.-n (core/fraction 100 122)))))

(deftest solver
  (is (< (- 2.7182817799042955
            (first (.-y (core/solve-test))))
         1e-5)))

(deftest decimal
  (let [x (core/decimal 123.4567)
        y (core/decimal "123456.7e-3")
        z (core/decimal x)]
    (is (= x y z))))
