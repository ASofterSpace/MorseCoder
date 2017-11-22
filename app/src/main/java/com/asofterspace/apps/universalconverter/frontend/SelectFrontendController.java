package com.asofterspace.apps.universalconverter.frontend;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.asofterspace.apps.universalconverter.R;
import com.asofterspace.apps.universalconverter.backend.Operation;
import com.asofterspace.apps.universalconverter.backend.OperationController;

/**
 * This class controls the frontend of the UniversalTranslator's select activity
 *
 * @author Moya (a softer space, 2017)
 */
public class SelectFrontendController {

    AppCompatActivity parent;

    OperationController operationCtrl;

    public SelectFrontendController(AppCompatActivity parent) {

        this.parent = parent;

        operationCtrl = new OperationController();

        addListenersToButtons();
    }

    private void addListenersToButtons() {

        Button selectMorseCodeBtn = (Button) parent.findViewById(R.id.selectMorseCodeBtn);
        selectMorseCodeBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operationCtrl.switchTo(parent, Operation.MORSE_CODE);
            }
        });

        Button selectBinaryBtn = (Button) parent.findViewById(R.id.selectBinaryBtn);
        selectBinaryBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operationCtrl.switchTo(parent, Operation.BINARY);
            }
        });

        Button selectBase64Btn = (Button) parent.findViewById(R.id.selectBase64Btn);
        selectBase64Btn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operationCtrl.switchTo(parent, Operation.BASE64);
            }
        });

        /*
        Button selectRot13Btn = (Button) parent.findViewById(R.id.selectRot13Btn);
        selectRot13Btn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operationCtrl.switchTo(parent, Operation.ROT13);
            }
        });
        */

        Button selectRomanNumeralsBtn = (Button) parent.findViewById(R.id.selectRomanNumeralsBtn);
        selectRomanNumeralsBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                operationCtrl.switchTo(parent, Operation.ROMAN_NUMERALS);
            }
        });
    }

}
