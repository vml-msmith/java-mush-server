package omush;

import java.net.UnknownHostException;
import java.net.InetSocketAddress;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.framing.CloseFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;


public class Server extends WebSocketServer {
    private static final Log LOG = LogFactory.getLog(Server.class);

    public interface Listener {
        void onOpen(Server serv, WebSocket conn);
        void onClose(Server serv, WebSocket conn);
        void onError(Server serv, WebSocket conn, Exception ex);
        void onMessage(Server serv, WebSocket conn, String message);
    }

    private ArrayList<Listener> _listeners = new ArrayList<Listener>();

    public Server(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        //this.listener = listener;
        if (LOG.isDebugEnabled()) {
            LOG.debug("EchoServer() : " + port);
        }
    }

    public void registerListener(Listener listener) {
        _listeners.add(listener);
    }

    @Override
    public void onError( WebSocket conn, Exception ex ) {
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        for (Listener l : _listeners) {
            System.out.println( "send" );
            l.onMessage(this, conn, message);
        }

        if (message.equals("QUIT")) {
            conn.close(CloseFrame.NORMAL, "goodebye");
        }
        else if (message.equals("Hello")) {
            conn.send("Hello World!");
        }
        else {
            conn.send(message);
        }
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
    }

    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
        for (Listener l : _listeners) {
            l.onOpen(this, conn);
        }
    }

}
