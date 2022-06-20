package com.endego.harnesscalculator;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

// helper text field subclass which restricts text input to a given range of natural int numbers
// and exposes the current numeric int value of the edit box as a value property.
class DoubleField extends TextField {
    final private SimpleDoubleProperty value;
    final private double minValue;
    final private double maxValue;


    // expose an integer value property for the text field.
    public Double getValue()                 { return value.getValue(); }
    public void setValue(int newValue)     { value.setValue(newValue); }
    public SimpleDoubleProperty valueProperty() { return value; }

    DoubleField(double minValue, double maxValue, double initialValue) {
        if (minValue > maxValue)
            throw new IllegalArgumentException(
                    "DoubleField min value " + minValue + " greater than max value " + maxValue
            );
        if (!((minValue <= initialValue) && (initialValue <= maxValue)))
            throw new IllegalArgumentException(
                    "DoubleField initialValue " + initialValue + " not between " + minValue + " and " + maxValue
            );

        // initialize the field values.
        this.minValue = minValue;
        this.maxValue = maxValue;
        value = new SimpleDoubleProperty(initialValue);
        setText(initialValue + "");

        final DoubleField doubleField = this;

        // make sure the value property is clamped to the required range
        // and update the field's text to be in sync with the value.
        value.addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == null) {
                    doubleField.setText("");
                } else {
                    if (newValue.doubleValue() < doubleField.minValue) {
                        value.setValue(doubleField.minValue);
                        return;
                    }

                    if (newValue.doubleValue() > doubleField.maxValue) {
                        value.setValue(doubleField.maxValue);
                        return;
                    }

                    if (newValue.doubleValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
                        // no action required, text property is already blank, we don't need to set it to 0.
                    } else {
                        doubleField.setText(newValue.toString());
                    }
                }
            }
        });

        // restrict key input to numerals.
        this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if(doubleField.minValue<0) {
                    if (!"-0123456789".contains(keyEvent.getCharacter())) {
                        keyEvent.consume();
                    }
                }
                else {
                    if (!"0123456789".contains(keyEvent.getCharacter())) {
                        keyEvent.consume();
                    }
                }
            }
        });

        // ensure any entered values lie inside the required range.
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue == null || "".equals(newValue) || (doubleField.minValue<0 && "-".equals(newValue))) {
                    value.setValue(0);
                    return;
                }

                final double doubleValue = Double.parseDouble(newValue);

                if (doubleField.minValue > doubleValue || doubleValue > doubleField.maxValue) {
                    textProperty().setValue(oldValue);
                }

                value.set((int) Double.parseDouble(textProperty().get()));
            }
        });
    }
}