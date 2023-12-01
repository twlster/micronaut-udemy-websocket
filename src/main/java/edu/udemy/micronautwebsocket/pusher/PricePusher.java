package edu.udemy.micronautwebsocket.pusher;

import edu.udemy.micronautwebsocket.pusher.dto.PriceUpdate;
import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.websocket.WebSocketBroadcaster;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
@AllArgsConstructor
public class PricePusher {

    private final WebSocketBroadcaster webSocketBroadcaster;

    @Scheduled(fixedDelay = "1s")
    public void push() {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        webSocketBroadcaster.broadcastSync(
                new PriceUpdate("AMZN", randomBigDecimal(random))
        );
    }

    private BigDecimal randomBigDecimal(ThreadLocalRandom random) {
        return BigDecimal.valueOf(random.nextDouble(1, 100));
    }

}
