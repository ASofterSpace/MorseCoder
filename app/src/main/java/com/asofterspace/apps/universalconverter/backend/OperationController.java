package com.asofterspace.apps.universalconverter.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.asofterspace.apps.universalconverter.MainActivity;

/**
 * This class keeps track of the currently selected operation
 *
 * @author Moya (a softer space, 2017)
 */
public class OperationController {

    // keep this static, such that the next activity can gain access to the last value
    private static Operation currentOperation;

    public OperationController() {
    }

    public void switchTo(AppCompatActivity from, Operation to) {

        currentOperation = to;

        Intent newIntent = new Intent(from, MainActivity.class);
        from.startActivity(newIntent);
    }

    public Operation getCurrentOperation() {
        return currentOperation;
    }

}
