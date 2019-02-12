# cljs-await

`[cljs-await cljs-await "1.0.2"]`

A very simple ClojureScript library for working with promises and callbacks via core.async.

## await

Works with JS promises. Returns [err res] where err - a caught exception (reject) and res - the result of the promise work (resolve).

```
  (ns my-ns
    (:require [cljs-await.core :refer [await-cb]
              [cljs.core.async :as async :refer [<! >! put! chan timeout]])
    (:require-macros [cljs.core.async.macros :refer [go go-loop]]))
  
  (defn- ->promise [t]
    (js/Promise. (fn [resolve reject] (js/setTimeout resolve, t, "hello from promise!"))))
    
  (go
    (let [[err res] (<! (await (->promise 2000)))]
      (println res)))
```

## await-cb

Works with functions, the last argument is the function callback. Returns [err res]. Where err - an exception (if it happens) and res - the result of the work of the callback function (nil - if there was an exception).

```
  (ns my-ns
    (:require [cljs-await.core :refer [await-cb]
              [cljs.core.async :as async :refer [<! >! put! chan timeout]])
    (:require-macros [cljs.core.async.macros :refer [go go-loop]]))
  
  (defn- ->callback []
    (fn [cb] (cb "hello from callback!")))

  (go
    (let [[err res] (<! (await-cb (->callback)))]
      (println res)))
```

## License

License

Copyright © 2017 Eugene Potapenko

Distributed under the Eclipse Public License, the same as Clojure.
