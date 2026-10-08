package ru.lab.regionapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.lab.regionapp.MainApp;
import ru.lab.regionapp.model.District;

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
        regionColumn.setCellValueFactory(cellData -> cellData.getValue().getRegion().nameProperty());

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
            regionLabel.setText(district.getRegion().getName());
        } else {
            nameLabel.setText("");
            areaLabel.setText("");
            adminCenterLabel.setText("");
            headLabel.setText("");
            regionLabel.setText("");
        }
    }
}
