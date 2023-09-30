(defproject todo-mvc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [duct/core "0.8.0"]
                 [duct/module.ataraxy "0.3.0"]
                 [duct/module.logging "0.5.0"]
                 [duct/module.web "0.7.3"]
                 [com.datomic/peer "1.0.6735"]
                 [com.datomic/client-pro "1.0.76"]
                 [metosin/ring-swagger "0.26.2"]
                 [metosin/spec-tools "0.10.6"]
                 [org.clojure/data.json "2.4.0"]
                 [org.webjars/swagger-ui "5.6.1"]
                 [metosin/ring-swagger-ui "5.0.0-alpha.0"]
                 ;; [com.datomic/local "1.0.267"]
                 ;;[datomic-client-memdb "1.1.1"]
                 ]
  :plugins [[duct/lein-duct "0.12.3"]]
  :main ^:skip-aot todo-mvc.main
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :middleware     [lein-duct.plugin/middleware]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.2"]
                                   [hawk "0.2.11"]
                                   [eftest "0.5.9"]
                                   [kerodon "0.9.1"]]}})