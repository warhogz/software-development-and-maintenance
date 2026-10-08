package ru.lab.regionapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.lab.regionapp.model.District;
import ru.lab.regionapp.model.Region;
import ru.lab.regionapp.view.DistrictOverviewController;

/**
 * Лабораторная работа 1.4. Компоненты MVC (вариант 13: Области – Районы).
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * Данные в виде наблюдаемых списков областей и районов.
     */
    private final ObservableList<Region> regionData = FXCollections.observableArrayList();
    private final ObservableList<District> districtData = FXCollections.observableArrayList();

    /**
     * Конструктор: заполняем списки тестовыми данными.
     */
    public MainApp() {
        Region nn = new Region("Нижегородская область", 76624, "Нижний Новгород", "Петров П.П.");
        Region vl = new Region("Владимирская область", 29084, "Владимир", "Соколов В.М.");
        Region rs = new Region("Ростовская область", 100967, "Ростов-на-Дону", "Захаров О.Л.");
        regionData.addAll(nn, vl, rs);

        districtData.add(new District("Арзамасский район", 1923, "Арзамас", "Иванов И.И.", nn));
        districtData.add(new District("Богородский район", 1300, "Богородск", "Смирнов А.В.", nn));
        districtData.add(new District("Кстовский район", 1203, "Кстово", "Кузнецов Д.С.", nn));
        districtData.add(new District("Павловский район", 1077, "Павлово", "Орлова Е.Н.", nn));
        districtData.add(new District("Ковровский район", 1999, "Ковров", "Морозов К.А.", vl));
        districtData.add(new District("Суздальский район", 1263, "Суздаль", "Волкова И.Г.", vl));
        districtData.add(new District("Муромский район", 1450, "Муром", "Лебедев Р.О.", vl));
        districtData.add(new District("Аксайский район", 1210, "Аксай", "Белов С.Н.", rs));
        districtData.add(new District("Азовский район", 2864, "Азов", "Ершова Т.В.", rs));
    }

    /**
     * Возвращает данные в виде наблюдаемого списка областей.
     */
    public ObservableList<Region> getRegionData() {
        return regionData;
    }

    /**
     * Возвращает данные в виде наблюдаемого списка районов.
     */
    public ObservableList<District> getDistrictData() {
        return districtData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Области и районы");

        initRootLayout();

        showDistrictOverview();
    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения о районах.
     */
    public void showDistrictOverview() {
        try {
            // Загружаем сведения о районах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DistrictOverview.fxml"));
            AnchorPane districtOverview = (AnchorPane) loader.load();

            // Помещаем сведения о районах в центр корневого макета.
            rootLayout.setCenter(districtOverview);

            // Даём контроллеру доступ к главному приложению.
            DistrictOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает главную сцену.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
