package edu.udemy.micronautwebsocket.server;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.websocket.CloseReason;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ServerWebSocket("/ws/simple/prices")
public class SimpleWebSocketServer {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleWebSocketServer.class);

    @OnOpen
    public Publisher<String> onOpen(WebSocketSession webSocketSession){
        return webSocketSession.send("Connected!");
    }

    @OnClose
    public void onClose(WebSocketSession webSocketSession){
        LOG.debug("Session closed {}", webSocketSession.getId());
    }


    @OnMessage
    public Publisher<String> onMessage(String message, WebSocketSession webSocketSession){
        LOG.debug("Message received {} from session id {}", message, webSocketSession.getId());

        if(message.equalsIgnoreCase("disconnect")){
            LOG.info("Client close request!");
            webSocketSession.close(CloseReason.NORMAL);
            return Publishers.empty();
        }

        return webSocketSession.send("Not Supporter => (".concat(message).concat(")"));
    }

}
