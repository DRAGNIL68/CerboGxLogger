module net.outmoded.cerbogxlogger {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.outmoded.cerbogxlogger to javafx.fxml;
    exports net.outmoded.cerbogxlogger;
}