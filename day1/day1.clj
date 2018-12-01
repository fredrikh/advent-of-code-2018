(def f (slurp "./input.txt"))
(def lines (clojure.string/split-lines f))
(defn parse-int [s] (Integer/parseInt s))
(def numbers (mapv parse-int lines))
(def sum (reduce + numbers))
(println sum)

(defn seen [sums val]
  (if (sums val) (reduced val) (conj sums val)))

(def twice (reduce seen #{0} (reductions + (cycle numbers))))
(println twice)