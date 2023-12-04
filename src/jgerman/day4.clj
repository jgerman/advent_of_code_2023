(ns jgerman.day4
  (:require [jgerman.utils :as utils]
            [clojure.string :as str]
            [clojure.set :as set]
            [clojure.math.numeric-tower :as math]))

(defn prepare-input [resource]
  (let [segments (->> resource
                      utils/resource->lines
                      (map #(str/split % #":"))
                      (map second)
                      (map #(str/split % #"\|")))]
       (map (fn [ele]
              [(str/split (str/trim (first ele)) #"\s+")
               (str/split (str/trim (second ele)) #"\s+")])
            segments)))

(defn score [wins]
  (if (= 0 wins)
    0
    (math/expt 2 (dec wins))))

(defn matches [[numbers card]]
  (count (set/intersection (set numbers) (set card))))

(defn task-1 [resource]
  (let [input (prepare-input resource)]
    (apply + (map score (map matches input)))))

(defn update-copies [ls n copies]
  (vec (concat (map #(+ % copies) (take n ls))
              (drop n ls))))

;; there is probably a much more elegant way to do this
(defn build-copies [wins]
  (let [copies (vec (repeat (count wins) 1))]
    (loop [cps copies
           ws  wins
           acc []]
      (let [current-win (first ws)
            current-copies (first cps)]
        (if (nil? current-win) acc
            (recur
             (update-copies (rest cps) current-win current-copies)
             (rest ws)
             (conj acc current-copies)))))))

(defn task-2 [resource]
  (let [input (prepare-input resource)
        initial (map matches input)
        copies (build-copies initial)]
    (apply + copies)))

(comment

  (= 13 (task-1 "day4/sample1.txt"))
  (= 24542 (task-1 "day4/input.txt"))

  (def sample (prepare-input "day4/sample1.txt"))
  (def input (prepare-input "day4/input.txt"))

  (= 30 (task-2 "day4/sample1.txt"))
  (= 8736438 (task-2 "day4/input.txt"))
  ;;
  ,)()
