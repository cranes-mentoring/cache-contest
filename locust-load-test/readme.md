# Load test with locust

Source: [link](https://locust.io)

#### POST request example

Source: [link](https://gist.github.com/subfuzion/08c5d85437d5d4f00e58)

```http request
curl -d '{"username":"test_user", "timestamp":"12312312"}' -H "Content-Type: application/json" -X POST http://localhost:1000/v1/api/order
```

#### using NGINX 
```http request
curl -d '{"username":"test_user", "timestamp":"12312312"}' -H "Content-Type: application/json" -X POST http://localhost/cf/v1/api/order
```