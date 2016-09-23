package org.omush.network;

public interface ISocketServer {
    abstract public void startIfNeeded();
    abstract public void loop();
}
