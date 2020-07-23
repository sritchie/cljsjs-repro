# cljsjs test repository

I started this as a repo to reproduce failing bugs. Now, it's a nice test
repository to make sure that externs work in the `cljsjs` dependencies I've been
creating.

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

### UPDATE:

It turns out this works with an older version of Clojurescript:

```clojure
[org.clojure/clojurescript "1.10.597"]
```

But fails with:

```clojure
[org.clojure/clojurescript "1.10.741"]
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

### UPDATE:

From @thheller in the [Clojurians slack](https://clojurians.slack.com/archives/C03S1L9DN/p1595448579428700):

> your `js/Complex` missing issue is coming from the js file itself not always
> creating that global as far as I can tell.
> https://github.com/infusion/Complex.js/blob/master/complex.js#L1384-L1395 is
> the common UMD style wrapper and in node it will take the `typeof` exports
> branch since it will exist. as such it will not take the last branch which
> creates the global

I asked what problems this would cause, and got:

> to work with CLJSJS on node yes. it is not a fault in the library itself, it
> will likely work just fine in regular node.
