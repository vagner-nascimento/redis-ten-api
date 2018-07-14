# Install local maven dependencies
mvn install:install-file -Dfile=./src/main/java/lib/infrastructure/cache/redis/redis-core-ten-1.0.jar -DgroupId=com.vn -DartifactId=redis-client-ten -Dversion=1.0 -Dpackaging=jar

# Set required local variables
export REDIS_SERVER=localhost
echo "REDIS_SERVER:" $REDIS_SERVER