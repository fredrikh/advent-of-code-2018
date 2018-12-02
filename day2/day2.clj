(def lines (clojure.string/split-lines (slurp "./input.txt")))
(def f (map (comp set vals frequencies) lines))
(println 
  (* 
    (reduce + (map #(if (% 2) 1 0 ) f)) 
    (reduce + (map #(if (% 3) 1 0 ) f))))

(defn distance [a b] 
  (count (filter (partial apply not=) (map vector a b))))

(defn common-letters [a b] 
  (filter (partial apply =) (map vector a b)))

(def pair
  (first (take 1 (
    for [i lines j lines :let [d (distance i j)] :when (= d 1)] [i j]))))

(println (clojure.string/join (map first (common-letters (first pair) (last pair)))))