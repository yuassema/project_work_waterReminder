package com.example.project_work;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class WaterProgressView extends View {
    private Paint paint;
    private float progress = 0; // 0 to 100

    public WaterProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        float radius = Math.min(centerX, centerY) - 20;

        // Draw background circle
        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Draw progress arc
        paint.setColor(Color.BLUE);
        canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                -90, (progress / 100) * 360, true, paint);
    }
}