package variant13;

/** Район */
public class District {
    private final String name;        // Название
    private final double area;        // Площадь, км²
    private final String adminCenter; // Административный центр
    private final String head;        // Глава

    public District(String name, double area, String adminCenter, String head) {
        this.name = name;
        this.area = area;
        this.adminCenter = adminCenter;
        this.head = head;
    }

    public String getName() { return name; }
    public double getArea() { return area; }
    public String getAdminCenter() { return adminCenter; }
    public String getHead() { return head; }

    @Override
    public String toString() {
        return String.format("%s (адм. центр: %s, площадь: %.0f км², глава: %s)",
                name, adminCenter, area, head);
    }
}
