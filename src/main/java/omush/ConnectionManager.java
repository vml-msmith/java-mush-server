package omush;

import org.joda.time.LocalTime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import omush.Server;
import omush.InputMessage;
import org.java_websocket.WebSocket;

import java.util.HashMap;
import java.util.LinkedList;

public class ConnectionManager implements Server.Listener {
    private static final Log LOG = LogFactory.getLog(ConnectionManager.class);
    private HashMap _connections;
    private LinkedList<InputMessage> _messageQueue = new LinkedList<InputMessage>();

    protected String getConnId(WebSocket conn) {
        Integer hash = conn.getRemoteSocketAddress().hashCode();
        return hash.toString();
    }

    protected void queueInput(String clientId, String message) {
        _messageQueue.add(new InputMessage(clientId, message));
    }

    public InputMessage getNextQueuedInput() {
        if (_messageQueue.isEmpty()) {
            return null;
        }
        return _messageQueue.removeFirst();
    }

    @Override
    public void onOpen(Server serv, WebSocket conn) {
        conn.send("Welcome to the Game!");
        LOG.info("Got a new connection " + conn.getRemoteSocketAddress().hashCode());
        _connections.put(getConnId(conn), conn);
    }

    @Override
    public void onClose(Server serv, WebSocket conn) {
    }

    @Override
    public void onError(Server serv, WebSocket conn, Exception ex) {
    }

    @Override
    public void onMessage(Server serv, WebSocket conn, String message) {
        queueInput(getConnId(conn), message);
    }
}
