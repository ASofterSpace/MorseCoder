package com.asofterspace.apps.universaltranslator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asofterspace.apps.universaltranslator.frontend.MainFrontendController;

/**
 * This is the main activity of the UniversalTranslator, which concerns itself with - who would have
 * guessed - translating. =)
 *
 * @author Moya (a softer space, 2017)
 */
public class MainActivity extends AppCompatActivity {

    MainFrontendController frontCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frontCtrl = new MainFrontendController(this);
    }

}
