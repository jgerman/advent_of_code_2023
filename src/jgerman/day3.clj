(ns jgerman.day3
  (:require
   [jgerman.utils :as utils]
   [clojure.core.matrix :as matrix]
   [clojure.string :as str]
   [instaparse.core :as insta]))

(def line
  (insta/parser
   "<S> = ELEMENT+
    <ELEMENT> = number | dot | symbol | gear
    number = #'[0-9]+'
    <dot> = <'.'>
    <gear>  = <'*'>
    <symbol> = <('+' | '*' | '#' | '$' | '&' | '-' | '=' | '%' | '@' | '/')>"))

(defn single-token [token parsed-line]
  (filter (fn [x]
            (= token (first x)))
          parsed-line))

(defn input->matrix [data]
  (map (fn [s]
         (str/split s #""))
       data))

(def numbers-only (partial single-token :number))
(def gears-only (partial single-token :gear))

(defn parse-it [l]
  (let [parsed (line l)
        numbers (map #(read-string (second %)) parsed)
        ranges (->> parsed
                    (map insta/span)
                    (map (fn [[start end]]
                           [start (dec end)])))]
    (map (fn [n r]
           [n r])
         numbers
         ranges)))

(defn get-numbers [ls]
  (map parse-it ls))

(defn line-result->map [line-result]
  (map-indexed (fn [idx itm]
                 {:line-num idx
                  :numbers itm})
               line-result))

(defn contains-symbols? [l]
  (< 0 (count (-> l
                  (str/replace #"\d" "")
                  (str/replace "." "")))))

(defn ->relevant-lines [ls n]
  (if (= n 0)
    (take 2 ls)
    (take 3 (drop (dec n) ls))))

(defn safe-subs [s start end]
  (let [safe-start (if (< start 0) 0 start)
        safe-end (if (<= (count s) end) (count s) (inc end))]
    (subs s safe-start safe-end)))

(defn extend-range [range]
  [(dec (first range))
   (inc (last range))])

(defn ->slices [ls range]
  (let [extended-range (extend-range range)]
    (map #(apply safe-subs % extended-range) ls)))

(defn process-number [data line-no [number range]]
  (let [relevant-lines (->relevant-lines data line-no)
        slices (apply str (->slices relevant-lines range))]
    (if (contains-symbols? slices)
      number
      0)))

(defn process-line-result [ls {:keys [line-num numbers]}]
  (map (partial process-number ls line-num) numbers))

(defn find-numbers [ls]
  (-> ls
      get-numbers
      line-result->map))

(defn gear-locations [input]
  (let [m (input->matrix input)]
    (filter (fn[x]
              (= "*" (apply matrix/mget m x)))
            (matrix/index-seq m))))

(defn task-1 [resource]
  (let [ls (utils/resource->lines resource)
        ns (find-numbers ls)]
    (apply + (flatten (map (partial process-line-result ls) ns)))))

(defn calculat-gear [is location]
  )

(defn task-2 [resource]
  (let [ls (utils/resource->lines resource)
        gear-locations (gear-locations [ls])]
    ))

(defn ->number-coords [line-num [num num-range]]
  (let [expanded (range (first num-range)
                        (inc (second num-range)))]
    [num (map (fn [x]
               [x line-num])
              expanded)]))

(defn line->full-locations [{:keys [line-num numbers]}]
  (mapcat (partial ->number-coords line-num) numbers))

(comment

  (= 4361 (task-1 "day3/sample1.txt"))
  (= 525181 (task-1 "day3/input.txt"))

  (def sample (utils/resource->lines "day3/sample1.txt"))
  (def input (utils/resource->lines "day3/input.txt"))
  (def sample-matrix (input->matrix sample))
  (def input-matrix (input->matrix input))

  (gear-locations sample)
  (matrix/index-seq sample-matrix)

  (get-numbers sample)
  (count (get-numbers input))

  (def input-numbers (find-numbers input))

  (apply + (process-line-result input (nth input-numbers 23)))

  (def t (first (drop 4 (find-numbers sample))))

  t
  (first (:numbers test))

  (process-number sample 4 [617 [0 2]])

  (process-line-result sample  t)
  (process-line-result sample (nth (find-numbers sample) 0))

  (numbers-only (line "...........364....812*.......*..$.........812*....*.....369....&......176......................15.346#...#.....908...828......*...-650......"))
  (map insta/span (gears-only (line "...........364....812*.......*..$.........812*....*.....369....&......176......................15.346#...#.....908...828......*...-650......")))

  (find-numbers sample)
  (line->full-locations { :line-num 0, :numbers ( [ 467 [ 0 2 ] ] [ 114 [ 5 7 ] ] ) })




  (->number-coords 0 [ 467 [ 0 2 ] ] )


  ;;
  ,)
