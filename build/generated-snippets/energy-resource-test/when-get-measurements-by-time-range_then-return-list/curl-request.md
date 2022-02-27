```bash
$ curl 'https://api-host/public/measurement/byRange' -i -X POST \
    -H 'Content-Type: application/json' \
    -d '{
  "from" : "2022-02-27T12:35:03.436641500Z",
  "to" : "2022-02-27T12:35:12.436641500Z"
}'
```