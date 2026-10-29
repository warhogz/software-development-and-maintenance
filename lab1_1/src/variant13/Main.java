package variant13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Лабораторная работа 1.1. Анонимные классы.
 * Вариант 13: Области – Районы.
 */
public class Main {

    public static void main(String[] args) {
        Region nn = new Region("Нижегородская область", 76624, "Нижний Новгород", "Петров П.П.");
        nn.addDistrict(new District("Арзамасский район", 1923, "Арзамас", "Иванов И.И."));
        nn.addDistrict(new District("Богородский район", 1300, "Богородск", "Смирнов А.В."));
        nn.addDistrict(new District("Кстовский район", 1203, "Кстово", "Кузнецов Д.С."));
        nn.addDistrict(new District("Павловский район", 1077, "Павлово", "Орлова Е.Н."));

        Region vl = new Region("Владимирская область", 29084, "Владимир", "Соколов В.М.");
        vl.addDistrict(new District("Ковровский район", 1999, "Ковров", "Морозов К.А."));
        vl.addDistrict(new District("Суздальский район", 1263, "Суздаль", "Волкова И.Г."));
        vl.addDistrict(new District("Муромский район", 1450, "Муром", "Лебедев Р.О."));

        // ---------- 1. Именованный класс и анонимные классы, реализующие один интерфейс ----------
        Report tableReport = new TableReport();

        // анонимный класс: краткий отчёт
        Report shortReport = new Report() {
            public void printRegion(Region region) {
                System.out.println(region.getName() + " — районов: " + region.getDistricts().size());
                for (District d : region.getDistricts()) {
                    printDistrict(d);
                }
            }

            public void printDistrict(District district) {
                System.out.println("   • " + district.getName());
            }
        };

        // анонимный класс: подробный отчёт (своё поле + дополнительный метод)
        Report fullReport = new Report() {
            int counter; // поле анонимного класса

            public void printRegion(Region region) {
                counter = 0;
                System.out.println(region);
                for (District d : region.getDistricts()) {
                    printDistrict(d);
                }
                double sum = totalArea(region);
                System.out.printf("   Суммарная площадь районов: %.0f км² (%.1f%% площади области)%n",
                        sum, sum / region.getArea() * 100);
            }

            public void printDistrict(District district) {
                counter++;
                System.out.println("   " + counter + ". " + district);
            }

            // дополнительный метод, которого нет в интерфейсе
            double totalArea(Region region) {
                double s = 0;
                for (District d : region.getDistricts()) {
                    s += d.getArea();
                }
                return s;
            }
        };

        System.out.println("===== Табличный отчёт (именованный класс) =====");
        tableReport.printRegion(nn);
        System.out.println();
        System.out.println("===== Краткий отчёт (анонимный класс) =====");
        shortReport.printRegion(vl);
        System.out.println();
        System.out.println("===== Подробный отчёт (анонимный класс) =====");
        fullReport.printRegion(nn);
        fullReport.printRegion(vl);

        // ---------- 2. Анонимные классы-фильтры (захват локальной переменной) ----------
        double minArea = 1400; // фактически final — доступна внутри анонимного класса
        DistrictFilter bigDistricts = new DistrictFilter() {
            public boolean accept(District district) {
                return district.getArea() >= minArea;
            }
        };

        System.out.println();
        System.out.println("===== Районы площадью от " + minArea + " км² =====");
        for (Region r : new Region[]{nn, vl}) {
            for (District d : r.findDistricts(bigDistricts)) {
                System.out.println("   " + d);
            }
        }

        // анонимный класс передаётся прямо в вызов метода
        System.out.println();
        System.out.println("===== Районы Владимирской обл., адм. центр которых начинается на «М» =====");
        List<District> mDistricts = vl.findDistricts(new DistrictFilter() {
            public boolean accept(District district) {
                return district.getAdminCenter().startsWith("М");
            }
        });
        for (District d : mDistricts) {
            System.out.println("   " + d);
        }

        // ---------- 3. Анонимный класс, реализующий стандартный интерфейс Comparator ----------
        List<District> all = new ArrayList<>(nn.getDistricts());
        all.addAll(vl.getDistricts());
        Collections.sort(all, new Comparator<District>() {
            public int compare(District a, District b) {
                return Double.compare(b.getArea(), a.getArea()); // по убыванию площади
            }
        });

        System.out.println();
        System.out.println("===== Все районы по убыванию площади =====");
        for (District d : all) {
            System.out.println("   " + d);
        }

        // ---------- 4. Анонимный класс, расширяющий класс (с аргументами конструктора) ----------
        District special = new District("Городецкий район", 2648, "Городец", "Новиков А.А.") {
            String note; // поле

            { // инициализатор экземпляра (конструктор в анонимном классе объявить нельзя)
                note = "исторический район";
            }

            @Override
            public String toString() {
                return super.toString() + " — " + note;
            }
        };
        nn.addDistrict(special);

        System.out.println();
        System.out.println("===== Район — анонимный подкласс District =====");
        System.out.println("   " + special);
        System.out.println("   Районов в Нижегородской области теперь: " + nn.getDistricts().size());
    }
}
