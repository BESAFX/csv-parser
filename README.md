# CSV Parser Microservice

Java Microservice for Reading and parsing large number and sized CSV files.

## Installation

Run docker compose file to setup kafka and zookeeper and make sure you already have docker, otherwise you can use this link to setup kafka locally 
(https://kafka.apache.org/quickstart)

```bash
docker-comopse up
```


## Usage

In resource folder, 3 sample continas dummy data for testing you can use them while calling REST API for read and processing files

Endpoint:
```bash
POST /v1/api/parser/csv/read
```

Request Body:
```bash
key => files and Value => select samples
```

Response:
```bash
{
    "passedFiles": [
        "sample1.csv",
        "sample2.csv",
        "sample3.csv"
    ],
    "failedFiles": []
}
```


## Tools & Libraries: 

Java 11

Spring Boot 2.1.9.RELEASE

Spring Kafka 

Google Guava

Apache Common

Lombok


