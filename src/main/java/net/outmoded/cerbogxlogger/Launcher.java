package net.outmoded.cerbogxlogger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher {
    private static final String version = "v1";
    public static void main(String[] args) {
        Launcher.getLogger().info("starting CerboGxLogger");
        Launcher.getLogger().info("version: {}", Launcher.getVersion());
        LangManager.getInstance().load();

//        if (!args[0].equals("nogui")){
//            javafx.application.Application.launch(Application.class, args);
//        }


    }


    public static Logger getLogger(){return LogManager.getLogger("CerboGx");}

    public static String getVersion(){return version;}
}
