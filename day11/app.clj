(defn get-hundreds [n]
  (rem (quot n 100)  10))

(defn power-level [x y sn]
  (let [rack-id (+ x 10)]
    (- (get-hundreds (* (+ (* rack-id y) sn) rack-id)) 5)))

(defn grid [sn size]
  (for [x (range 1 (+ size 1)) y (range 1 (+ size 1))] [x y (power-level x y sn)]))

(defn sum-area [table cell]
  (let [val (fn [n] (or n 0))
        x (first cell) y (second cell) level (last cell)
        above [x (- y 1)] left [(- x 1) y] above-left [(- x 1) (- y 1)]
        sum (- (+ level (val (table above)) (val (table left))) (val (table above-left)))]
    (assoc table [x y] sum)))

(defn summed-area-table [grid]
  (reduce sum-area {} grid))

(defn table-val [table pos] (or (table pos) 0))

(defn square-sum [x y size sat]
  (let [tval (partial table-val sat)
        offsetX (- x size)
        offsetY (- y size)
        a (tval [offsetX offsetY])
        b (tval [x offsetY])
        c (tval [offsetX y])
        d (tval [x y])]
    [(- x size -1) (- y size -1) size (- (+ a d) b c)]))

(defn part-1 [grid sat]
  (apply max-key last
    (let [bounds (int (Math/sqrt (count grid)))]
    (for [i (range 1 bounds)
          j (range 1 bounds)
          :when (and (<= 3 i) (<= 3 j))]
    (square-sum i j 3 sat)))))

(defn part-2 [grid sat]
  (apply max-key last
    (let [bounds (int (Math/sqrt (count grid)))]
    (for [i (range 1 bounds)
          j (range 1 bounds)
          k (range 1 bounds)
          :when (and (<= i j) (<= i k))]
    (square-sum j k i sat)))))

(def g (grid 3031 300))
(def sat (summed-area-table g))

(println (part-1 g sat))
(println (part-2 g sat))