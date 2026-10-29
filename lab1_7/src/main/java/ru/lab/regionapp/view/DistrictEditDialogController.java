package ru.lab.regionapp.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.lab.regionapp.model.District;
import ru.lab.regionapp.model.Region;

/**
 * Окно для изменения информации о районе.
 */
public class DistrictEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField areaField;
    @FXML
    private TextField adminCenterField;
    @FXML
    private TextField headField;
    @FXML
    private ComboBox<Region> regionBox;

    private Stage dialogStage;
    private District district;
    private boolean okClicked = false;

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену для этого окна.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Задаёт список областей для выпадающего списка.
     */
    public void setRegions(ObservableList<Region> regions) {
        regionBox.setItems(regions);
    }

    /**
     * Задаёт район, информацию о котором будем менять.
     */
    public void setDistrict(District district) {
        this.district = district;

        nameField.setText(district.getName());
        areaField.setText(district.getArea() == 0 ? "" : formatArea(district.getArea()));
        adminCenterField.setText(district.getAdminCenter());
        headField.setText(district.getHead());
        regionBox.setValue(district.getRegion());
    }

    /**
     * Returns true, если пользователь кликнул OK, в другом случае false.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            district.setName(nameField.getText().trim());
            district.setArea(parseArea(areaField.getText()));
            district.setAdminCenter(adminCenterField.getText().trim());
            district.setHead(headField.getText().trim());
            district.setRegion(regionBox.getValue());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке «Отмена».
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (isEmpty(nameField)) {
            errorMessage += "Не указано название района!\n";
        }
        if (regionBox.getValue() == null) {
            errorMessage += "Не выбрана область!\n";
        }
        if (isEmpty(areaField)) {
            errorMessage += "Не указана площадь!\n";
        } else {
            try {
                double area = parseArea(areaField.getText());
                if (area <= 0) {
                    errorMessage += "Площадь должна быть больше нуля!\n";
                } else if (regionBox.getValue() != null && area >= regionBox.getValue().getArea()) {
                    errorMessage += "Площадь района должна быть меньше площади области ("
                            + formatArea(regionBox.getValue().getArea()) + " км²)!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Площадь должна быть числом!\n";
            }
        }
        if (isEmpty(adminCenterField)) {
            errorMessage += "Не указан административный центр!\n";
        }
        if (isEmpty(headField)) {
            errorMessage += "Не указан глава района!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверно заполнены поля");
            alert.setHeaderText("Пожалуйста, исправьте поля");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    private static boolean isEmpty(TextField field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    private static double parseArea(String text) {
        return Double.parseDouble(text.trim().replace(',', '.'));
    }

    private static String formatArea(double area) {
        return area == Math.floor(area) ? String.valueOf((long) area) : String.valueOf(area);
    }
}
