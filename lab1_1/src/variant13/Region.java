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

    /** Отбор районов по условию, которое передаётся объектом DistrictFilter */
    public List<District> findDistricts(DistrictFilter filter) {
        List<District> result = new ArrayList<>();
        for (District d : districts) {
            if (filter.accept(d)) {
                result.add(d);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s (адм. центр: %s, площадь: %.0f км², глава: %s)",
                name, adminCenter, area, head);
    }
}
