package org.omush.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.omush.framework.IGame;
import org.omush.framework.GameInstance;
import org.omush.framework.IGameBuilder;

import org.omush.network.ISocketServer;

public class Game implements IGame {
    private final static Log LOG = LogFactory.getLog(Game.class);
    private GameInstance gameInstance;

    @Override
    public boolean initialize(GameInstance instance,
                              IGameBuilder gameBuilder) {
        gameInstance = instance;
        gameBuilder.setupInstance(instance);

        return isInitialized();
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    @Override
    public boolean loop() {
        if (gameInstance.network != null) {
            gameInstance.network.startIfNeeded();
            gameInstance.network.loop();
        }

        //        LOG.info("Looping the game");
        return true;
    }
}
