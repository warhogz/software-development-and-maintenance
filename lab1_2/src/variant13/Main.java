package variant13;

import java.util.ArrayList;
import java.util.List;

/**
 * Лабораторная работа 1.2. Лямбда-выражения.
 * Вариант 13: Области – Районы.
 */
public class Main {

    static int reportCounter = 0; // переменная уровня класса — её можно менять внутри лямбды

    public static void main(String[] args) {
        // ---------- Ссылка на конструктор ----------
        DistrictFactory factory = District::new;

        Region nn = new Region("Нижегородская область", 76624, "Нижний Новгород", "Петров П.П.");
        nn.addDistrict(factory.create("Арзамасский район", 1923, "Арзамас", "Иванов И.И."));
        nn.addDistrict(factory.create("Богородский район", 1300, "Богородск", "Смирнов А.В."));
        nn.addDistrict(factory.create("Кстовский район", 1203, "Кстово", "Кузнецов Д.С."));
        nn.addDistrict(factory.create("Павловский район", 1077, "Павлово", "Орлова Е.Н."));

        Region vl = new Region("Владимирская область", 29084, "Владимир", "Соколов В.М.");
        vl.addDistrict(factory.create("Ковровский район", 1999, "Ковров", "Морозов К.А."));
        vl.addDistrict(factory.create("Суздальский район", 1263, "Суздаль", "Волкова И.Г."));
        vl.addDistrict(factory.create("Муромский район", 1450, "Муром", "Лебедев Р.О."));

        // ---------- 1. Терминальные лямбды ----------
        DistrictPrinter printer = d -> System.out.println("   " + d);
        DistrictPrinter shortPrinter = d -> System.out.println("   • " + d.getName() + " — " + d.getAdminCenter());

        System.out.println("===== 1. Районы областей (терминальные лямбды) =====");
        System.out.println(nn);
        nn.forEachDistrict(printer);
        System.out.println(vl);
        vl.forEachDistrict(shortPrinter);

        // ---------- 2. Лямбды как параметры методов ----------
        System.out.println();
        System.out.println("===== 2. Лямбда как параметр метода =====");
        System.out.printf("   Суммарная площадь всех районов Нижегородской обл.: %.0f км²%n", nn.totalArea(d -> true));
        System.out.printf("   Площадь районов больше 1250 км²: %.0f км²%n", nn.totalArea(d -> d.getArea() > 1250));
        String letter = "К"; // локальная переменная (фактически final) используется в лямбде
        System.out.println("   Районы, адм. центр которых начинается на «" + letter + "»:");
        nn.printIf(d -> d.getAdminCenter().startsWith(letter), printer);
        vl.printIf(d -> d.getAdminCenter().startsWith(letter), printer);

        // ---------- 3. Ссылки на методы ----------
        System.out.println();
        System.out.println("===== 3. Ссылки на методы =====");
        System.out.println("   Крупные районы (DistrictChecks::isLarge — статический метод):");
        nn.printIf(DistrictChecks::isLarge, printer);
        vl.printIf(DistrictChecks::isLarge, printer);
        DistrictChecks smallCheck = new DistrictChecks(1300);
        System.out.println("   Районы меньше 1300 км² (smallCheck::isSmaller — метод объекта):");
        nn.printIf(smallCheck::isSmaller, printer);
        vl.printIf(smallCheck::isSmaller, printer);

        // ---------- 4. Блочная лямбда ----------
        AreaShare share = (district, region) -> {
            if (region.getArea() == 0) {
                return 0;
            }
            return district.getArea() / region.getArea() * 100;
        };
        System.out.println();
        System.out.println("===== 4. Доля площади района в площади области (блочная лямбда) =====");
        for (District district : nn.getDistricts()) {
            System.out.printf("   %-20s %.2f%%%n", district.getName(), share.calculate(district, nn));
        }

        // ---------- 5. Обобщённый функциональный интерфейс ----------
        Converter<District, String> toShort = d -> d.getName() + " [глава: " + d.getHead() + "]";
        Converter<Double, Double> km2ToHa = area -> area * 100;
        Converter<Region, Integer> districtsCount = r -> r.getDistricts().size();

        System.out.println();
        System.out.println("===== 5. Обобщённый интерфейс Converter<T, R> =====");
        System.out.println("   " + toShort.convert(vl.getDistricts().get(0)));
        System.out.printf("   Площадь «%s»: %.0f га%n", nn.getName(), km2ToHa.convert(nn.getArea()));
        System.out.println("   Районов во Владимирской обл.: " + districtsCount.convert(vl));

        // ---------- 6. Лямбда как результат метода ----------
        double nnArea = nn.getArea();
        double vlArea = vl.getArea();
        System.out.println();
        System.out.println("===== 6. Лямбда как результат метода =====");
        System.out.printf("   Сумма площадей областей: %.0f км²%n", action(1).execute(nnArea, vlArea));
        System.out.printf("   Разница площадей: %.0f км²%n", action(2).execute(nnArea, vlArea));
        AreaOperation ratio = action(3);
        System.out.printf("   Нижегородская обл. больше Владимирской в %.2f раза%n", ratio.execute(nnArea, vlArea));

        // ---------- 7. Отложенное выполнение ----------
        Runnable report = () -> {
            reportCounter++;
            System.out.println("   Отчёт №" + reportCounter + ": " + nn.getName()
                    + ", районов: " + nn.getDistricts().size());
        };
        System.out.println();
        System.out.println("===== 7. Отложенное выполнение =====");
        report.run();
        nn.addDistrict(factory.create("Городецкий район", 2648, "Городец", "Новиков А.А."));
        report.run(); // та же лямбда, но данные уже изменились

        // ---------- 8. Сортировка лямбдой и ссылка System.out::println ----------
        List<District> all = new ArrayList<>(nn.getDistricts());
        all.addAll(vl.getDistricts());
        all.sort((d1, d2) -> Double.compare(d2.getArea(), d1.getArea()));
        System.out.println();
        System.out.println("===== 8. Все районы по убыванию площади =====");
        all.forEach(System.out::println);
    }

    /** Возвращает лямбда-выражение в зависимости от номера операции */
    private static AreaOperation action(int number) {
        switch (number) {
            case 1: return (x, y) -> x + y;
            case 2: return (x, y) -> Math.abs(x - y);
            case 3: return (x, y) -> y == 0 ? 0 : x / y;
            default: return (x, y) -> 0;
        }
    }
}
