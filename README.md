

# Swagger URL:

http://localhost:8080/swagger-ui/index.html

# Build the docker project (multi-stage)

docker build -t todo .

# Run the docker project

docker run -p 8080:8080 -d todo

# Tag the image

docker image tag todo kamenskikh/todo:version1.0

# Push docker image

docker push kamenskikh/todo:version1.0