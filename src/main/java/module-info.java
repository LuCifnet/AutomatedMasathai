module com.example.helllllllooo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.logging;
    requires java.sql;
    requires mysql.connector.j;
    requires fontawesomefx;
    requires jbcrypt;

    opens com.example.helllllllooo to javafx.fxml;
    exports com.example.helllllllooo;
}