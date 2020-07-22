# Failing Dependency Repro

This error has a dependency I've packaged using `cljsjs`. The code is simple:

```clojure
(ns example.core
  (:require [cljsjs.complex]))

(defn complex
  [re im]
  (js/Complex. re im))
```

Here's the test:


```clojure
(ns example.core-test
  (:require [cljs.test :refer-macros [deftest is]]
            [example.core :as core]))

(deftest complex
  (is (= 1 (.-re (core/complex 1 2)))))
```

If you run the tests with NO optimizations, all succeeds:

```clojure
lein doo phantom none once
```

## Doo failure

The first failure is odd. If I run with `:simple` optimizations:

```clojure
lein doo phantom simple once
```

I see:

```
;; ======================================================================
;; Testing with Phantom:


ERROR: doo was not loaded from the compiled script.

Make sure you start your tests using doo-tests or doo-all-tests
and that you include that file in your build

Subprocess failed (exit code: 1)
```

## Import Failure

If I run with node:

```
lein doo node simple-node once
```

I see a real failure:

```
;; ======================================================================
;; Testing with Node:


Testing example.core-test

ERROR in (complex) (/Users/samritchie/code/clj/complex-failure/test/example/core_test.cljs:6:7)
expected: (= 1 (.-re (core/complex 1 2)))
  actual: #object[ReferenceError ReferenceError: Complex is not defined]

Ran 1 tests containing 1 assertions.
```

It looks like `Complex` is not available in the global namespace.


This is the generated javascript code: https://gist.github.com/sritchie/13cbc2cf37cee50ff2c8cd0c04dac606
