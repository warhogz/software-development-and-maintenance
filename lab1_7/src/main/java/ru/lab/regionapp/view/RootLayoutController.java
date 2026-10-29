package ru.lab.regionapp.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import ru.lab.regionapp.MainApp;

/**
 * Контроллер корневого макета. Предоставляет базовые меню навигации приложения.
 */
public class RootLayoutController {

    // Ссылка на главное приложение
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Создаёт пустую книгу данных.
     */
    @FXML
    private void handleNew() {
        mainApp.getDistrictData().clear();
        mainApp.getRegionData().clear();
        mainApp.setDataFilePath(null);
    }

    /**
     * Открывает FileChooser, чтобы пользователь смог выбрать XML-файл для загрузки.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл данных");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML файлы (*.xml)", "*.xml"));

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            mainApp.loadDataFromFile(file);
        }
    }

    /**
     * Сохраняет файл в открытый в настоящее время файл. Если файл не открыт,
     * то отображается диалог «Сохранить как».
     */
    @FXML
    private void handleSave() {
        File file = mainApp.getDataFilePath();
        if (file != null) {
            mainApp.saveDataToFile(file);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Открывает FileChooser, чтобы пользователь смог выбрать файл для сохранения.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить файл данных");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML файлы (*.xml)", "*.xml"));

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if (file != null) {
            // Проверяем, что файл имеет правильное расширение.
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveDataToFile(file);
        }
    }

    /**
     * Открывает диалог «О программе».
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Области и районы");
        alert.setHeaderText("О программе");
        alert.setContentText("Автор: Хасаев Умахан, группа ИСТ-331\nДанные хранятся в XML-файле.");
        alert.showAndWait();
    }

    /**
     * Закрывает приложение.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
