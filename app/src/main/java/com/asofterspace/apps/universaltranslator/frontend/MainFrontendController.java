package com.asofterspace.apps.universaltranslator.frontend;

import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.asofterspace.apps.universaltranslator.R;
import com.asofterspace.apps.universaltranslator.backend.OperationController;
import com.asofterspace.apps.universaltranslator.backend.coders.Base64Decoder;
import com.asofterspace.apps.universaltranslator.backend.coders.Base64Encoder;
import com.asofterspace.apps.universaltranslator.backend.coders.BinaryDecoder;
import com.asofterspace.apps.universaltranslator.backend.coders.BinaryEncoder;
import com.asofterspace.apps.universaltranslator.backend.coders.MorseDecoder;
import com.asofterspace.apps.universaltranslator.backend.coders.MorseEncoder;
import com.asofterspace.apps.universaltranslator.backend.coders.RomanNumeralDecoder;
import com.asofterspace.apps.universaltranslator.backend.coders.RomanNumeralEncoder;

/**
 * This class controls the frontend of the UniversalTranslator's main activity
 *
 * @author Moya (a softer space, 2017)
 */
public class MainFrontendController implements AdapterView.OnItemSelectedListener {

    AppCompatActivity parent;

    OperationController operationCtrl;

    private String[] operations = new String[]{};

    private int selectedOperation = 0;


    public MainFrontendController(AppCompatActivity parent) {

        this.parent = parent;

        operationCtrl = new OperationController();

        addOptionsToOperationSelector();

        addListenerToTranslateButton();

        addListenerToInputMemo();

        addListenerToExchangeButton();

        addLinkToCreatedByTxt();
    }

    private void addOptionsToOperationSelector() {

        switch (operationCtrl.getCurrentOperation()) {

            case MORSE_CODE:
                operations = new String[]{"Text to Morse Code", "Morse Code to Text"};
                break;

            case BINARY:
                operations = new String[]{"Decimal Integer to Binary", "Binary to Decimal Integer"};
                break;

            case BASE64:
                operations = new String[]{"Text to Base64", "Base64 to Text"};
                break;

            case ROT13:
                operations = new String[]{"ROT1", "ROT2", "ROT3", "ROT4", "ROT5", "ROT6", "ROT7",
                        "ROT8", "ROT9", "ROT10", "ROT11", "ROT12", "ROT13", "ROT14", "ROT15",
                        "ROT16", "ROT17", "ROT18", "ROT19", "ROT20", "ROT21", "ROT22", "ROT23",
                        "ROT24", "ROT25", "ROT26"};
                break;

            case ROMAN_NUMERALS:
                operations = new String[]{"Integer to Roman Numerals", "Roman Numerals to Integer"};
                break;
        }

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

        switch (operationCtrl.getCurrentOperation()) {

            case MORSE_CODE:
                if (selectedOperation == 0) {
                    translatedText = (new MorseEncoder()).translateToMorseCode(textToTranslate);
                } else {
                    translatedText = (new MorseDecoder()).translateFromMorseCode(textToTranslate);
                }
                break;

            case BINARY:
                if (selectedOperation == 0) {
                    translatedText = (new BinaryEncoder()).encodeIntoBinaryString(textToTranslate);
                } else {
                    translatedText = (new BinaryDecoder()).decodeFromBinaryIntoStr(textToTranslate);
                }
                break;

            case BASE64:
                if (selectedOperation == 0) {
                    translatedText = (new Base64Encoder()).encodeIntoBase64(textToTranslate);
                } else {
                    translatedText = (new Base64Decoder()).decodeFromBase64(textToTranslate);
                }
                break;

            case ROMAN_NUMERALS:
                if (selectedOperation == 0) {
                    translatedText = (new RomanNumeralEncoder()).encodeNumbersIntoRomanNumerals(
                            textToTranslate);
                } else {
                    translatedText = (new RomanNumeralDecoder()).decodeRomanNumeralsIntoNumbers(
                            textToTranslate);
                }
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

    private void addLinkToCreatedByTxt() {
        /*
        TextView createdByTxt = (TextView) parent.findViewById(R.id.createdByTxt);
        createdByTxt.setClickable(true);
        // createdByTxt.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "Created BY <a href='http://www.asofterspace.com'>A Softer Space</a>, 2017";
        createdByTxt.setText(Html.fromHtml(text));
        */
    }
}
