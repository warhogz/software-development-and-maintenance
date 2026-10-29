package variant13;

/** Методы-проверки для ссылок на методы */
public class DistrictChecks {
    private final double limit;

    public DistrictChecks(double limit) {
        this.limit = limit;
    }

    /** Статический метод: крупный район (от 1500 км²) */
    public static boolean isLarge(District d) {
        return d.getArea() >= 1500;
    }

    /** Нестатический метод: район меньше заданного лимита */
    public boolean isSmaller(District d) {
        return d.getArea() < limit;
    }
}
