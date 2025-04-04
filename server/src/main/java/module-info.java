module finalproject {
    requires entities; // Add this line

    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.data;
    requires jakarta.inject;
    requires jakarta.ws.rs;
    requires java.naming;
    requires static lombok;

    opens finalproject to javafx.fxml;
    exports finalproject;
}