package com.google.firebase.samples.apps.mlkit.textdetection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.firebase.samples.apps.mlkit.GraphicOverlay;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

public class textGraphic extends GraphicOverlay.Graphic {

    private static final int TEXT_COLOR = Color.WHITE;
    private static final float TEXT_SIZE = 54.0f;
    private static final float STROKE_WIDTH = 4.0f;

    private final Paint rectPaint;
    private final Paint textPaint;
    private final FirebaseVisionText.Element text;
    public textGraphic(GraphicOverlay overlay , FirebaseVisionText.Element text) {
        super(overlay);

        this.text=text;

        rectPaint=new Paint();
        rectPaint.setColor(TEXT_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(STROKE_WIDTH);

        textPaint=new Paint();
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(TEXT_SIZE);
        postInvalidate();


    }

    @Override
    public void draw(Canvas canvas) {
        RectF rectF=new RectF(text.getBoundingBox());

        rectF.left=translateX(rectF.left);
        rectF.right=translateX(rectF.right);
        rectF.top=translateY(rectF.top);
        rectF.bottom=translateY(rectF.bottom);

        canvas.drawText(text.getText(), rectF.left, rectF.bottom, textPaint);
    }
}
