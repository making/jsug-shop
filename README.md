    mvn clean package

### Deploy to Pivotal Web Services

    cf create-service cleardb spark shop-db
    cf create-service rediscloud 30mb shop-redis
    cf push


### Deploy to Pivotal Cloud Foundry

    cf create-service p-mysql 100mb-dev shop-db
    cf create-service p-redis shared-vm shop-redis
    cf push
