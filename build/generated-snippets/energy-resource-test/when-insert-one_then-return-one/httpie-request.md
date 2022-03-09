```bash
$ echo '{
  "id" : 0,
  "timePoint" : "2022-02-27T19:25:46.746+00:00",
  "assetId" : "dcb5077a-6681-44f6-8918-8c38aebca56f",
  "activePower" : 100,
  "voltage" : 230
}' | http POST 'https://api-host/public/measurement' \
    'Content-Type:application/json'
```