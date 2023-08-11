module com.github.idelstak.autoresponder {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;
    //requires jakarta.activation;

    opens com.github.idelstak.autoresponder to javafx.fxml;
    exports com.github.idelstak.autoresponder;
}