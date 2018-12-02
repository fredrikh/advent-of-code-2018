(def f (slurp "./input.txt"))
(def lines (clojure.string/split-lines f))
(def f (map (comp set vals frequencies) lines))
(println 
  (* 
    (reduce + (map #(if (% 2) 1 0 ) f)) 
    (reduce + (map #(if (% 3) 1 0 ) f))))

