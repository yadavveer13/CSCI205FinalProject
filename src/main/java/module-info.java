module csci205_final_project{
    requires java.base;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports org.team09;
    opens org.team09 to javafx.fxml;
    exports org.team09.Servers;
    opens org.team09.Servers to javafx.fxml;
    exports org.team09.Clients;
    opens org.team09.Clients to javafx.fxml;
}