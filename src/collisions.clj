(ns collisions
  (:require [clojure.spec.alpha :as s]
            [common :as common]))

;; given an array of object coordinates and hitboxes return colliding objects

;; Specs
(s/def ::unit (s/or :int int? :dec float?))
(s/def ::x ::unit)
(s/def ::y ::unit)

(s/def ::measurement ::unit)
(s/def ::height ::unit)
(s/def ::width ::unit)

(s/def ::name string?)

(s/def ::2d-coordinate (s/keys :req-un [::x ::y]))
(s/def ::2d-dimension (s/keys :req-un [::height ::width]))
(s/def ::2d-entity (s/merge (s/keys :opt-un [::name]) ::2d-coordinate ::2d-dimension))

(s/def ::2d-entities (s/coll-of ::2d-entity))

(s/def ::2d-collision (s/and ::2d-entities
                             #(= 2 (count %))))
(s/def ::2d-collisions (s/coll-of ))

(s/fdef collisions
  :args ::2d-entities
  :ret ::2d-collisions)

(s/fdef collision
  :args (s/cat :A ::2d-entity
               :B ::2d-entity))

;;;; Detecting Collisions
;; TODO: Spec
(defn- get-right [{:keys [x width]}] (+ x width))
(defn- get-left [{:keys [x]}] x)
(defn- get-top [{:keys [y]}] y)
(defn- get-bottom [{:keys [y height]}] (+ y height))

;; TODO: get real implementation
(defn collides? [entity-a entity-b]
  (and
   (< (get-left entity-a) (get-right entity-b))
   (< (get-left entity-b) (get-right entity-a))
   (< (get-top entity-b) (get-bottom entity-a))
   (< (get-top entity-a) (get-bottom entity-b))))

#_(get-left {:x 0 :y 1 :width 2 :height 2})
#_(get-right {:x 0 :y 1 :width 2 :height 2})
#_(get-top {:x 0 :y 1 :width 2 :height 2})
#_(get-bottom {:x 0 :y 1 :width 2 :height 2})
#_(collides? {:x 0 :y 1 :width 2 :height 2}
             {:x 1 :y 1 :width 2 :height 2})

(defn collision [entity-a entity-b]
  (if (collides? entity-a entity-b)
    (list entity-a entity-b)))

#_(collision {:x 0 :y 1 :width 2 :height 2}
             {:x 1 :y 1 :width 2 :height 2})

(defn collisions [entities]
  (loop [[first & rest] entities
         result []]
    (if (nil? rest)
      result
      (recur rest (conj result
                        (mapcat (partial collision first) rest))))))

#_(collisions '({:x 0 :y 1 :width 2 :height 2}
                {:x 5 :y -2 :width 2 :height 2}
                {:x 22 :y 1 :width 2 :height 2}
                {:x 6 :y 2 :width 2 :height 2}
                {:x 1 :y 1 :width 2 :height 2}
                {:x 5 :y 1 :width 2 :height 2}))

#_(s/conform (s/* (s/or :odd odd? :even even?))
             [1 2 3 4])

#_(common/conform-ex ::2d-entity {:name "Testing"
                                  :x 0
                                  :y 0
                                  :width 1
                                  :height 2})

#_(s/explain ::2d-entities '({:x 0
                              :y 0
                              :width 1
                              :height 2}))



'({:x 0 :y 1}
  {:x 2 :y 2})
