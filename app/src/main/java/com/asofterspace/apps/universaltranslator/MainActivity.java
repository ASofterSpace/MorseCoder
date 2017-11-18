package com.asofterspace.apps.universaltranslator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.asofterspace.apps.universaltranslator.backend.coders.MorseEncoder;
import com.asofterspace.apps.universaltranslator.frontend.FrontendController;

/**
 * This is the main activity of the UniversalTranslator, which concerns itself with - who would have
 * guessed - translating. =)
 *
 * @author Moya (a softer space, 2017)
 */
public class MainActivity extends AppCompatActivity {

    FrontendController frontCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frontCtrl = new FrontendController(this);
    }

}
