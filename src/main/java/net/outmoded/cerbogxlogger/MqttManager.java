package net.outmoded.cerbogxlogger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;

public class MqttManager {

    private MqttManager() {}

    private volatile MqttClient mqttClient;
    private volatile String uri; private volatile String password;
    private volatile String clientId; private volatile String username;

    private static class SingletonHelper {
        private static final MqttManager SINGLETON_INSTANCE = new MqttManager();
    }

    public static MqttManager getInstance() {
        return SingletonHelper.SINGLETON_INSTANCE;
    }

    public synchronized void connect(@NotNull String uri, @NotNull String password,@NotNull String username){
        if (mqttClient != null)
            return;

        this.uri = uri; this.password = password; this.username = username;

        clientId = MqttClient.generateClientId();

        try {
            this.mqttClient = new MqttClient(uri, clientId);
        } catch (MqttException e) {
            Launcher.getLogger().warn("CerboGx URI path was invalid");
        }

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());

        try {
            mqttClient.connect(options);

        } catch (Exception e) {
            Launcher.getLogger().warn("password was invalid");
        }
        Launcher.getLogger().warn("connected to {}", uri);
        subscribe();
    }

    public void disconnect() throws MqttException {
        if (mqttClient == null)
            return;

        Launcher.getLogger().warn("disconnecting from {}", uri);
        mqttClient.disconnect();
    }

    /**
     * purges all data
     */
    public synchronized void close(){
        if (mqttClient == null)
            return;

        try {
            mqttClient.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        mqttClient = null;
    }

    private void subscribe(){
        if (mqttClient == null)
            return;

        Launcher.getLogger().warn("subscribing to all data feeds");
        // subscribe to all data feeds and place them in the nonexistent
        // sqllite database

    }


}
