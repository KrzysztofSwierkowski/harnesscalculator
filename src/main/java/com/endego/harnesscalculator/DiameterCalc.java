package com.endego.harnesscalculator;

import javafx.scene.control.ListView;
import javafx.scene.control.skin.VirtualFlow;

public class DiameterCalc {
    double diameterAll;
    public double OD;
    private VirtualFlow flow;


    public DiameterCalc(ListView wires) {
        OD = 0;

        wires.getSelectionModel().selectLast();
        int indexOfLastValue = wires.getSelectionModel().getSelectedIndex();
        indexOfLastValue = indexOfLastValue + 1;
        for (int i = 0; i < indexOfLastValue; i++) {
            String element = wires.getItems().get(i).toString();
           OD = OD + Double.parseDouble(element);
        }
        calc(indexOfLastValue);

    }

    public double calc(int indexOfLastValue) {

        double diameterOnceWire = OD;

        if (indexOfLastValue <= 1) {
            diameterAll = OD;
            return diameterAll;
        } else {
            diameterOnceWire = OD * indexOfLastValue;
            //((OD / 4) * Math.PI)

            diameterAll = Math.sqrt(4 * (diameterOnceWire * Math.PI));


            return diameterAll;
        }
    }
}
