package org.omush.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.omush.framework.IGameBuilder;
import org.omush.framework.GameInstance;

import org.omush.network.WebSocketServer;
import java.net.UnknownHostException;

public class GameBuilder implements IGameBuilder {
    private static final Log LOG = LogFactory.getLog(GameBuilder.class);

    @Override
    public void setupInstance(GameInstance instance) {
        setupNetwork(instance);
    }

    private void setupNetwork(GameInstance instance) {
        int port = 1701;

        try {
            WebSocketServer server = new WebSocketServer(port);
            instance.network = server;
        }
        catch (UnknownHostException e) {
            LOG.error("Unable to start websocket server on port: " + port);
        }
    }
}
