package com.chienfu.mywatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by isenw on 2023/3/8.
 */

public class ClockView extends View {
    private Paint mHourHandPaint;
    private Paint mMinuteHandPaint;
    private Paint mSecondHandPaint;

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // 刷新View
            invalidate();
            // 延遲1秒後再次執行此Runnable
            mHandler.postDelayed(this, 1000);
        }
    };

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHourHandPaint = new Paint();
        mHourHandPaint.setColor(Color.BLACK);
        mHourHandPaint.setStrokeWidth(5f);
        mHourHandPaint.setAntiAlias(true);
        mHourHandPaint.setStyle(Paint.Style.STROKE);

        mMinuteHandPaint = new Paint();
        mMinuteHandPaint.setColor(Color.BLACK);
        mMinuteHandPaint.setStrokeWidth(3f);
        mMinuteHandPaint.setAntiAlias(true);
        mMinuteHandPaint.setStyle(Paint.Style.STROKE);

        mSecondHandPaint = new Paint();
        mSecondHandPaint.setColor(Color.RED);
        mSecondHandPaint.setStrokeWidth(2f);
        mSecondHandPaint.setAntiAlias(true);
        mSecondHandPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 在View被添加到窗口時開始執行Runnable
        mHandler.post(mRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 在View從窗口移除時停止執行Runnable
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 獲取View的寬度和高度
        int width = getWidth();
        int height = getHeight();

        // 計算時針的長度
        int hourHandLength = Math.min(width, height) / 4;
        // 計算分針和秒針的長度
        int minuteHandLength = hourHandLength * 4 / 3;
        int secondHandLength = hourHandLength * 5 / 3;

        // 獲取當前時間
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        // 計算時針旋轉的角度
        float hourAngle = (hours + minutes / 60f) / 12f * 360f;
        // 計算分針旋轉的角度
        float minuteAngle = minutes / 60f * 360f;
        // 計算秒針旋轉的角度
        float secondAngle = seconds / 60f * 360f;

        // 將畫布坐標系原點移動到View的中心點
        canvas.translate(width / 2f, height / 2f);

        /*
        // 將畫布旋轉到時針指向的角度
        canvas.rotate(hourAngle);
        // 將畫布旋轉到分針指向的角度
        canvas.rotate(minuteAngle - hourAngle);
        // 將畫布旋轉到秒針指向的角度
        canvas.rotate(secondAngle - minuteAngle);

        // 繪製時針
        canvas.drawLine(0, 0, 0, -hourHandLength, mHourHandPaint);
        // 繪製分針
        canvas.drawLine(0, 0, 0, -minuteHandLength, mMinuteHandPaint);
        // 繪製秒針
        canvas.drawLine(0, 0, 0, -secondHandLength, mSecondHandPaint);
        */
        // 繪製圓形背景
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        float circle_x = getWidth() / 2f;
        float circle_y = getHeight() / 2f;
        float startx = 0;
        float starty = 0;
        float stopx_radius = Math.min(circle_x, circle_y);
        canvas.drawCircle(startx, starty, stopx_radius, paint);
//        canvas.translate(startx, starty);//移動中心至(x,y)指定座標

        /*
               對於 srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()) 這行代碼，
               它創建了一個新的 Rect 對象，並將其
               左上角坐標設置為 (0, 0)，
               右下角坐標設置為 (bitmap.getWidth(), bitmap.getHeight())。
               這意味著 srcRect 表示位圖中整個區域。

               座標表示方法：
               (x=0 , y=0)：為手機畫面正中心
               (x= -getWidth() ,  y=-getHeight())：為畫面左上方 (自動抓取)
               (x= getWidth() ,  y=getHeight())：為畫面右下方 (自動抓取)

               這段代碼使用 canvas.drawBitmap(bitmap, srcRect, dstRect, paint) 方法在畫布上繪製一張位圖。其中，
                bitmap 是要繪製的圖檔，
                srcRect 是要繪製到的圖檔區域，
                dstRect 是要繪製到的畫布區域，
                paint 是用於繪製圖檔的畫筆（在這個例子中為 null）。
               */
        // 繪製相片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.watch_scale);
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dstRect = new Rect(-getWidth()/2, -getHeight()/2, getWidth()/2, getHeight()/2);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);

        /*
        使用 canvas.rotate(angle) 方法將畫布旋轉到指針指向的角度
        然後使用 canvas.drawLine(x1, y1, x2, y2, paint) 方法在畫布上繪製一條直線作為指針。
        其中，hourAngle 是時針指向的角度，hourHandLength 是時針的長度，mHourHandPaint 是用於繪製時針的畫筆。
        */
        //將畫布旋轉到時針指向的角度並繪製時針
        canvas.save();
        canvas.rotate(hourAngle);
        canvas.drawLine(startx, starty, 0, -hourHandLength, mHourHandPaint);
        canvas.restore();

        // 將畫布旋轉到分針指向的角度並繪製分針
        canvas.save();
        canvas.rotate(minuteAngle);
        canvas.drawLine(startx, starty, 0, -minuteHandLength, mMinuteHandPaint);
        canvas.restore();

        // 將畫布旋轉到秒針指向的角度並繪製秒針
        canvas.save();
        canvas.rotate(secondAngle);
        canvas.drawLine(startx, starty, 0, -secondHandLength, mSecondHandPaint);
        canvas.restore();
    }
}
