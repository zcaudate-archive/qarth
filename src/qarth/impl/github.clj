(ns qarth.impl.github
  "A Github oauth impl. Type is github.com."
  (require (qarth [oauth :as oauth])
           qarth.impl.oauth-v2
           cheshire.core))

(oauth/derive :github.com :oauth)

(defmethod oauth/build :github.com
  [service]
  (assoc service
         :request-url "https://github.com/login/oauth/authorize"
         :access-url "https://github.com/login/oauth/access_token"))

(defmethod oauth/id :github.com
  [requestor]
  (with-open [body (:body (requestor
                            {:url "https://api.github.com/user" :as :stream}))]
    (-> body cheshire.core/parse-stream (get "id"))))
