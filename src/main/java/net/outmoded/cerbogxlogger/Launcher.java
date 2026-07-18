package net.outmoded.cerbogxlogger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;

public class Launcher {
    private static final String version = "v1";
    public static void main(String[] args) {
        Launcher.getLogger().info("starting CerboGxLogger");
        Launcher.getLogger().info("version: {}", Launcher.getVersion());
        LangManager.getInstance().load();

        String clientId = MqttClient.generateClientId();
        MqttManager.getInstance().connect("ssl://192.168.1.194:8883", "582204", clientId);

        if (args.length >= 1 && args[0].equals("nogui")){

        }
        else {
            javafx.application.Application.launch(Application.class, args);
        }


    }


    public static Logger getLogger(){return LogManager.getLogger("CerboGx");}

    public static String getVersion(){return version;}
}
