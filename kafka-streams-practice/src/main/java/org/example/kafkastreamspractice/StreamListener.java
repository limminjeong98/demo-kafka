package org.example.kafkastreamspractice;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class StreamListener {

    @Bean
    public KStream<String, String> kStream(StreamsBuilder builder) {
        final String inputTopic = "checkout.complete.v1";
        final String outputTopic = "checkout.productId.aggregated.v1";

        KStream<String, String> inputStream = builder.stream(inputTopic);
        inputStream
                .map((k, v) -> new KeyValue<>(JsonUtils.getProductId(v), JsonUtils.getAmount(v)))
                // group by productId
                .groupByKey(Grouped.with(Serdes.Long(), Serdes.Long()))
                // window 설정
                .windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
                // apply sum method
                .reduce(Long::sum)
                // map the window key
                .toStream((key, value) -> key.key())
                // outputTopic에 보낼 JsonString으로 generate
                .mapValues(JsonUtils::getSendingJson)
                // outputTopic으로 보낼 key 값을 null 설정
                .selectKey((key, value) -> null)
                // outputTopic으로 메세지(null, jsonString) 전송 설정
                .to(outputTopic, Produced.with(null, Serdes.String()));

        return inputStream;
    }
}
