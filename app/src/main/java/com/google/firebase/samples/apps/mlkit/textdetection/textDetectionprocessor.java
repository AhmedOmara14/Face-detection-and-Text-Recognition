package com.google.firebase.samples.apps.mlkit.textdetection;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;



import com.google.firebase.samples.apps.mlkit.FrameMetadata;
import com.google.firebase.samples.apps.mlkit.GraphicOverlay;
import com.google.firebase.samples.apps.mlkit.VisionProcessorBase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.io.IOException;
import java.util.List;

public class textDetectionprocessor extends VisionProcessorBase<FirebaseVisionText>{


    private FirebaseVisionTextDetector detector;
    public textDetectionprocessor() {
        detector= FirebaseVision.getInstance().getVisionTextDetector();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void stop() {
        try {
            detector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Task<FirebaseVisionText> detectInImage
            (FirebaseVisionImage image) {
        return detector.detectInImage(image);
    }

    @Override
    protected void onSuccess(@NonNull FirebaseVisionText results, @NonNull FrameMetadata frameMetadata, @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.clear();
        List<FirebaseVisionText.Block> blocks=results.getBlocks();
        for (int i=0;i<blocks.size();i++){
            List<FirebaseVisionText.Line> lines=blocks.get(i).getLines();
            for (int j=0;j<lines.size();j++){
                List<FirebaseVisionText.Element> elements=lines.get(j).getElements();
                for (int k=0;k<elements.size();k++){
                   GraphicOverlay.Graphic graphic=new textGraphic(graphicOverlay,elements.get(k));
                   graphicOverlay.add(graphic);
                }
            }
        }
    }

    @Override
    protected void onFailure(@NonNull Exception e) {

    }
}
