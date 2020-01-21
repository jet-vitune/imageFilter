package iamutkarshtiwari.github.io.ananas.editimage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import iamutkarshtiwari.github.io.ananas.editimage.utils.PaintUtil;

public class RotateImageView extends View {
    private Rect srcRect;
    private RectF dstRect;
    private Rect maxRect;

    private Bitmap bitmap;
    private Matrix matrix = new Matrix();

    private float scale;
    private int rotateAngle;

    private RectF wrapRect = new RectF();
    private Paint bottomPaint;
    private RectF originImageRect;

    public RotateImageView(Context context) {
        super(context);
        init(context);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RotateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        srcRect = new Rect();
        dstRect = new RectF();
        maxRect = new Rect();
        bottomPaint = PaintUtil.newRotateBottomImagePaint();
        originImageRect = new RectF();
    }

    public void addBit(Bitmap bit, RectF imageRect) {
        try {
            bitmap = bit;
            srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
            dstRect = imageRect;

            originImageRect.set(0, 0, bit.getWidth(), bit.getHeight());
            this.invalidate();
        }catch (Exception e){

        }
    }

    public void rotateImage(int angle) {
        rotateAngle = angle;
        this.invalidate();
    }

    public void reset() {
        rotateAngle = 0;
        scale = 1;
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (bitmap == null)
            return;
        maxRect.set(0, 0, getWidth(), getHeight());

        calculateWrapBox();
        scale = 1;
        if (wrapRect.width() > getWidth()) {
            scale = getWidth() / wrapRect.width();
        }

        canvas.save();
        canvas.scale(scale, scale, canvas.getWidth() >> 1,
                canvas.getHeight() >> 1);
        canvas.drawRect(wrapRect, bottomPaint);
        canvas.rotate(rotateAngle, canvas.getWidth() >> 1,
                canvas.getHeight() >> 1);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        canvas.restore();
    }

    private void calculateWrapBox() {
        wrapRect.set(dstRect);
        matrix.reset();// 重置矩阵为单位矩阵
        int centerX = getWidth() >> 1;
        int centerY = getHeight() >> 1;
        matrix.postRotate(rotateAngle, centerX, centerY);
        matrix.mapRect(wrapRect);
    }

    public RectF getImageNewRect() {
        Matrix m = new Matrix();
        m.postRotate(this.rotateAngle, originImageRect.centerX(),
                originImageRect.centerY());
        m.mapRect(originImageRect);
        return originImageRect;
    }


    public synchronized float getScale() {
        return scale;
    }

    public synchronized int getRotateAngle() {
        return rotateAngle;
    }
}
