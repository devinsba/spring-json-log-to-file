## Download tracer

```
wget -O dd-java-agent.jar https://dtdg.co/latest-java-tracer
```

## Run with tracer

```
./gradlew bootJar
java -javaagent:dd-java-agent.jar -jar build/libs/log-to-file-0.0.1-SNAPSHOT.jar
```

## Requests

```shell
curl --location 'http://localhost:8080/cities' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Dover",
    "state": "NH"
}'

curl --location 'http://localhost:8080/cities/1'
```