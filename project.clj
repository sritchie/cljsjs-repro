(defproject lein-doo-example "0.1.12-SNAPSHOT"
  :description "Reproduction of my simple compilation error."
  :dependencies [[org.clojure/clojure "1.10.1" :scope "provided"]
                 [org.clojure/clojurescript "1.10.773" :scope "provided"]

                 ;; This is the jar I'm trying to build.
                 [cljsjs/complex "2.0.11-0"]
                 ]
  :plugins [[lein-cljsbuild "1.1.8" :exclusions [org.clojure/clojurescript]]
            [lein-doo "0.1.11"]]
  :source-paths ["src" "test"]
  :clean-targets ^{:protect false} [:target-path "resources/public/js/" "out"]
  :jvm-opts ["-Xmx1g"]
  :aliases {"test-succeed" ["doo" "phantom" "none" "once"]
            "test-fail" ["doo" "phantom" "simple" "once"]
            "test-fail-node" ["doo" "node" "simple-node" "once"]}
  :cljsbuild {:builds
              {:none {:source-paths ["src" "test"]
                      :compiler     {:output-to     "out/testable.js"
                                     :main          example.runner
                                     :source-map    true
                                     :optimizations :none}}
               :simple {:source-paths ["src" "test"]
                        :compiler     {:output-to     "out/testable.js"
                                       :main          example.runner
                                       :optimizations :simple}}
               :simple-node {:source-paths ["src" "test"]
                             :compiler     {:output-to     "out/testable.js"
                                            :target :nodejs
                                            :main          example.runner
                                            :optimizations :simple}}}})