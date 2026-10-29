package ru.lab.regionapp;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.lab.regionapp.model.District;
import ru.lab.regionapp.model.Region;
import ru.lab.regionapp.view.DistrictEditDialogController;
import ru.lab.regionapp.view.DistrictOverviewController;
import ru.lab.regionapp.view.RootLayoutController;
import ru.lab.regionapp.model.RegionDataWrapper;

/**
 * Лабораторная работа 1.7. Работа с данными в XML (вариант 13: Области – Районы).
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

        // Устанавливаем иконку приложения.
        setAppIcons(this.primaryStage);

        initRootLayout();

        showDistrictOverview();

        // Если в настройках сохранён путь к последнему файлу, загружаем из него данные.
        File file = getDataFilePath();
        if (file != null && file.exists()) {
            loadDataFromFile(file);
        }
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

            // Даём контроллеру доступ к главному приложению.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

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
     * Открывает диалоговое окно для изменения информации о районе.
     * Если пользователь кликнул OK, изменения сохраняются в предоставленном
     * объекте района и возвращается значение true.
     *
     * @param district объект района, который надо изменить
     * @return true, если пользователь кликнул OK, в противном случае false.
     */
    public boolean showDistrictEditDialog(District district) {
        try {
            // Загружаем fxml-файл и создаём новую сцену для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DistrictEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(district.getName() == null ? "Новый район" : "Редактирование района");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            setAppIcons(dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём район и список областей в контроллер.
            DistrictEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRegions(regionData);
            controller.setDistrict(district);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Возвращает путь к последнему открытому файлу данных. Информация считывается
     * из пользовательских настроек Java (в Windows – из реестра).
     *
     * @return файл или null, если путь не сохранён
     */
    public File getDataFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        return filePath != null ? new File(filePath) : null;
    }

    /**
     * Задаёт путь к текущему загруженному файлу. Путь сохраняется в настройках
     * и отображается в заголовке окна.
     *
     * @param file файл или null, чтобы удалить путь
     */
    public void setDataFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            primaryStage.setTitle("Области и районы - " + file.getName());
        } else {
            prefs.remove("filePath");
            primaryStage.setTitle("Области и районы");
        }
    }

    /**
     * Загружает данные из указанного файла. Текущие данные будут заменены.
     */
    public void loadDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(RegionDataWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            RegionDataWrapper wrapper = (RegionDataWrapper) um.unmarshal(file);

            regionData.clear();
            regionData.addAll(wrapper.getRegions());
            districtData.clear();
            districtData.addAll(wrapper.getDistricts());

            // Сохраняем путь к файлу в настройках.
            setDataFilePath(file);
        } catch (Exception e) {
            showError("Не удалось загрузить данные", "Не удалось загрузить данные из файла:\n" + file.getPath(), e);
        }
    }

    /**
     * Сохраняет текущие данные в указанный файл.
     */
    public void saveDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(RegionDataWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Оборачиваем наши данные.
            RegionDataWrapper wrapper = new RegionDataWrapper();
            wrapper.setRegions(regionData);
            wrapper.setDistricts(districtData);

            // Маршализация и сохранение XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в настройках.
            setDataFilePath(file);
        } catch (Exception e) {
            showError("Не удалось сохранить данные", "Не удалось сохранить данные в файл:\n" + file.getPath(), e);
        }
    }

    private void showError(String header, String text, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Ошибка");
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Устанавливает иконку приложения для окна.
     * Добавляются изображения нескольких размеров: система сама выбирает
     * подходящий для заголовка окна и для панели задач.
     */
    public static void setAppIcons(Stage stage) {
        for (int size : new int[]{16, 32, 64, 128}) {
            stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("images/region_" + size + ".png")));
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
