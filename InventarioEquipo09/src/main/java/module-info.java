module com.example.inventarioequipo09 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.inventarioequipo09 to javafx.fxml;
    exports com.example.inventarioequipo09;
    exports Controllers;
    opens Controllers to javafx.fxml;
    exports ProductoFile;
    opens ProductoFile to javafx.fxml;
    exports ProductoService;
    opens ProductoService to javafx.fxml;
}