(def polymer (slurp "./input.txt"))
(def units "ABCDEFGHIJKLMNOPQRSTUVWXY")
(defn to-codes [polymer] (map int polymer))
(defn react? [a b] (and (some? a) (= (bit-xor a b) 0x20)))
(defn r [acc c] (if (react? (last acc) c) (pop acc) (conj acc c)))
(defn unit-count [polymer] (count (reduce r [] (to-codes polymer))))
(defn remove-unit [polymer unit] (clojure.string/replace polymer (re-pattern (str "(?i)" unit)) ""))

(println (unit-count polymer))
(println (apply min (map (comp unit-count (partial remove-unit polymer)) units))) 
