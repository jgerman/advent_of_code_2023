(ns jgerman.day1
  (:require [jgerman.utils :as utils]
            [clojure.string :as str]
            [jgerman.day1 :as day1]))


(defn pre-process [line]
  (-> line
      (str/replace "one" "o1e")
      (str/replace "two" "t2o")
      (str/replace "three" "t3e")
      (str/replace "four" "f4r")
      (str/replace "five" "f5e")
      (str/replace "six" "s6x")
      (str/replace "seven" "s7v")
      (str/replace "eight" "e8t")
      (str/replace "nine" "n9e")))

(defn calibrate [line]
  (let [calibrated
        (->> (str/split line #"")
             (map read-string)
             (filter number?))]
    (parse-long (str (first calibrated) (last calibrated)))))

(defn task1 [resource]
  (->> resource
       utils/resource->lines
       (map calibrate)
       (reduce +)))

(defn task2 [resource]
  (->> resource
       utils/resource->lines
       (map pre-process)
       (map calibrate)
       (reduce +)))

(comment

  (= 142 (task1 "day1/sample.txt"))
  (= 55621 (task1 "day1/input.txt"))

  (= 281 (task2 "day1/sample2.txt"))
  (= 53592 (task2 "day1/input.txt"))

  (prn
   (map pre-process
        (-> "day1/sample2.txt" utils/resource->lines)))


  ;;
  ,)
