package io.github.andia92.aws.websockets.handlers;

import io.github.andia92.aws.websockets.SessionManager;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


@WebSocket
public class JSONWebSocketHandler {

    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = "User" + SessionManager.nextUserNumber++;
        SessionManager.userUsernameMap.put(user, username);
        SessionManager.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = SessionManager.userUsernameMap.get(user);
        SessionManager.userUsernameMap.remove(user);
        SessionManager.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        SessionManager.broadcastMessage(sender = SessionManager.userUsernameMap.get(user), msg = message);
    }

}