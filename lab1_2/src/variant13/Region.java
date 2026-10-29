package variant13;

import java.util.ArrayList;
import java.util.List;

/** Область */
public class Region {
    private final String name;        // Название
    private final double area;        // Площадь, км²
    private final String adminCenter; // Административный центр
    private final String head;        // Глава
    private final List<District> districts = new ArrayList<>();

    public Region(String name, double area, String adminCenter, String head) {
        this.name = name;
        this.area = area;
        this.adminCenter = adminCenter;
        this.head = head;
    }

    public String getName() { return name; }
    public double getArea() { return area; }
    public String getAdminCenter() { return adminCenter; }
    public String getHead() { return head; }
    public List<District> getDistricts() { return districts; }

    public void addDistrict(District district) {
        districts.add(district);
    }

    /** Выполнить действие (лямбду) для каждого района */
    public void forEachDistrict(DistrictPrinter printer) {
        for (District d : districts) {
            printer.print(d);
        }
    }

    /** Вывести районы, удовлетворяющие условию (условие передаётся лямбдой) */
    public void printIf(DistrictCondition condition, DistrictPrinter printer) {
        for (District d : districts) {
            if (condition.check(d)) {
                printer.print(d);
            }
        }
    }

    /** Суммарная площадь районов, удовлетворяющих условию */
    public double totalArea(DistrictCondition condition) {
        double sum = 0;
        for (District d : districts) {
            if (condition.check(d)) {
                sum += d.getArea();
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        return String.format("%s (адм. центр: %s, площадь: %.0f км², глава: %s)",
                name, adminCenter, area, head);
    }
}
