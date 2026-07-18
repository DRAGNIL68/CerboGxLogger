module net.outmoded.cerbogxlogger {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.yaml.snakeyaml;
    requires org.eclipse.paho.client.mqttv3;
    requires org.jetbrains.annotations;


    opens net.outmoded.cerbogxlogger to javafx.fxml;
    exports net.outmoded.cerbogxlogger;
}