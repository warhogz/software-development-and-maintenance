package ru.lab.regionapp.view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.lab.regionapp.MainApp;
import ru.lab.regionapp.model.District;
import ru.lab.regionapp.model.Region;

/**
 * Контроллер для DistrictOverview.fxml
 */
public class DistrictOverviewController {

    @FXML
    private TableView<District> districtTable;
    @FXML
    private TableColumn<District, String> nameColumn;
    @FXML
    private TableColumn<District, String> regionColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label areaLabel;
    @FXML
    private Label adminCenterLabel;
    @FXML
    private Label headLabel;
    @FXML
    private Label regionLabel;

    // Ссылка на главное приложение.
    private MainApp mainApp;

    /**
     * Конструктор вызывается раньше метода initialize().
     */
    public DistrictOverviewController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы районов с двумя столбцами.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        // Столбец «Область» обновляется, если у района сменили область.
        regionColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> {
            Region region = cellData.getValue().getRegion();
            return region == null ? "" : region.getName();
        }, cellData.getValue().regionProperty()));

        // Очистка подробной информации о районе.
        showDistrictDetails(null);

        // Слушаем изменения выбора и при изменении отображаем подробную информацию о районе.
        districtTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDistrictDetails(newValue));
    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка.
        districtTable.setItems(mainApp.getDistrictData());
    }

    /**
     * Заполняет текстовые поля, отображая подробности о районе.
     * Если указанный район = null, то все текстовые поля очищаются.
     */
    private void showDistrictDetails(District district) {
        if (district != null) {
            nameLabel.setText(district.getName());
            areaLabel.setText(String.format("%.0f км²", district.getArea()));
            adminCenterLabel.setText(district.getAdminCenter());
            headLabel.setText(district.getHead());
            regionLabel.setText(district.getRegion() == null ? "" : district.getRegion().getName());
        } else {
            nameLabel.setText("");
            areaLabel.setText("");
            adminCenterLabel.setText("");
            headLabel.setText("");
            regionLabel.setText("");
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке «Удалить».
     */
    @FXML
    private void handleDeleteDistrict() {
        int selectedIndex = districtTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            districtTable.getItems().remove(selectedIndex);
        } else {
            showNoSelectionAlert();
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке «Новый...».
     * Открывает диалоговое окно с пустыми полями для нового района.
     */
    @FXML
    private void handleNewDistrict() {
        District tempDistrict = new District();
        boolean okClicked = mainApp.showDistrictEditDialog(tempDistrict);
        if (okClicked) {
            mainApp.getDistrictData().add(tempDistrict);
            districtTable.getSelectionModel().select(tempDistrict);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке «Изменить...».
     * Открывает диалоговое окно для изменения выбранного района.
     */
    @FXML
    private void handleEditDistrict() {
        District selectedDistrict = districtTable.getSelectionModel().getSelectedItem();
        if (selectedDistrict != null) {
            boolean okClicked = mainApp.showDistrictEditDialog(selectedDistrict);
            if (okClicked) {
                showDistrictDetails(selectedDistrict);
            }
        } else {
            showNoSelectionAlert();
        }
    }

    /**
     * Предупреждение, если в таблице ничего не выбрано.
     */
    private void showNoSelectionAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Нет выбора");
        alert.setHeaderText("Район не выбран");
        alert.setContentText("Пожалуйста, выберите район в таблице.");
        alert.showAndWait();
    }
}
