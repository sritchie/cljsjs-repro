(ns example.core-test
  (:require [cljs.test :refer-macros [deftest is]]
            [example.core :as core]))

(deftest complex
  (is (= 1 (.-re (core/complex 1 2)))))
