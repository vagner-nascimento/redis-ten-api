# Install local maven dependencies
mvn install:install-file -Dfile=./src/main/java/lib/infrastructure/cache/redis/redis-core-ten-1.0.jar -DgroupId=com.vn -DartifactId=redis-client-ten -Dversion=1.0 -Dpackaging=jar

# Set required local variables, if it doest work put these variables in OS environment file
export REDIS_SERVER=localhost # Required
# export REDIS_PORT=6379 # Optional, default 6379
# export REDIS_PASSWORD=YourPass # Optional, default none

# Run API
mvn clean compile spring-boot:run