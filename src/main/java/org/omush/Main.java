package org.omush;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.omush.framework.GameBuilder;
import org.omush.framework.GameInstance;
import org.omush.framework.Game;

public class Main {
    private final static Log LOG = LogFactory.getLog(Main.class);

    static private class GameTimer {
        private long timePerLoop;
        private long thisTime;
        private long lastTime;

        public GameTimer(long loopTime) {
            timePerLoop = loopTime;
        }
        public void start() {
            updateThisTime();
            lastTime = thisTime;
        }

        public void sleep() {
            updateThisTime();

            long duration = thisTime - lastTime;
            if (duration < timePerLoop) {
                long sleepTime = timePerLoop - duration;

                try {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException e) {
                    LOG.error("Caught interrupt in the sleep method.");
                }
            }
            lastTime = System.currentTimeMillis();
        }

        private void updateThisTime() {
            thisTime = System.currentTimeMillis();
        }
    }

    public static void main(String[] args) {
        GameBuilder gameBuilder = new GameBuilder();
        GameInstance instance = new GameInstance();
        Game game = new Game();

        if (game.initialize(instance, gameBuilder) == false) {
            LOG.error("Unable to initialize game.");
            return;
        }

        long loopMilliseconds = 1000;
        GameTimer timer = new GameTimer(loopMilliseconds);
        timer.start();

        while (game.loop()) {
            timer.sleep();
        }
    }
}
