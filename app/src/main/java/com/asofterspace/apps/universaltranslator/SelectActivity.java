package com.asofterspace.apps.universaltranslator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.asofterspace.apps.universaltranslator.frontend.SelectFrontendController;

/**
 * This is the activity of the UniversalTranslator which allows the user to select what actually is
 * supposed to be happening =)
 *
 * @author Moya (a softer space, 2017)
 */
public class SelectActivity extends AppCompatActivity {

    SelectFrontendController frontCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        frontCtrl = new SelectFrontendController(this);
    }

}
