package omush;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;


import org.joda.time.LocalTime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import omush.Server;
import omush.ConnectionManager;
import omush.InputMessage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    private final static Log LOG = LogFactory.getLog(Game.class);
    private ConnectionManager _connectionManager = new ConnectionManager();

    private void loopServer() {
    }

    private  void startServer(int port) {
        LOG.info("Creating Echo server. (Port = " + port + ")");
        try {
            Server server = new Server(1701);
            server.registerListener(_connectionManager);
            server.start();
            System.out.println( "ChatServer started on port: " + server.getPort() );


            final Runnable loopServer = new Runnable() {
                    public void run() {
                        try {
                            if (_connectionManager != null) {
                                InputMessage msg = _connectionManager.getNextQueuedInput();
                                if (msg != null) {
                                    System.out.println(msg.message());
                                }
                            }
                        } catch (Exception e) {
                            LOG.error("EchoServer err: " + e.toString());
                            e.printStackTrace();
                        }
                    }
                };

            final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(loopServer, 0, 1, TimeUnit.SECONDS);

        } catch (Exception e) {
            LOG.error("EchoServer err: " + e.toString());
            e.printStackTrace();
        }
    }

    public void run() {
        startServer(1701);
    }
}
