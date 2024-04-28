module com.example.foodcentergui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.foodcentergui to javafx.fxml;
    exports com.example.foodcentergui;
}