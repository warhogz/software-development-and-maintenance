package ru.lab.regionapp.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Класс-модель для района (District).
 */
public class District {

    private final StringProperty name;           // Название
    private final DoubleProperty area;           // Площадь, км²
    private final StringProperty adminCenter;    // Административный центр
    private final StringProperty head;           // Глава
    private final ObjectProperty<Region> region; // Область, в которую входит район

    /**
     * Конструктор по умолчанию.
     */
    public District() {
        this(null, 0, null, null, null);
    }

    public District(String name, double area, String adminCenter, String head, Region region) {
        this.name = new SimpleStringProperty(name);
        this.area = new SimpleDoubleProperty(area);
        this.adminCenter = new SimpleStringProperty(adminCenter);
        this.head = new SimpleStringProperty(head);
        this.region = new SimpleObjectProperty<>(region);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getArea() {
        return area.get();
    }

    public void setArea(double area) {
        this.area.set(area);
    }

    public DoubleProperty areaProperty() {
        return area;
    }

    public String getAdminCenter() {
        return adminCenter.get();
    }

    public void setAdminCenter(String adminCenter) {
        this.adminCenter.set(adminCenter);
    }

    public StringProperty adminCenterProperty() {
        return adminCenter;
    }

    public String getHead() {
        return head.get();
    }

    public void setHead(String head) {
        this.head.set(head);
    }

    public StringProperty headProperty() {
        return head;
    }

    public Region getRegion() {
        return region.get();
    }

    public void setRegion(Region region) {
        this.region.set(region);
    }

    public ObjectProperty<Region> regionProperty() {
        return region;
    }
}
