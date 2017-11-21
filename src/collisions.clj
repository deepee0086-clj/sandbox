(ns collisions
  (:require [clojure.spec.alpha :as s]
            [common :as common]))

;; given an array of object coordinates and hitboxes return colliding objects
(s/def ::unit (s/or :int int? :dec float?))
(s/def ::name string?)
(s/def ::x ::unit)
(s/def ::y ::unit)

(s/def ::measurement ::unit)
(s/def ::height ::unit)
(s/def ::width ::unit)

(s/def ::2d-coordinate (s/keys :req-un [::x ::y]))
(s/def ::2d-dimension (s/keys :req-un [::height ::width]))
(s/def ::2d-entity (s/merge (s/keys :opt-un [::name]) ::2d-coordinate ::2d-dimension))

(s/def ::2d-entities (s/coll-of ::2d-entity))

(s/fdef collisions
  :args ::2d-entities)

(defn collisions [entities]
  )

#_(s/conform (s/* (s/cat :odd odd?
                         :even even?))
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
