# Redis Ten API
A HTTP rest API (implemented with Spring Boot) to publish redis commands

# Installation
Edit your parameters in "install.sh" and execute it to update and install libraries and run the API with default configurations of Spring Boot

# Commands to call using curl
WARNING: Without Content-Type header some commands doesn't work
# SET and SET EX seconds
- curl -X PUT -H "Content-Type: text/plain" -d "Eggs, cheese, meat, beer and wine" http://localhost:8080/{food}
- curl -X PUT -H "Content-Type: text/plain" -d "Eggs, cheese, meat, beer and wine" http://localhost:8080/{food-tmp}/{10}

# GET
- curl -X GET -H "Content-Type: text/plain" http://localhost:8080/{food}

# DEL e multi DEL
- curl -X DELETE -H "Content-Type: text/plain" http://localhost:8080/{key-1}
- curl -X DELETE -H "Content-Type: text/plain" http://localhost:8080/{key-1%20key-2%20key-N}

# DBSIZE
- curl -X GET -H "Content-Type: text/plain" http://localhost:8080/dbsize

# INCR
- curl -X PUT -H "Content-Type: text/plain" http://localhost:8080/incr/{key}

# ZADD
- curl -X PUT -H "Content-Type: text/plain" -d "524.89 RazorBlade 845 KevinBacon 999 Gandhi" http://localhost:8080/zadd/{players}

# ZCARD
- curl -X GET -H "Content-Type: text/plain" http://localhost:8080/zcard/{players}

# ZRANK
- curl -X GET -H "Content-Type: text/plain" http://localhost:8080/zrank/{players}/{KevinBacon}

# ZRANGE
- curl -X GET -H "Content-Type: text/plain" http://localhost:8080/zrange/players/{0}/{3}

# Next steps
- Dockerize it all
- Implement automatized unit and integration tests.
