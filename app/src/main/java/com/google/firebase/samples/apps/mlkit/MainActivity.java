package com.google.firebase.samples.apps.mlkit;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;


import com.google.firebase.samples.apps.mlkit.textdetection.textDetectionprocessor;
import com.google.samples.R;
import com.google.firebase.samples.apps.mlkit.facedetection.faceDetectionprocessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback,
        AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener {

    private CameraSource cameraSource = null;
    private CameraSourcePreview cameraSourcePreview;
    private GraphicOverlay graphicOverlay;
    private ToggleButton toggleButton;
    private final String FACE_DETECTION = "FACE DETECTION";
    private static final String TEXt_DETECTION = "TEXT DETECTION";

    private String select = FACE_DETECTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraSourcePreview = (CameraSourcePreview) findViewById(R.id.firePreview);

        graphicOverlay = (GraphicOverlay) findViewById(R.id.fireFaceOverlay);


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> options = new ArrayList<>();
        options.add(FACE_DETECTION);
        options.add(TEXt_DETECTION);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_style, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

        ToggleButton facingSwitch = (ToggleButton) findViewById(R.id.facingswitch);
        facingSwitch.setOnCheckedChangeListener(this);

        createCameraSource(select);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        select = parent.getItemAtPosition(position).toString();
        cameraSourcePreview.stop();
        createCameraSource(select);
        startCameraSource();
    }

    private void createCameraSource(String model) {

        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
        }
        switch (model) {
            case TEXt_DETECTION:
                cameraSource.setMachineLearningFrameProcessor(new textDetectionprocessor());
                break;
            case FACE_DETECTION:
                cameraSource.setMachineLearningFrameProcessor(new faceDetectionprocessor());
                break;
            default:
        }

    }

    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                cameraSourcePreview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                cameraSource.release();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSourcePreview.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraSourcePreview.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
