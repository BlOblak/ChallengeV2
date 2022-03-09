```bash
$ curl 'https://api-host/public/measurement/byRange' -i -X POST \
    -H 'Content-Type: application/json' \
    -d '{
  "from" : "2022-02-27T19:25:49.702924300Z",
  "to" : "2022-02-27T19:25:58.702924300Z"
}'
```