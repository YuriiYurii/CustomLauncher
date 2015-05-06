package com.example.yuriitsap.customlauncher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;


public class MainActivity3Activity extends ActionBarActivity {

    private Bitmap mMask;
    private Paint mPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_activity3);
        mPaint = new Paint();
        mMask = converToAlphaMask(
                BitmapFactory.decodeResource(getResources(), R.drawable.spot_mask));
        Shader shader = createShader(
                BitmapFactory.decodeResource(getResources(), R.drawable.photo_background));
        mPaint.setShader(shader);
//        ((RelativeLayout) findViewById(R.id.parent))
//                .addView(new CustomView(MainActivity3Activity.this));

    }

    private Bitmap converToAlphaMask(Bitmap bitmap) {
        Bitmap b = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(b);
        canvas.drawBitmap(b, 0f, 0f, null);
        return b;
    }

    private BitmapShader createShader(Bitmap bitmap) {
        return new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    public class CustomView extends View {

        public CustomView(Context context) {
            super(context);
        }

        public CustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(mMask, 1.0f, 1.0f, mPaint);
        }
    }
}
