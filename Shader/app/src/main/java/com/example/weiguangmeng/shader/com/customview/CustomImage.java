package com.example.weiguangmeng.shader.com.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.weiguangmeng.shader.R;

/**
 * Created by weiguangmeng on 16/1/11.
 */
public class CustomImage extends ImageView {
    private float radius;
    private Paint paint = null;

    private float x;
    private float y;

    private boolean shouldDrawOpen = false;

    public CustomImage(Context context) {
        this(context, null);
    }

    public CustomImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CustomImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        radius = getResources().getDimensionPixelOffset(R.dimen.default_radius);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImage, defStyleAttr, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.CustomImage_radius:
                    radius = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    break;
            }
        }

        a.recycle();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            shouldDrawOpen = true;
        } else {
            shouldDrawOpen = false;
        }

        x = event.getX();
        y = event.getY();

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);

        if (null == paint) {
            Bitmap original = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas originalCanvas = new Canvas(original);
            super.onDraw(originalCanvas);

            Shader shader = new BitmapShader(original, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            paint = new Paint();
            paint.setShader(shader);
        }

        canvas.drawColor(Color.GRAY);

        /*
        int[] colors = new int[] {Color.RED, Color.GREEN, Color.BLACK};
        float[] positions = new float[]{0.4f, 0.6f, 1};
        Shader sweep = new SweepGradient(x, y, colors, positions);
        paint.setShader(sweep);
        */

        if (shouldDrawOpen) {
            canvas.drawCircle(x, y - radius, radius, paint);
        }
    }
}
