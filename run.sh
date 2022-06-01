docker-compose down
docker rmi mangareader:0.0.1-SNAPSHOT
./mvnw -Dmaven.test.skip spring-boot:build-image
docker-compose up -d
