## System Configuration

This chapter describes all configuration parameters for the Challenge v2 module. 

## Environment variables

All properties can be supplied to the module through environment variables.

Environment Variable    |Description 
------------- | ------------- 
DB_HOST | Database host
DB_PORT | Database port
DB_NAME | Database name
RABBITMQ_USERNAME | RabbitMq username
RABBITMQ_PASSWORD | RabbitMq password
RABBITMQ_HOST | RabbitMq host
RABBITMQ_PORT | RabbitMq port
RABBITMQ_VIRTUAL_HOST | RabbitMq virtual host
RABBITMQ_LISTENER_PREFETCH | How many messages to send to each consumer in a single request
RABBITMQ_CONSUMERS_PER_QUEUE | The minimum number of consumers to creat
REDIS_CACHE_NAMES | Redis cache names 
REDIS_TTL | Redis key expiration time 
SENTINEL_NODES | Redis sentinel nodes IP and port separated by comma
REDIS_SENTINEL_MASTER | Redis master sentinel name
REDIS_PASSWORD | Redis password
REDIS_TIMEOUT  | Redis command time-out
REDIS_SHUTDOWN_TIMEOUT | Redis shutdown time-out

## Message Queue Configuration

This chapter describes all queues and exchanges for the Challenge v2 module. 

### Exchanges

 Name | Type 
--- | --- 
TestExchange | direct 
 
### Queues

 Name | Arguments
 --- | ---
challenge.v2.measurement.insert       | 
