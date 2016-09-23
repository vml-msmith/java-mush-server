package org.omush.framework;

import org.omush.framework.GameInstance;
import org.omush.framework.IGameBuilder;

public interface IGame {
    public boolean initialize(GameInstance instance,
                              IGameBuilder gameBuilder);
    public boolean isInitialized();
    public boolean loop();
}
