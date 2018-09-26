package com.asofterspace.apps.universalconverter.frontend;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.asofterspace.apps.universalconverter.R;
import com.asofterspace.toolbox.coders.MorseDecoder;
import com.asofterspace.toolbox.coders.MorseEncoder;
import com.asofterspace.toolbox.coders.RomanNumeralDecoder;
import com.asofterspace.toolbox.coders.RomanNumeralEncoder;

/**
 * This class controls the frontend of the UniversalTranslator
 *
 * @author Moya (a softer space, 2017)
 */
public class FrontendController implements AdapterView.OnItemSelectedListener {

    AppCompatActivity parent;

    private static final String[] operations = {"Text to Morse Code", "Morse Code to Text",
        "Numbers to Roman Numerals", "Roman Numerals to Numbers"};

    private int selectedOperation = 0;

    public FrontendController(AppCompatActivity parent) {

        this.parent = parent;

        addOptionsToOperationSelector();

        addListenerToTranslateButton();

        addListenerToInputMemo();

        addListenerToExchangeButton();
    }

    private void addOptionsToOperationSelector() {

        Spinner selectOperation = (Spinner) parent.findViewById(R.id.selectOperation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(parent,
                android.R.layout.simple_spinner_item, operations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectOperation.setAdapter(adapter);
        selectOperation.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        selectedOperation = position;

        performTranslation();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // ignore that nothing is selected and keep last selection ;)
    }

    private void addListenerToTranslateButton() {

        Button translateButton = (Button) parent.findViewById(R.id.translateButton);
        translateButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                performTranslation();
            }
        });
    }

    private void addListenerToInputMemo() {

        final EditText inputMemo = (EditText) parent.findViewById(R.id.inputMemo);
        inputMemo.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (inputMemo.getText().toString().equals(R.string.StartText)) {
                    inputMemo.setText("");
                }
            }
        });
    }

    private void addListenerToExchangeButton() {

        Button exchangeButton = (Button) parent.findViewById(R.id.exchangeButton);
        exchangeButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                exchangeMemoContents();
            }
        });
    }

    private void performTranslation() {

        EditText inputMemo = (EditText) parent.findViewById(R.id.inputMemo);
        String textToTranslate = inputMemo.getText().toString();

        String translatedText = "";

        switch (selectedOperation) {

            case 0:
                translatedText = MorseEncoder.translateToMorseCode(textToTranslate);
                break;

            case 1:
                translatedText = MorseDecoder.translateFromMorseCode(textToTranslate);
                break;

            case 2:
                translatedText = RomanNumeralEncoder.encodeNumbersIntoRomanNumerals(
                        textToTranslate);
                break;

            case 3:
                translatedText = RomanNumeralDecoder.decodeRomanNumeralsIntoNumbers(
                        textToTranslate);
                break;
        }

        EditText outputMemo = (EditText) parent.findViewById(R.id.outputMemo);
        outputMemo.setText(translatedText);
    }

    private void exchangeMemoContents() {

        EditText inputMemo = (EditText) parent.findViewById(R.id.inputMemo);
        String inputText = inputMemo.getText().toString();

        EditText outputMemo = (EditText) parent.findViewById(R.id.outputMemo);
        String outputText = outputMemo.getText().toString();

        inputMemo.setText(outputText);
        outputMemo.setText(inputText);
    }
}
