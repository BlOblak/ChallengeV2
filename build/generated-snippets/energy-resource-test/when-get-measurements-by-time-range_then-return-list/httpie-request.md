```bash
$ echo '{
  "from" : "2022-02-27T19:25:49.702924300Z",
  "to" : "2022-02-27T19:25:58.702924300Z"
}' | http POST 'https://api-host/public/measurement/byRange' \
    'Content-Type:application/json'
```