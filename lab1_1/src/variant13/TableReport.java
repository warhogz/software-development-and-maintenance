package variant13;

/** Обычный (именованный) класс, реализующий Report — аналог EnglishGreeting */
public class TableReport implements Report {

    public void printRegion(Region region) {
        System.out.println("Область: " + region.getName());
        System.out.printf("%-22s | %-15s | %8s | %s%n", "Район", "Адм. центр", "Площадь", "Глава");
        System.out.println("-".repeat(70));
        for (District d : region.getDistricts()) {
            printDistrict(d);
        }
    }

    public void printDistrict(District d) {
        System.out.printf("%-22s | %-15s | %8.0f | %s%n",
                d.getName(), d.getAdminCenter(), d.getArea(), d.getHead());
    }
}
