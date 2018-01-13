(ns cljs-await.core
  (:require [cljs.core.async :as async :refer [<! >! put! chan timeout]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(enable-console-print!)

(def set-timeout js/setTimeout)
(def clear-timeout js/clearTimeout)

(defn await
  "works with JS promises

  returns [err res]

  where err - a catched exception (reject)
  res - the result of the promise work (resolve)

  (go
    (let [[err res] (<! (await my-promise))]
      (println res)))
  "
  [promise]
  (let [port (chan)]
    (-> promise
        (.then (fn [res] (put! port [nil res]))
               (fn [err] (put! port [err nil])))
        (.catch (fn [err] (put! port [err nil]))))
    port))

(defn await-cb
  "works with functions, the last argument is the function callback

  returns [err res]

  err - an exception (if it happens)
  res - the result of the work of the callback function (nil - if there was an exception)

  (go
    (let [[err res] (<! (await-cb my-callback))]
      (println res)))
  "
  [fnc & args]
  (let [port (chan)]
    (try
      (apply fnc (concat args [(fn [a] (put! port [nil a]))]))
      (catch js/Error e (put! port [e nil])))
    port))


(comment

  (defn- ->promise [t]
    (js/Promise. (fn [resolve reject] (set-timeout resolve, t, "hello from promise!"))))

  (defn- ->callback []
    (fn [cb] (cb "hello from callback!")))

  (go
    (let [[err res] (<! (await (->promise 2000)))]
      (println res)))

  (go
    (let [[err res] (<! (await-cb (->callback)))]
      (println res)))

  )
