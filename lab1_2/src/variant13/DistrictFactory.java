package variant13;

/** Функциональный интерфейс для ссылки на конструктор District::new */
public interface DistrictFactory {
    District create(String name, double area, String adminCenter, String head);
}
