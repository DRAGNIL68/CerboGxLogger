package net.outmoded.cerbogxlogger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.*;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class MqttManager {

    private MqttManager() {}

    private volatile MqttClient mqttClient = null;
    private volatile String uri; private volatile String password;
    private volatile String clientId; private volatile String username;

    private static class SingletonHelper {
        private static final MqttManager SINGLETON_INSTANCE = new MqttManager();
    }

    public static MqttManager getInstance() {
        return SingletonHelper.SINGLETON_INSTANCE;
    }

    public synchronized void connect(@NotNull String uri, @NotNull String password,@NotNull String username){

        if (this.mqttClient != null)
            return;

        this.uri = uri; this.password = password; this.username = username;

        clientId = MqttClient.generateClientId();

        try {
            this.mqttClient = new MqttClient(uri, clientId);
        } catch (MqttException e) {
            Launcher.getLogger().error("CerboGx URI path was invalid");
            return;
        }

        //i dont like this will fix once i am back
        TrustManager [] trustAllCerts = new TrustManager [] {new X509ExtendedTrustManager() {
            @Override
            public void checkClientTrusted (X509Certificate [] chain, String authType, Socket socket) {

            }

            @Override
            public void checkServerTrusted (X509Certificate [] chain, String authType, Socket socket) {

            }

            @Override
            public void checkClientTrusted (X509Certificate [] chain, String authType, SSLEngine engine) {

            }

            @Override
            public void checkServerTrusted (X509Certificate [] chain, String authType, SSLEngine engine) {

            }

            @Override
            public java.security.cert.X509Certificate [] getAcceptedIssuers () {
                return null;
            }

            @Override
            public void checkClientTrusted (X509Certificate [] certs, String authType) {
            }

            @Override
            public void checkServerTrusted (X509Certificate [] certs, String authType) {
            }

        }};

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance ("TLS");
            sc.init (null, trustAllCerts, new java.security.SecureRandom ());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace ();
        }


        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(10);
        options.setSocketFactory(sc.getSocketFactory());

        try {
            this.mqttClient.connect(options);
            Launcher.getLogger().info("connected to {}", uri);
            subscribe();

        } catch (MqttException e) {
            Launcher.getLogger().error("failed to connect: "+e.getCause());
        }
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
