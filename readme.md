```sudo bin/kafka-avro-console-producer \
       --broker-list kafka1.com:9092,kafka2.com:9092,kafka3.com:9092 \
       --topic test-avro \
       --property \
         schema.registry.url=http://schemaregistry1.com:8081 \
       --property \
         value.schema='{"type": "record","name": "myrecord","doc": "This is a myrecord schema","namespace": "com.landoop","fields": [{"name": "f1","type": "string"}]}'
```
    
    {"f1": "value1"}
    {"f1": "value2"}
    {"f1": "value3"}
    
```
    sudo bin/kafka-avro-console-consumer \
    --bootstrap-server kafka1.com:9092,kafka2.com:9092,kafka3.com:9092 \
    --topic test-avro \
    --property \
             schema.registry.url=http://schemaregistry1.com:8081 \
    --from-beginning
```