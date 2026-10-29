package demo;

/** Примеры из методических указаний к лабораторной работе 1.2 */
public class LambdaApp {

    interface Operationable {
        int calculate(int x, int y);
    }

    interface Operation {
        int calculate();
    }

    interface Printable {
        void print(String s);
    }

    interface GenericOperationable<T> {
        T calculate(T x, T y);
    }

    interface Expression {
        boolean isEqual(int n);
    }

    interface UserBuilder {
        User create(String name);
    }

    interface BinaryOperation {
        int execute(int x, int y);
    }

    static class ExpressionHelper {
        static boolean isEven(int n) {
            return n % 2 == 0;
        }

        static boolean isPositive(int n) {
            return n > 0;
        }

        boolean isOdd(int n) {
            return n % 2 != 0;
        }
    }

    static class User {
        private final String name;

        User(String n) {
            this.name = n;
        }

        String getName() {
            return name;
        }
    }

    static int x = 10;
    static int y = 20;

    public static void main(String[] args) {
        System.out.println("--- Лямбда-выражение ---");
        Operationable operation = (a, b) -> a + b;
        System.out.println(operation.calculate(10, 20)); // 30

        System.out.println("--- То же через анонимный класс ---");
        Operationable op = new Operationable() {
            public int calculate(int a, int b) {
                return a + b;
            }
        };
        System.out.println(op.calculate(20, 10)); // 30

        System.out.println("--- Несколько лямбд для одного интерфейса ---");
        Operationable operation1 = (int a, int b) -> a + b;
        Operationable operation2 = (int a, int b) -> a - b;
        Operationable operation3 = (int a, int b) -> a * b;
        System.out.println(operation1.calculate(20, 10)); // 30
        System.out.println(operation2.calculate(20, 10)); // 10
        System.out.println(operation3.calculate(20, 10)); // 200

        System.out.println("--- Терминальная лямбда ---");
        Printable printer = s -> System.out.println(s);
        printer.print("Hello Java!");

        System.out.println("--- Переменные уровня класса ---");
        Operation op1 = () -> {
            x = 30;
            return x + y;
        };
        System.out.println(op1.calculate()); // 50
        System.out.println(x);               // 30 - значение x изменилось

        System.out.println("--- Локальные переменные ---");
        int n = 70;
        int m = 30;
        Operation op2 = () -> {
            // n = 100; - так нельзя
            return m + n;
        };
        System.out.println(op2.calculate()); // 100

        System.out.println("--- Блочная лямбда ---");
        Operationable division = (int a, int b) -> {
            if (b == 0)
                return 0;
            else
                return a / b;
        };
        System.out.println(division.calculate(20, 10)); // 2
        System.out.println(division.calculate(20, 0));  // 0

        System.out.println("--- Обобщённый функциональный интерфейс ---");
        GenericOperationable<Integer> g1 = (a, b) -> a + b;
        GenericOperationable<String> g2 = (a, b) -> a + b;
        System.out.println(g1.calculate(20, 10));     // 30
        System.out.println(g2.calculate("20", "10")); // 2010

        System.out.println("--- Лямбда как параметр метода ---");
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Expression func = k -> k % 2 == 0;
        System.out.println(sum(nums, func));       // 20
        System.out.println(sum(nums, k -> k > 5)); // 30

        System.out.println("--- Ссылки на методы ---");
        System.out.println(sum(nums, ExpressionHelper::isEven)); // 20
        Expression expr = ExpressionHelper::isPositive;
        System.out.println(sum(nums, expr));                     // 45
        ExpressionHelper helper = new ExpressionHelper();
        System.out.println(sum(nums, helper::isOdd));            // 25

        System.out.println("--- Ссылка на конструктор ---");
        UserBuilder userBuilder = User::new;
        User user = userBuilder.create("Tom");
        System.out.println(user.getName());

        System.out.println("--- Лямбда как результат метода ---");
        BinaryOperation f = action(1);
        System.out.println(f.execute(6, 5));         // 11
        System.out.println(action(2).execute(8, 2)); // 6
    }

    private static int sum(int[] numbers, Expression func) {
        int result = 0;
        for (int i : numbers) {
            if (func.isEqual(i))
                result += i;
        }
        return result;
    }

    private static BinaryOperation action(int number) {
        switch (number) {
            case 1: return (a, b) -> a + b;
            case 2: return (a, b) -> a - b;
            case 3: return (a, b) -> a * b;
            default: return (a, b) -> 0;
        }
    }
}
