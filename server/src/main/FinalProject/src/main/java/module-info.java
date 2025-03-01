module org.example.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.data;
    requires jakarta.inject;
    requires jakarta.ws.rs;
    requires java.naming;
    requires static lombok;

    opens org.example.finalproject to javafx.fxml;
    exports org.example.finalproject;
}