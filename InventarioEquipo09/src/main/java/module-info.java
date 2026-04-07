module com.example.inventarioequipo09 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.inventarioequipo09 to javafx.fxml;
    exports com.example.inventarioequipo09;
}