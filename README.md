## Run prepared demo app

    docker run -d --name redis -p 6379:6379 redis
    docker run -ti --rm --link redis:redis -p 8080:8080 making/jsug-shop

or

    docker-compose up

## Build your own Docker image

    mvn clean package
    docker build -t username/jsug-shop

After building the image,

    docker run -d --name redis -p 6379:6379 redis
    docker run -ti --rm --link redis:redis -p 8080:8080 username/jsug-shop
