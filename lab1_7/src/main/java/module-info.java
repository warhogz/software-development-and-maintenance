module regionapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;
    requires java.prefs;

    opens ru.lab.regionapp.view to javafx.fxml;
    opens ru.lab.regionapp.model to jakarta.xml.bind, org.glassfish.jaxb.runtime;
    exports ru.lab.regionapp;
    exports ru.lab.regionapp.model;
}
