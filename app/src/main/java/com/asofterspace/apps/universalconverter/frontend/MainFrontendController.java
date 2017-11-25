package com.asofterspace.apps.universalconverter.frontend;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.asofterspace.apps.universalconverter.R;
import com.asofterspace.apps.universalconverter.backend.Operation;
import com.asofterspace.apps.universalconverter.backend.OperationController;
import com.asofterspace.apps.universalconverter.backend.coders.Base64Decoder;
import com.asofterspace.apps.universalconverter.backend.coders.Base64Encoder;
import com.asofterspace.apps.universalconverter.backend.coders.BinaryDecoder;
import com.asofterspace.apps.universalconverter.backend.coders.BinaryEncoder;
import com.asofterspace.apps.universalconverter.backend.coders.MorseDecoder;
import com.asofterspace.apps.universalconverter.backend.coders.MorseEncoder;
import com.asofterspace.apps.universalconverter.backend.coders.RomanNumeralDecoder;
import com.asofterspace.apps.universalconverter.backend.coders.RomanNumeralEncoder;

/**
 * This class controls the frontend of the UniversalTranslator's main activity
 *
 * @author Moya (a softer space, 2017)
 */
public class MainFrontendController implements AdapterView.OnItemSelectedListener {

    private AppCompatActivity parent;

    private OperationController operationCtrl;

    private String[] operations = new String[]{};

    private int selectedOperation = 0;

    private Camera camera;

    private Camera.Parameters cameraparams;


    public MainFrontendController(AppCompatActivity parent) {

        this.parent = parent;

        operationCtrl = new OperationController();

        addOptionsToOperationSelector();

        showFlashButtonIfNecessary();

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

    /**
     * Shows the flash button if we are on the Morse tab (and if there is a camera), and hides it
     * otherwise
     */
    private void showFlashButtonIfNecessary() {

        // get a reference to be the button ready
        Button flashButton = (Button) parent.findViewById(R.id.flashButton);

        // get a reference to the layout ready
        ConstraintLayout layout = (ConstraintLayout) parent.findViewById(R.id.mainActivityLayout);
        ConstraintSet newConstraints = new ConstraintSet();
        newConstraints.clone(layout);

        // if we are on the morse page...
        if (operationCtrl.getCurrentOperation().equals(Operation.MORSE_CODE)) {

            // ... and the device actually supports using a flash light...
            boolean flashPossible = parent.getApplicationContext().getPackageManager()
                    .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

            if (flashPossible) {
                // ... then show the flash button
                // (which is visible by default already, so nothing needs to be done here ^^)

                // and let something happen when we click on the button =)
                flashButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        performMorseFlash();
                    }
                });

                // aaaand we are done
                return;
            }
        }

        // default: hide the flash button
        layout.removeView(flashButton);
        // flashButton.setVisibility(View.INVISIBLE);


        // and connect the output memo to the parent directly
        newConstraints.connect(R.id.outputMemo, ConstraintSet.BOTTOM, R.id.mainActivityLayout,
                ConstraintSet.BOTTOM);
        newConstraints.applyTo(layout);
    }

    private void performMorseFlash() {

        String textToFlash;

        if (selectedOperation == 0) {
            TextView outputMemo = (TextView) parent.findViewById(R.id.outputMemo);
            textToFlash = outputMemo.getText().toString();
        } else {
            TextView inputMemo = (TextView) parent.findViewById(R.id.inputMemo);
            textToFlash = inputMemo.getText().toString();
        }

        textToFlash = textToFlash.trim();

        // no need to carry on if there is no text to be flashed
        if (textToFlash.length() < 1) {
            return;
        }

        // no need to carry on if there is no camera that could flash
        if (!initializeCamera()) {
            return;
        }

        // TODO :: all this is synchronous... maybe instead start another thread for it

        MorseDecoder decoder = new MorseDecoder();
        char[] dots = decoder.getWhatCountsAsDot();
        char[] dashes = decoder.getWhatCountsAsDash();

        // common values are 50 or 60 milliseconds, but we here want to flash such that an unskilled
        // observer can easily make out a short text, so we want to be REALLY SLOW - let's say 400
        // milliseconds (nearly ten times as much as the professionals use) should be fine!
        int unitFlashDuration = 400;
        int accumulateWaitTime = 0;

        for (char c : textToFlash.toCharArray()) {
            for (char dot : dots) {
                if (c == dot) {
                    // wait the accumulated waiting time
                    waitForMilliSeconds(accumulateWaitTime * unitFlashDuration);

                    // flash one duration
                    flashForMilliSeconds(unitFlashDuration);

                    // wait one duration (until the next character)
                    waitForMilliSeconds(unitFlashDuration);

                    // subtract 1 unit from the wait time in case spaces follow (as we just waited)
                    accumulateWaitTime = -1;
                }
            }
            for (char dash : dashes) {
                if (c == dash) {
                    // wait the accumulated waiting time
                    waitForMilliSeconds(accumulateWaitTime * unitFlashDuration);

                    // flash three durations
                    flashForMilliSeconds(3 * unitFlashDuration);

                    // wait one duration (until the next character)
                    waitForMilliSeconds(unitFlashDuration);

                    // subtract 1 unit from the wait time in case spaces follow (as we just waited)
                    accumulateWaitTime = -1;
                }
            }
            if (c == ' ') {
                accumulateWaitTime++;
            }
        }

        camera.release();
    }

    private void waitForMilliSeconds(int howLong) {
        if (howLong > 0) {
            try {
                Thread.sleep(howLong);
            } catch (InterruptedException e) {
                // ignore this for now - TODO :: maybe propagate up, such that flashing is fully stopped
            }
        }
    }


    /**
     * Tries to initialize the camera and returns true if it worked
     * @return True if initializing the camera worked, false otherwise
     */
    private boolean initializeCamera() {

        if (android.os.Build.VERSION.SDK_INT >= 23){

            int cameraPermission = parent.checkSelfPermission(Manifest.permission.CAMERA);

            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {

                parent.requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);

                // false for now, but the user can click on [flash] again after granting permission
                return false;
            }
        }

        try {

            camera = Camera.open();
            cameraparams = camera.getParameters();
            return true;

        } catch (RuntimeException e) {

            return false;
        }
    }

    private void flashForMilliSeconds(int howLong) {

        cameraparams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(cameraparams);
        camera.startPreview();

        try {
            Thread.sleep(howLong);

        } catch (InterruptedException e) {
            // ignore this for now - TODO :: maybe propagate up, such that flashing is fully stopped
        }

        cameraparams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(cameraparams);
        camera.stopPreview();
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
