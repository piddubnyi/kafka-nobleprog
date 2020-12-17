Overview of Distributed Messaging Systems
Alternatives to Kafka
    - ActiveMQ / RabbitMQ (no real data distribution)
    - zmq (no disk persistence)
    - nats (no disk persistence)
    - apache pulsar

Use Cases
    - Messaging
    - Website Activity Tracking
    - Metrics
    - Log Aggregation
    - Stream Processing
    - Event Sourcing

Kafka API


Producer API
High Level Consumer API
Simple Consumer API
Kafka Hadoop Consumer API

Configuration
New Producer
Broker



Consumer and Producer Configuration
Design
Motivation
Persistence
Efficiency
The Producer
The Consumer
Message Delivery Semantics


·         Как копровать данные из одного топика на другой на постоянной основе?
·         Как архивировать данные из одого топика в такой же, но архивный на постоянной основе
·         Как создать аргегированный топик из нескольких?
·         Авторризация в разрезе топиков
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
