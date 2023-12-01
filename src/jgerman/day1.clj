(ns jgerman.day1
  (:require [jgerman.utils :as utils]
            [clojure.string :as str]
            [jgerman.day1 :as day1]))


(defn pre-process [line]
  (-> line
      (str/replace "oneight" "18")
      (str/replace "twone" "21")
      (str/replace "eightwo" "82")
      (str/replace "eighthree" "83")
      (str/replace "threeight" "38")
      (str/replace "fiveight" "58")
      (str/replace "sevenine" "79")
      (str/replace "nineight" "98")
      (str/replace "one" "1")
      (str/replace "two" "2")
      (str/replace "three" "3")
      (str/replace "four" "4")
      (str/replace "five" "5")
      (str/replace "six" "6")
      (str/replace "seven" "7")
      (str/replace "eight" "8")
      (str/replace "nine" "9")))

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
