module org.example.javafxx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.naming;


    opens org.example.javafxx to javafx.fxml;
    exports org.example.javafxx;
    exports org.example.javafxx.CONTROLLER;
    exports org.example.javafxx.MODELS;
    opens org.example.javafxx.CONTROLLER to javafx.fxml;
    opens org.example.javafxx.MODELS to javafx.base;
}