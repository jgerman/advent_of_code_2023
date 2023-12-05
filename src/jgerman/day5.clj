(ns jgerman.day5
  (:require
   [jgerman.utils :as utils]))

(defn match? [n rec]
  (let [[destination source offset] rec]
    (when (and (<= source n)
               (< n (+ source offset)))
      (+ destination (- n source)))))

(defn ->mapping [rules n]
  (let [match (first (filter (partial match? n) rules))]
    (if match
      (match? n match)
      n)))

(defn map-seed [almanac seed]
  (->> seed
       (->mapping (:seed-to-soil almanac))
       (->mapping (:soil-to-fertilizer almanac))
       (->mapping (:fertilizer-to-water almanac))
       (->mapping (:water-to-light almanac))
       (->mapping (:light-to-temperature almanac))
       (->mapping (:temperature-to-humidity almanac))
       (->mapping (:humidity-to-location almanac))))

(defn produce-range [[start span]]
  (range start (+ start span)))

(defn seed-ranges->seeds [seeds]
  (let [ranges (partition 2 seeds)]
    (mapcat produce-range ranges)))

(defn task-1 [resource]
  (let [almanac (utils/resource->edn resource)
        seeds (:seeds almanac)]
    (apply min (map (partial map-seed almanac) seeds))))

(defn task-2 [resource]
  (let [almanac (utils/resource->edn resource)
        seeds (seed-ranges->seeds (:seeds almanac))]
    (apply min (map (partial map-seed almanac) seeds))))


(comment

  (= 35 (task-1 "day5/sample1.edn"))
  (= 621354867 (task-1 "day5/input.edn"))

  (= 46 (task-2 "day5/sample1.edn"))
  (task-2 "day5/input.edn")

  (seed-ranges->seeds [79 14 55 13])
  (range 79 (+ 79 14))
  ;; testing match
  (= 50 (match? 98 '(50 98 2)))
  (= 51 (match? 99 '(50 98 2)))
  (not (match? 100 '(50 98 2)))
  (not (match? 97  '(50 98 2)))

  (match? 14 '(0 15 37))
  (match? 14 '(37 52 2))
  (match? 14 '(39 0 15))

  (->mapping [[0 15 37]
  [37 52 2]
              [39 0 15]] 14)

  ;; testing ->mapping
  (= 50 (->mapping '((50 98 2)
                        (52 50 48)) 98))

  (= 51 (->mapping '((50 98 2)
                        (52 50 48)) 99))

  (= 55 (->mapping '((50 98 2)
                        (52 50 48)) 53))

  (= 10 (->mapping '((50 98 2)
                        (52 50 48)) 10))

  ;;
  ,)
