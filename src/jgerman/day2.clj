(ns jgerman.day2
  (:require [jgerman.utils :as utils]
            [instaparse.core :as insta]
            [clojure.string :as str]))

(def game
  (insta/parser
   "<S> = GAME_NUM PULLS
    GAME_NUM = <word> <whitespace> number <colon> <whitespace>
    PULLS = PULL | PULL <semicolon> <whitespace> PULLS
    PULL = CUBES | CUBES <comma> <whitespace> CUBES | CUBES <comma> <whitespace> CUBES <comma> <whitespace> CUBES
    CUBES = number <whitespace> color
    <color> = 'red' | 'green' | 'blue'
    word = #'[a-zA-Z]+'
    colon = ':'
    semicolon = ';'
    comma = ','
    number = #'[0-9]+'
    whitespace = #'\\s+'"))

(defn parse-line [line]
  (let [[game-num pulls] (->> (game line)
                              (insta/transform {:number (fn [x] (read-string x))})
                              (insta/transform {:CUBES (fn [n c] {c n})})
                              (insta/transform {:PULL (fn [& xs] (apply merge xs))})
                              (insta/transform {:PULLS (fn [& xs] xs)}))]
    {:game-number (second game-num)
     :pulls (flatten  pulls)}))

(defn parse-input [resource]
  (let [lines (-> resource
                  utils/resource->lines)]
    (map parse-line lines)))

(defn check-color [max n]
  (or (nil? n)
      (>= max n)))

(defn pull-possible? [g]
  (and (check-color 12 (get g "red"))
       (check-color 13 (get g "green"))
       (check-color 14 (get g "blue"))))

(defn valid-game? [{:keys [pulls]}]
  (every? pull-possible? pulls))

(defn power [pulls]
  (* (apply max (filter identity (map #(get % "red") pulls)))
     (apply max (filter identity (map #(get % "green") pulls)))
     (apply max (filter identity (map #(get % "blue") pulls)))))

(defn power-set [pulls]
  (apply + (map power pulls)))

(defn task-1 [resource]
  (let [games (parse-input resource)
        possible-games (filter valid-game? games)]
    (apply + (map :game-number possible-games))))

(defn task-2 [resource]
  (let [games (parse-input resource)
        pulls (map :pulls games)]
    (power-set pulls)))

(comment
  (= 8 (task-1 "day2/sample1.txt"))
  (= 2207 (task-1 "day2/input.txt"))

  (= 2286 (task-2 "day2/sample1.txt"))
  (= 62241 (task-2 "day2/input.txt"))
  ;;
  ,)
