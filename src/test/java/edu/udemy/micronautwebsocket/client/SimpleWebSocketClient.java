package edu.udemy.micronautwebsocket.client;

import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.reactivex.Single;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

@ClientWebSocket("/ws/simple/prices")
public abstract class SimpleWebSocketClient implements AutoCloseable {

    private final Collection<String> observedMessages = new ConcurrentLinkedDeque<>();
    private WebSocketSession session;

    @OnOpen
    public void open(WebSocketSession webSocketSession) {
        this.session = webSocketSession;
    }

    public abstract void send(String message);

    public abstract Single<String> sendReactive(String message);

    @OnMessage
    public void onMessage(String message) {
        observedMessages.add(message);
    }

    public Collection<String> getObservedMessages(){
        return observedMessages;
    }

    public WebSocketSession getSession(){
        return  session;
    }


}
