(ns example.core
  (:require [cljsjs.complex]))

(defn complex
  [re im]
  (js/Complex. re im))
