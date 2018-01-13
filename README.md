# cljs-await

`[cljs-await "1.0.0-SNAPSHOT"]`

A very simple ClojureScript library for working with promises and callbacks with core.async.

## await

Works with JS promises. Returns [err res] Where err - a catched exception (reject) and res - the result of the promise work (resolve).

```
  (ns my-ns
    (:require [cljs-await.core :refer [await])
  
  (defn- ->promise [t]
    (js/Promise. (fn [resolve reject] (set-timeout resolve, t, "hello from promise!"))))
    
  (go
    (let [[err res] (<! (await (->promise 2000)))]
      (println res)))
```

## await-cb

Works with functions, the last argument is the function callback. Returns [err res]. Where err - an exception (if it happens) and res - the result of the work of the callback function (nil - if there was an exception).

```
  (ns my-ns
    (:require [cljs-await.core :refer [await-cb])
  
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