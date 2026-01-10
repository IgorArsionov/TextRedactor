module com.example.textredactor {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.textredactor to javafx.fxml;
    exports com.example.textredactor;
}