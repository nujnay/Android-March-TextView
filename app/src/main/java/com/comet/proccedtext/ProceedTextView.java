package com.comet.proccedtext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Created by nujjj on 2018/3/26.
 */

public class ProceedTextView extends SurfaceView {
    private Paint paint;
    private Rect textBound;
    private String text = "abcdefg";
    private Path path;
    private float width;
    private float height;
    private float movePercent = 0f;


    public ProceedTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setTextSize(sp2px(50));
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        path = new Path();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = (getPaddingLeft() + paint.measureText(text) +
                getPaddingRight());
        height = (getPaddingTop() + textBound.height() + getPaddingBottom());
        setMeasuredDimension((int) width, (int) height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int layer = canvas.saveLayer(0.0F, 0.0F, width, height, null, Canvas.ALL_SAVE_FLAG);

        path.reset();
        path.moveTo(0, 0);
        path.lineTo(width * movePercent, 0);
        path.lineTo(width * movePercent, height);
        path.lineTo(0, height);
        canvas.clipPath(path, Region.Op.INTERSECT);

        canvas.save();
        paint.setColor(Color.RED);
        canvas.drawText(text, 0, textBound.height() - textBound.bottom, paint);

        canvas.restoreToCount(layer);
        path.reset();
        path.moveTo(width * movePercent, 0);
        path.lineTo(width, 0);
        path.lineTo(width, height);
        path.lineTo(width * movePercent, height);
        canvas.clipPath(path, Region.Op.INTERSECT);

        canvas.save();
        paint.setColor(Color.BLACK);
        canvas.drawText(text, 0, textBound.height() - textBound.bottom, paint);
    }


    public static int sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public float getMovePercent() {
        return movePercent;
    }

    public void setMovePercent(float movePercent) {
        this.movePercent = movePercent;
        Log.d("movePercent", movePercent + "");
        invalidate();
    }
}
