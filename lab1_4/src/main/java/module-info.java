module regionapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.lab.regionapp.view to javafx.fxml;
    exports ru.lab.regionapp;
    exports ru.lab.regionapp.model;
}
