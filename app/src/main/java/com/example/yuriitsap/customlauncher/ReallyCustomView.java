package com.example.yuriitsap.customlauncher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yuriitsap on 06.05.15.
 */
public class ReallyCustomView extends View {

    private Bitmap mMask;
    private Paint mPaint;
    private Bitmap mDefaultBitmap;


    public ReallyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMask = converToAlphaMask(
                BitmapFactory.decodeResource(getResources(), R.drawable.spot_mask));
        mPaint = new Paint();
        mDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_xx);
        mPaint.setShader(createShader(mDefaultBitmap));
    }

    private Bitmap converToAlphaMask(Bitmap bitmap) {
        Bitmap b = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        canvas.drawBitmap(b, 0.5f, 0.5f, new Paint());
        return b;
    }

    private BitmapShader createShader(Bitmap bitmap) {
        return new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(200, 200);
        canvas.drawBitmap(mMask,
                0, 0, mPaint);
    }
}
