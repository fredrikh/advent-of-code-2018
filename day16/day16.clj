; Avoid indexing out or range
(defn v [r n] (if (<= 0 n 3) (r n) -1))
; Operations
(defn addr [r a b c] (assoc r c (+ (v r a) (v r b))))
(defn addi [r a b c] (assoc r c (+ (v r a) b)))
(defn mulr [r a b c] (assoc r c (* (v r a) (v r b))))
(defn muli [r a b c] (assoc r c (* (v r a) b)))
(defn banr [r a b c] (assoc r c (bit-and (v r a) (v r b))))
(defn bani [r a b c] (assoc r c (bit-and (v r a) b)))
(defn borr [r a b c] (assoc r c (bit-or (v r a) (v r b))))
(defn bori [r a b c] (assoc r c (bit-or (v r a) b)))
(defn setr [r a _ c] (assoc r c (v r a)))
(defn seti [r a _ c] (assoc r c a))
(defn gtir [r a b c] (assoc r c (if (> a (v r b)) 1 0)))
(defn gtri [r a b c] (assoc r c (if (> (v r a) b) 1 0)))
(defn gtrr [r a b c] (assoc r c (if (> (v r a) (v r b)) 1 0)))
(defn eqir [r a b c] (assoc r c (if (= a (v r b)) 1 0)))
(defn eqri [r a b c] (assoc r c (if (= (v r a) b) 1 0)))
(defn eqrr [r a b c] (assoc r c (if (= (v r a) (v r b)) 1 0)))

(def ops [addr addi mulr muli banr bani borr bori setr seti gtir gtri gtrr eqir eqri eqrr])
(defn reg-to-vec [r] (vec (for [i (range 4)] (r i))))
(defn init-reg [values] (zipmap '(0 1 2 3) values))

(def rx #"(\d)[,\s]+(\d)[,\s]+(\d)[,\s]+(\d)")
(defn parse-line [line] (map #(Integer/parseInt %) (drop 1 (re-find rx line))))
(defn f [sample] (map parse-line sample))
(def input (map clojure.string/split-lines (clojure.string/split (slurp "./input1.txt") #"\n\n")))
; compares initial registry state ri with expected registry state re 
; after applying candidate op with arguments a b c
(defn sample-test [ri re a b c] (fn [op] (= (op ri a b c) re)))
(defn sample-to-test [sample]
  (let [ri (init-reg (first sample)) 
        re (init-reg (last sample))
        [_ a b c] (second sample)]
    (sample-test ri re a b c)))

(def samples (map f input))

(defn count-candidates [ops]
  (fn [sample]
    (count (filter sample ops))))

(defn test-ops [ts ops]
  (let [cc (count-candidates ops)]
    (map cc ts)))

(def tests (map sample-to-test samples))

(println (count (filter (partial <= 3) (test-ops tests ops))))

