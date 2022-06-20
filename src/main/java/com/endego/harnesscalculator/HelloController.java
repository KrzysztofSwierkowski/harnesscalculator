package com.endego.harnesscalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HelloController {
    int i = 0;



    @FXML
    private VBox labelBox;

    @FXML
    private TextField manywires;

    @FXML
    private ListView listdim;

    @FXML
    private Label suma;

    @FXML
    private void DeleteWireFromListView(ActionEvent event) {
        int index = listdim.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            listdim.getItems().remove(index);
        }
    }

    @FXML
    private void DeleteAllWireFromListView(ActionEvent event) {
        int index = listdim.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            listdim.getItems().clear();
        }
    }

    @FXML
    private void AddManyWires(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.NONE);


        try {
        DoubleField doubleField = new DoubleField(0,100,Double.parseDouble(manywires.getText()));
        listdim.getItems().add(manywires.getText());
        listdim.scrollTo(listdim.getItems().size() - 1);
        listdim.layout();
        listdim.edit(listdim.getItems().size() - 1);
        manywires.requestFocus();
        manywires.setText("");
        }
catch (NumberFormatException e)
        {
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Zły Typ Danych");

            // show the dialog
            a.show();
        }
    }

    private void NewTextField() {

        TextField newField = new TextField();
        newField.setMaxSize(72, 25);
        newField.setId("textfield" + i);

        Label newLabel = new Label("Przekrój: ");
        newField.setAlignment(Pos.TOP_CENTER);
        HBox newHbox = new HBox();
        newHbox.getChildren().addAll(newLabel, newField);
        labelBox.getChildren().addAll(newHbox);
        i++;

    }
    @FXML
    private void Calculate(){
        DiameterCalc diameterCalc = new DiameterCalc(listdim);

        double sum = diameterCalc.diameterAll;
        double finalSum = BigDecimal.valueOf(sum)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        double bendRadius = finalSum + (finalSum * 4);
        suma.setText("O.D. = " + finalSum+" mm^2" + "  Bendradius = " + bendRadius);



    }

}