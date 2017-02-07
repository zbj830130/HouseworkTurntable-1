package com.bignerdranch.android.houseworkturntable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Util.HouseworkTurntable.ColorUtil;
import business.HouseworkTurntable.ChartBus;
import business.HouseworkTurntable.HouseworkLab;
import models.HouseworkTurntable.HouseworkItem;

/**
 * Created by zhangbojin on 6/02/17.
 */

public class HouseworkPanBusiness extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;

    private Thread t;

    private boolean isRunning;

    private String[] mStrs = null;

    private int[] mImgs = null;

    private int[] mColors;// = new int[]{0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300};


    private int mItemCount = 0;


    private Bitmap[] mImgsBitmap = null;


    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);


    private RectF mRange = new RectF();


    private int mRadius;


    private Paint mArcPaint;


    private Paint mTextPaint;


    private float mTextSize = TypedValue.applyDimension
            (TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());


    private double mSpeed;


    private volatile int mStartAngle = 0;


    private boolean isShouldEnd;


    private int mCenter;

    /**
     * this is the mix padding or use the paddingleft
     */
    private int mPadding;

    private List<HouseworkItem> selectedItems = null;


    public HouseworkPanBusiness(Context context) {
        this(context, null);
    }

    public HouseworkPanBusiness(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPan(context);

        mHolder = getHolder();

        mHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);

        setKeepScreenOn(true);

    }

    private void initPan(Context context) {
        Resources res = getResources();

        HouseworkLab lab = HouseworkLab.get(context);
        lab.Refresh(context);
        selectedItems = new ArrayList<>();

        List<HouseworkItem> items = lab.getHouseworkItems();
        for (HouseworkItem hwItem : items
                ) {
            if (hwItem.isSelected() == true) {
                selectedItems.add(hwItem);
                new ChartBus(context).updateCountNum(hwItem.getId());
            }
        }

        mItemCount = selectedItems.size();
        mImgs = new int[mItemCount];
        mStrs = new String[mItemCount];
        mColors = new int[mItemCount];


        int i = 0;
        for (HouseworkItem hwItem : selectedItems
                ) {
            int expNum = new Random().nextInt(28) + 1;

            mImgs[i] = res.getIdentifier("exp_" + expNum,
                    "drawable", "com.bignerdranch.android.houseworkturntable");

            mStrs[i] = hwItem.getName();
            mColors[i] = ColorUtil.getColor();
            i++;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());

        mRadius = width - getPaddingLeft() * 2;

        mPadding = getPaddingLeft();

        mRadius = width - mPadding * 2;

        mCenter = width / 2;

        setMeasuredDimension(width, width);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setTextSize(mTextSize);

        mRange = new RectF(mPadding, mPadding, mPadding + mRadius, mPadding + mRadius);

        mImgsBitmap = new Bitmap[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(), mImgs[i]);
        }

        isRunning = true;

        t = new Thread(this);
        t.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            long start = System.currentTimeMillis();

            draw();

            long end = System.currentTimeMillis();

            if ((end - start) < 50) {
                try {
                    Thread.sleep(50 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void draw() {

        try {
            mCanvas = mHolder.lockCanvas();

            if (mCanvas != null) {
                drawBg();

                float tmpAngle = mStartAngle;
                float sweepAngle = 360.00f / mItemCount;

                for (int i = 0; i < mItemCount; i++) {
                    mArcPaint.setColor(mColors[i]);
                    mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true, mArcPaint);

                    drawText(tmpAngle, sweepAngle, mStrs[i]);
                    drawIcons(tmpAngle, mImgsBitmap[i]);
                    tmpAngle += sweepAngle;
                }

                mStartAngle += mSpeed;

                if (isShouldEnd) {
                    mSpeed -= 1;
                }
                if (mSpeed <= 0) {
                    mSpeed = 0;
                    isShouldEnd = false;
                }
            }
        } catch (Exception e) {
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }


    public void panStart() {
        mSpeed = 50;
        isShouldEnd = false;

    }

    public void panEnd() {
        isShouldEnd = true;
    }

    public boolean isStart() {
        return mSpeed != 0;
    }


    public boolean isShouldEnd() {
        return isShouldEnd;
    }


    private void drawIcons(float tmpAngle, Bitmap bitmap) {

        int imgWidth = mRadius / 8;

        float angle = (float) ((tmpAngle + 360 / mItemCount / 2) * Math.PI / 180);

        int x = (int) (mCenter + mRadius / 2 / 2 * Math.cos(angle));
        int y = (int) (mCenter + mRadius / 2 / 2 * Math.sin(angle));

        Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);
        mCanvas.drawBitmap(bitmap, null, rect, null);


    }


    private void drawText(float tmpAngle, float sweepAngle, String string) {
        Path path = new Path();
        path.addArc(mRange, tmpAngle, sweepAngle);

        float textWidth = mTextPaint.measureText(string);
        int hOffset = (int) ((mRadius * Math.PI / mItemCount / 2) - (textWidth / 2));

        int vOffset = mRadius / 2 / mItemCount;

        mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);

    }


    private void drawBg() {
        mCanvas.drawColor(0xffffffff);
        mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding / 2,
                mPadding / 2, getMeasuredWidth() - mPadding / 2,
                getMeasuredHeight() - mPadding / 2), null);

    }
}
