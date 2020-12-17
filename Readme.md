Overview of Distributed Messaging Systems
Alternatives to Kafka
    - ActiveMQ / RabbitMQ (no real data distribution)
    - zmq (no disk persistence)
    - nats (no disk persistence)
    - apache pulsar

https://github.com/tszmytka/benchmark-mq

Use Cases
    - Messaging
    - Website Activity Tracking
    - Metrics
    - Log Aggregation
    - Stream Processing
    - Event Sourcing

Deployment and startup
 - native
 - docker
 - k8s

Kafka API
./bin/kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --describe *
./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic my-topic

Simple Producer
Producer API
Simple Consumer
Consumer API

Configuration
Broker
    - broker.id
    - log.dirs
    - zookeeper.connect 
    - advertised.listeners
    - advertised.port
    - log.retention.bytes
    - log.retention.hours
Topic
    - compression.type
Consumer and Producer Configuration
    - bootstrap.servers
    - (Producer) acks
    - compression.type
    - (Consumer) group.id group.instance.id

Design
Persistence
    All data is immediately written to a persistent log on the filesystem without necessarily flushing to disk.
    In effect this just means that it is transferred into the kernel's pagecache.
Efficiency
     Data buffering (avoid small IO and frequent flushes) 
     Reduce sendfile and using the zero-copy
     !Batch Compression!

The Producer
    key is used for partitioning
    async sending + batches
    no producer persistence
The Consumer
    - pull mode (+blocking)
    - event mode
    - consumer position (offset)
    - Static Membership

Message Delivery Semantics

    At most once — Messages may be lost but are never redelivered.
        Consumer read the messages, then save its position in the log, and finally process the messages
        Consumer process crashes after saving its position but before saving the output
    At least once — Messages are never lost but may be redelivered.
        Consumer read the messages, process the messages, and finally save its position
        Crashes after processing messages but before saving its position
    Exactly once — each message is delivered once and only once.
        The consumer's position is stored as a message in a topic, 
        Write the offset to Kafka in the same transaction as the output topics receiving the processed data. 
        If the transaction is aborted, the consumer's position will revert



·         Как копровать данные из одного топика на другой на постоянной основе?
·         Как архивировать данные из одого топика в такой же, но архивный на постоянной основе
·         Как создать аргегированный топик из нескольких?
·         Авторризация в разрезе топиков https://kafka.apache.org/documentation/#security_authz
·         имплементация ksql и для стримов


Replication
Implementation
API Design
Network Layer
Messages
Message format
Log
Distribution
Basic Kafka Operations
Adding and removing topics
Modifying topics

Graceful shutdown
Balancing leadership
Checking consumer position
Mirroring data between clusters
Expanding your cluster
Decommissioning brokers
Increasing replication factor

Other
Datacenters
Important Server Configs
Important Client Configs
Production Server Configs

Monitoring
ZooKeeper


·         как удобно искать по тегам сообщение в топике, было ли или не было
·         интеграция авро в уже существующее решение
·         log compacted топики
·         Tools for troubleshooting
·         Monitoring Approaches
