package org.omush.network;

import org.omush.network.ISocketServer;

import java.net.UnknownHostException;
import java.net.InetSocketAddress;

//import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.framing.CloseFrame;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebSocketServer extends org.java_websocket.server.WebSocketServer implements ISocketServer {
    private static final Log LOG = LogFactory.getLog(WebSocketServer.class);
    private boolean hasBeenStarted = false;


    public WebSocketServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));

        if (LOG.isDebugEnabled()) {
            LOG.debug("EchoServer() : " + port);
        }
    }

    @Override
    public void startIfNeeded() {
        if (hasBeenStarted == false) {
            start();
            hasBeenStarted = true;
        }
    }

    @Override
    public void loop() {
    }


    @Override
    public void onError( WebSocket conn, Exception ex ) {
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        LOG.info("Got a message: " + message);
        conn.send(message);
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
    }

    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
    }
}
