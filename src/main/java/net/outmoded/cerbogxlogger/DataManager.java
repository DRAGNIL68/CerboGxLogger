package net.outmoded.cerbogxlogger;

public class DataManager {
    
    private static DataManager instance;
    
    private DataManager() {}

    private static class SingletonHelper {
        private static final DataManager SINGLETON_INSTANCE = new DataManager();
    }
    
    public static synchronized DataManager getInstance() {
        return SingletonHelper.SINGLETON_INSTANCE;
    }


}
