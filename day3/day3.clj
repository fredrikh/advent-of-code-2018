(require '[clojure.set :as set])

(def lines (clojure.string/split-lines (slurp "./input.txt")))

(defn parse-line [line]
  (map #(Integer/parseInt %) (rest (re-matches #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" line))))
  
(defn line-to-set [line]
  (let [[id x y w h] (parse-line line)]
  [id (set (for [i (range x (+ x w)) j (range y (+ y h))] [i j]))]))

(def claims (map line-to-set lines))

(def overlapping 
  (set (map first (filter #(> (second %) 1) (reduce #(assoc %1 %2 (inc (%1 %2 0))) {} (mapcat second claims))))))

(println (count overlapping))

(def non-overlapping (first (filter #(empty? (set/intersection overlapping %)) (map second claims))))

(println (first (filter #(= non-overlapping (second %)) claims)))
