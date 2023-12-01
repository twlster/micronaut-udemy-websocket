package edu.udemy.micronautwebsocket.server;

import edu.udemy.micronautwebsocket.client.SimpleWebSocketClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.websockets.RxWebSocketClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MicronautTest
public class SimpleWebSocketServerTest {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleWebSocketServerTest.class);

    @Inject
    @Client("http://localhost:8180")
    RxWebSocketClient client;

    private SimpleWebSocketClient webSocketClient;

    @BeforeEach
    public void connect(){
        webSocketClient =
                client.connect(SimpleWebSocketClient.class, "/ws/simple/prices").blockingFirst();
    }

    @Test
    void canReceiveMessageWithClient(){
        webSocketClient.send("Hello");
        Awaitility.await().untilAsserted(() -> {
            final Object[] messages = webSocketClient.getObservedMessages().toArray();
            LOG.info("Observed messages {} - {}", messages.length, messages);
            Assertions.assertEquals("Connected!", messages[0]);
            Assertions.assertEquals("Not Supporter => (Hello)", messages[1]);
        });

    }

    @Test
    void canSendReactive(){
        LOG.info("Send {}",webSocketClient.sendReactive("Hello").blockingGet());
        Awaitility.await().untilAsserted(() -> {
            final Object[] messages = webSocketClient.getObservedMessages().toArray();
            LOG.info("Observed messages {} - {}", messages.length, messages);
            Assertions.assertEquals("Connected!", messages[0]);
            Assertions.assertEquals("Not Supporter => (Hello)", messages[1]);
        });

    }

}
