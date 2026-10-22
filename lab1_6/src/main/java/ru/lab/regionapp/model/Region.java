package ru.lab.regionapp.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Класс-модель для области (Region).
 */
public class Region {

    private final StringProperty name;        // Название
    private final DoubleProperty area;        // Площадь, км²
    private final StringProperty adminCenter; // Административный центр
    private final StringProperty head;        // Глава

    /**
     * Конструктор по умолчанию.
     */
    public Region() {
        this(null, 0, null, null);
    }

    public Region(String name, double area, String adminCenter, String head) {
        this.name = new SimpleStringProperty(name);
        this.area = new SimpleDoubleProperty(area);
        this.adminCenter = new SimpleStringProperty(adminCenter);
        this.head = new SimpleStringProperty(head);
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

    @Override
    public String toString() {
        return getName();
    }
}
