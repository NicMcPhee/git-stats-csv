(ns git-stats-csv.core
  (require [clojure.data.json :as json])
  (:gen-class))

(defn read-repo-names [repo-name-file]
  (with-open [rdr (clojure.java.io/reader repo-name-file)]
    (vec (line-seq rdr))))

(defn repo-name-2-api-url [repo-name]
  (str "https://api.github.com/repos/" repo-name))

(defn get-repo-json [api-url]
  (json/read-str (slurp api-url)))

(def desired-keys
  [
   "name"
   "html_url"
   "language"
   "commits"
   "contributors"
   "watchers"
   "stargazers_count"
   "forks"])

(defn extract-stats [repo-map]
  (select-keys repo-map desired-keys))

(defn stats-to-csv-line [x] x)

(defn join-csv-lines [x] x)

(defn print-csv-file [x] x)

(defn -main
  "Read Github.com repo names from the given file and output
   a CSV file containing various statistics about those repositories."
  [repo-name-file & args]
  (println
   (->>
     repo-name-file
     (read-repo-names)
     (map repo-name-2-api-url)
     (map get-repo-json)
     (map extract-stats)
     (map stats-to-csv-line)
     (join-csv-lines)
     (print-csv-file))))
