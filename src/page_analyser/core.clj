(ns page-analyser.core
   (:require [clojure.java.io :as io]))

;; To be replaced with words from a loaded file/url
(def PHRASE "It was a cold grey day in late November The weather had changed overnight when a backing wind brought a granite sky and a mizzling rain with it and although it was now only a little after two o'clock in the afternoon the pallor of a winter evening seemed to have closed upon the hills cloaking them in mist")

(defn parse-unimportant-words-from-file [file]
  (->>   (-> file io/resource io/file)
         (slurp)
         (re-seq #"\w+")))

(defn get-words-from-phrase [phrase]
  (clojure.string/split (.toLowerCase phrase) #" "))

(defn create-word-freq-map [words]
  (frequencies words))

(defn clean-freq-map-from-unimportant-words [unimportant-words word-freq-map]
  (apply dissoc word-freq-map unimportant-words))

(def unimportant-words (parse-unimportant-words-from-file "unimportant-words.txt"))

(def clean-word-freq-map
  (->> PHRASE
       (get-words-from-phrase)
       (create-word-freq-map)
       (clean-freq-map-from-unimportant-words unimportant-words)))

(defn get-word-use-frequency [word-freq-map]
  (let [total-word-uses (reduce + (vals word-freq-map))]
   (reduce #(assoc % (key %2) (double(* (/ (val %2) total-word-uses) 100)))
        {}
        word-freq-map)))

clean-word-freq-map

(get-word-use-frequency clean-word-freq-map)
