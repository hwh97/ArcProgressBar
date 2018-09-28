package cn.hwwwwh.circleprogress;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by xuyong on 17/7/17.
 *
 * modify by hwhong on 18/9/20.
 */
public class ArcProgressBar extends View {
    /**
     * 外层背景圆弧画笔
     */
    private Paint mArcBgPaint;
    /**
     * 外层前景圆弧画笔
     */
    private Paint mArcForePaint;
    /**
     * 内层虚线画笔
     */
    private Paint mDottedLinePaint;
    /**
     * 刻度文字画笔
     */
    private Paint mTextHintPaint;
    /**
     * 底部字矩形背景画笔
     */
    private Paint mRonudRectPaint;
    /**
     * 进度画笔
     */
    private Paint mProgressPaint;
    /**
     * 进度圆画笔
     */
    private Paint mProgressRoundPaint;
    /**
     * 外层圆弧需要
     */
    private RectF mArcRect;
    /**
     * 圆弧宽度
     */
    private float mArcWidth = 1.0f;
    /**
     * 背景圆弧颜色
     */
    private int mArcBgColor = 0xFFC9D7EC;
    /**
     * 前景圆弧结束颜色
     */
    private int mArcForeEndColor = 0xFF46D0FA;
    /**
     * 前景圆弧开始颜色
     */
    private int mArcForeStartColor = 0xFF3486D7;
    /**
     * 虚线默认颜色
     */
    private int mDottedDefaultColor = 0xFF9799A1;
    /**
     * 虚线变动颜色
     */
    private int mDottedRunColor = 0xFF3895EB;
    /**
     * 刻度文字颜色
     */
    private int mTextHintPaintColor = 0xFF000000;
    /**
     * 圆弧两边的距离
     */
    private int mPdDistance = 50;

    /**
     * 线条数
     */
    private int mDottedLineCount = 100;
    /**
     * 线条宽度
     */
    private int mDottedLineWidth = 40;
    /**
     * 分割点线条宽度
     */
    private int mDottedSectionLineWidth = 10;
    /**
     * 线条高度
     */
    private int mDottedLineHeight = 6;
    /**
     * 圆弧跟虚线之间的距离
     */
    private int mLineDistance = 0;
    /**
     * 进度显示圆半径
     */
    private int mCircleRadius = 18;
    /**
     * 进度显示圆距离
     */
    private int mCircleDistance = 4;
    /**
     * 进度条最大值
     */
    private int mProgressMax = 100;
    /**
     * 进度文字大小
     */
    private int mProgressTextSize = 35;
    /**
     * 进度文字颜色
     */
    private int mProgressTextRunColor = 0xFFFFFFFF;
    /**
     * 进度圆背景颜色
     */
    private int mProgressRoundBgColor = 0xFF3F51B5;
    /**
     * 进度描述
     */
    private String mProgressDesc;

    //是否使用渐变
    protected boolean useGradient = true;

    private int mScressWidth;
    private int mProgress;
    private float mExternalDottedLineRadius;
    private float mExternalSectionDottedLineRadius;
    private float mSectionTextRadius;
    private float mProgressTextRadius;
    private float mInsideDottedLineRadius;
    private int mArcCenterX;
    private int mArcRadius; // 圆弧半径
    private double bDistance;
    private double aDistance;
    private boolean isRestart = false;
    private int mRealProgress = -1;


    public ArcProgressBar(Context context) {
        this(context, null, 0);
    }

    public ArcProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiAttributes(context, attrs);
        initView();
    }

    //开放api//
    public void setmArcBgColor(int mArcBgColor) {
        this.mArcBgColor = mArcBgColor;
    }

    public void setmArcForeEndColor(int mArcForeEndColor) {
        this.mArcForeEndColor = mArcForeEndColor;
    }

    public void setmArcForeStartColor(int mArcForeStartColor) {
        this.mArcForeStartColor = mArcForeStartColor;
    }

    public void setmDottedDefaultColor(int mDottedDefaultColor) {
        this.mDottedDefaultColor = mDottedDefaultColor;
    }

    public void setmDottedRunColor(int mDottedRunColor) {
        this.mDottedRunColor = mDottedRunColor;
    }

    public void setmPdDistance(int mPdDistance) {
        this.mPdDistance = mPdDistance;
    }

    public void setmDottedLineCount(int mDottedLineCount) {
        this.mDottedLineCount = mDottedLineCount;
    }

    public void setmDottedLineWidth(int mDottedLineWidth) {
        this.mDottedLineWidth = mDottedLineWidth;
    }

    public void setmDottedLineHeight(int mDottedLineHeight) {
        this.mDottedLineHeight = mDottedLineHeight;
    }

    public void setmLineDistance(int mLineDistance) {
        this.mLineDistance = mLineDistance;
    }

    public void setmProgressMax(int mProgressMax) {
        this.mProgressMax = mProgressMax;
    }

    public void setmProgressTextSize(int mProgressTextSize) {
        this.mProgressTextSize = mProgressTextSize;
    }

    public void setmProgressTextRunColor(int mProgressTextRunColor) {
        this.mProgressTextRunColor = mProgressTextRunColor;
    }

    public void setmProgressDesc(String mProgressDesc) {
        this.mProgressDesc = mProgressDesc;
    }

    public void setUseGradient(boolean useGradient) {
        this.useGradient = useGradient;
    }


    //开放api//

    private void intiAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcProgressBar);
        mPdDistance = a.getInteger(R.styleable.ArcProgressBar_arcDistance, mPdDistance);
        mArcBgColor = a.getColor(R.styleable.ArcProgressBar_arcBgColor, mArcBgColor);
        mArcForeStartColor = a.getColor(R.styleable.ArcProgressBar_arcForeStartColor, mArcForeStartColor);
        mArcForeEndColor = a.getColor(R.styleable.ArcProgressBar_arcForeEndColor, mArcForeEndColor);
        mDottedDefaultColor = a.getColor(R.styleable.ArcProgressBar_dottedDefaultColor, mDottedDefaultColor);
        mDottedRunColor = a.getColor(R.styleable.ArcProgressBar_dottedRunColor, mDottedRunColor);
        mDottedLineCount = a.getInteger(R.styleable.ArcProgressBar_dottedLineCount, mDottedLineCount);
        mDottedLineWidth = a.getInteger(R.styleable.ArcProgressBar_dottedLineWidth, mDottedLineWidth);
        mDottedLineHeight = a.getInteger(R.styleable.ArcProgressBar_dottedLineHeight, mDottedLineHeight);
        mLineDistance = a.getInteger(R.styleable.ArcProgressBar_lineDistance, mLineDistance);
        mProgressMax = a.getInteger(R.styleable.ArcProgressBar_progressMax, mProgressMax);
        mProgressTextSize = a.getInteger(R.styleable.ArcProgressBar_arcProgressTextSize, mProgressTextSize);
        mProgressDesc = a.getString(R.styleable.ArcProgressBar_progressDesc);
        mProgressTextRunColor = a.getColor(R.styleable.ArcProgressBar_progressTextRunColor, mProgressTextRunColor);
        useGradient = a.getBoolean(R.styleable.ArcProgressBar_arcUseGradient, useGradient);
        mProgressRoundBgColor = a.getColor(R.styleable.ArcProgressBar_progressRoundBgColor, mProgressRoundBgColor);
        mTextHintPaintColor = a.getColor(R.styleable.ArcProgressBar_textHintPaintColor, mTextHintPaintColor);
        mDottedSectionLineWidth = a.getInteger(R.styleable.ArcProgressBar_dottedSectionLineWidth, mDottedSectionLineWidth);
        a.recycle();
    }

    private void initView() {
        int[] screenWH = getScreenWH();
        mScressWidth = screenWH[0];
        // 外层背景圆弧的画笔
        mArcBgPaint = new Paint();
        mArcBgPaint.setAntiAlias(true);
        mArcBgPaint.setStyle(Paint.Style.STROKE);
        mArcBgPaint.setStrokeWidth(dp2px(getResources(), mArcWidth));
        mArcBgPaint.setColor(mArcBgColor);
        mArcBgPaint.setStrokeCap(Paint.Cap.ROUND);
        // 外层前景圆弧的画笔
        mArcForePaint = new Paint();
        mArcForePaint.setAntiAlias(true);
        mArcForePaint.setStyle(Paint.Style.STROKE);
        mArcForePaint.setStrokeWidth(dp2px(getResources(), mArcWidth));
        mArcForePaint.setStrokeCap(Paint.Cap.ROUND);
        // 内测虚线的画笔
        mDottedLinePaint = new Paint();
        mDottedLinePaint.setAntiAlias(true);
        mDottedLinePaint.setStrokeWidth(dp2px(getResources(), mDottedLineHeight));
        mDottedLinePaint.setColor(mDottedDefaultColor);
        //
        mRonudRectPaint = new Paint();
        mRonudRectPaint.setAntiAlias(true);
        mRonudRectPaint.setColor(mDottedRunColor);
        mRonudRectPaint.setStyle(Paint.Style.FILL);
        // 进度画笔
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        //
        mProgressRoundPaint = new Paint();
        mProgressRoundPaint.setAntiAlias(true);
        //
        mTextHintPaint = new Paint();
        mTextHintPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = mScressWidth - 2 * mPdDistance;
//        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mArcCenterX = (int) (w / 2.f);

        mArcRect = new RectF();
        mArcRect.top = dp2px(getResources(), mCircleDistance + mCircleRadius * 2);
        mArcRect.left = dp2px(getResources(), mCircleDistance + mCircleRadius * 2);
        mArcRect.right = w - dp2px(getResources(), mCircleDistance + mCircleRadius * 2);
        mArcRect.bottom = h - dp2px(getResources(), mCircleDistance + mCircleRadius * 2);
        // 设置内容padding
        mArcRect.inset(dp2px(getResources(), mArcWidth) / 2, dp2px(getResources(), mArcWidth) / 2);
        mArcRadius = (int) (mArcRect.width() / 2);

        double sqrt = Math.sqrt(mArcRadius * mArcRadius + mArcRadius * mArcRadius);
        bDistance = Math.cos(Math.PI * 45 / 180) * mArcRadius;
        aDistance = Math.sin(Math.PI * 45 / 180) * mArcRadius;

        // 内部虚线的外部半径
        mExternalDottedLineRadius = mArcRadius - dp2px(getResources(), mArcWidth) / 2 - dp2px(getResources(), mLineDistance);
        // 内部虚线的内部半径
        mInsideDottedLineRadius = mExternalDottedLineRadius - dp2px(getResources(), mDottedLineWidth);
        mExternalSectionDottedLineRadius = mExternalDottedLineRadius - dp2px(getResources(), mDottedSectionLineWidth);
        mSectionTextRadius =  mArcRadius - dp2px(getResources(), mArcWidth) / 2 - dp2px(getResources(), 10) - dp2px(getResources(), mDottedSectionLineWidth);
        mProgressTextRadius = mArcRadius + dp2px(getResources(), mCircleDistance + mCircleRadius);
        if (useGradient) {
            LinearGradient gradient = new LinearGradient(0, 0, getMeasuredWidth(), getMeasuredHeight(), mArcForeEndColor, mArcForeStartColor, Shader.TileMode.CLAMP);
            mArcForePaint.setShader(gradient);
        } else {
            mArcForePaint.setColor(mArcForeStartColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mArcBgPaint.setColor(mArcBgColor);
        //canvas.drawText("信用额度", mArcCenterX - mProgressPaint.measureText("信用额度") / 2,
        //      mArcCenterX - (mProgressPaint.descent() + mProgressPaint.ascent()) / 2 - dp2px(getResources(), 20), mProgressPaint);
        //drawRunText(canvas);

        mTextHintPaint.setTextSize(dp2px(getResources(), 14));
        mTextHintPaint.setColor(mTextHintPaintColor);
        mProgressRoundPaint.setColor(mProgressRoundBgColor);

        canvas.rotate(135, mArcCenterX, mArcCenterX);
        drawDottedLineArc(canvas);
        canvas.drawArc(mArcRect, 0, 270, false, mArcBgPaint);//画背景圆弧
//        canvas.drawRect(mArcRect, mArcBgPaint);//画直角矩形
//        canvas.drawCircle(400, 400, 100, mArcForePaint);//画圆
        canvas.drawArc(mArcRect, 0, 0, false, mArcForePaint);//画前景圆弧
//        canvas.drawColor(Color.TRANSPARENT);//设置画布背景
//        canvas.drawLine(100, 100, 400, 400, mArcBgPaint);//画直线
//        canvas.drawPoint(500, 500, mArcBgPaint);//画点
        mProgressPaint.setColor(mProgressTextRunColor);
        mProgressPaint.setTextSize(dp2px(getResources(), 12));
        if(mRealProgress != -1){// 不绘制其他直绘制刻度盘
            drawRunDottedLineArc(canvas);
            drawRunFullLineArc(canvas);
        }
//        if (isRestart) {
//            drawDottedLineArc(canvas);
//        }
    }

    private void drawRunText(Canvas canvas) {
        String progressStr = (mRealProgress * 25) + "";
        if (!TextUtils.isEmpty(mProgressDesc)) {
            progressStr = mProgressDesc;
        }
        mProgressPaint.setTextSize(dp2px(getResources(), mProgressTextSize));
        mProgressPaint.setColor(mProgressTextRunColor);
        canvas.drawText(progressStr, mArcCenterX - mProgressPaint.measureText(progressStr) / 2,
                mArcCenterX - (mProgressPaint.descent() + mProgressPaint.ascent()) / 2 + dp2px(getResources(), 6), mProgressPaint);
    }

    public void reset() {
        mRealProgress = -1;
        invalidate();
    }

    /**
     * 设置中间进度描述
     *
     * @param desc
     */
    public void setProgressDesc(String desc) {
        this.mProgressDesc = desc;
        postInvalidate();
    }

    /**
     * 设置最大进度
     *
     * @param max
     */
    public void setMaxProgress(int max) {
        this.mProgressMax = max;
    }


    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        // 进度100% = 控件的75%
        this.mRealProgress = progress;
        isRestart = false;
        this.mProgress = (((mDottedLineCount - 1)  * 3 / 4) * progress) / mProgressMax;
        postInvalidate();
    }


    /**
     * 虚线变动
     *
     * @param canvas
     */
    private void drawRunDottedLineArc(Canvas canvas) {
        mDottedLinePaint.setColor(mDottedRunColor);
        float evenryDegrees = (float) (270.f / mDottedLineCount);

        // float startDegress = (float) (225 * Math.PI / 180) + evenryDegrees / 2;
        double length = mRealProgress * 1.0 / mProgressMax * mDottedLineCount + 1;
        for (int i = 0; i < (int)length;  i++) {
            float degrees = i * evenryDegrees ;
            float startX;
            float startY;
            if((i % 10 == 0 && i >= 10) || i == 0){
                startX = mArcCenterX + (float) Math.cos(degrees  * Math.PI / 180) * mExternalSectionDottedLineRadius;
                startY = mArcCenterX + (float) Math.sin(degrees * Math.PI / 180) * mExternalSectionDottedLineRadius;
            }else{
                startX = mArcCenterX + (float) Math.cos(degrees  * Math.PI / 180) * mInsideDottedLineRadius;
                startY = mArcCenterX + (float) Math.sin(degrees * Math.PI / 180) * mInsideDottedLineRadius;
            }
            float stopX;
            float stopY;
            stopX = mArcCenterX + (float) Math.cos(degrees  * Math.PI / 180) * mExternalDottedLineRadius;
            stopY = mArcCenterX + (float) Math.sin(degrees  * Math.PI / 180) * mExternalDottedLineRadius;

            canvas.drawLine(startX, startY, stopX, stopY, mDottedLinePaint);
        }

    }

    /**
     * 实线变动
     *
     * @param canvas
     */
    private void drawRunFullLineArc(Canvas canvas) {
        //for (int i = 0; i < mProgress; i++) {
        canvas.drawArc(mArcRect, 0, 270 * mRealProgress / mProgressMax , false, mArcForePaint);
        // 画提示进度圆
        float degrees = (float) ((mRealProgress * 1.0f / mProgressMax * 270) / 180 * Math.PI);
        float stopX;
        float stopY;
        stopX = mArcCenterX + (float) Math.cos(degrees) * mProgressTextRadius;
        stopY = mArcCenterX + (float) Math.sin(degrees) * mProgressTextRadius;
        canvas.drawCircle(stopX, stopY, dp2px(getResources(), mCircleRadius), mProgressRoundPaint);
        // 画提示点小角
        float triangleX = mArcCenterX + (float) Math.cos(degrees) * (mArcRadius + dp2px(getResources(),1));
        float triangleY = mArcCenterX + (float) Math.sin(degrees) * (mArcRadius + dp2px(getResources(),1));
        RectF oval=new RectF(); //RectF对象
        // 拓展oval
        oval.left = triangleX - 80; //左边
        oval.top = triangleY - 80; //上边
        oval.right = triangleX + 80; //右边
        oval.bottom = triangleY + 80; //下边
        canvas.drawArc(oval,   270 * mRealProgress / mProgressMax, 30, true, mProgressRoundPaint); //绘制圆弧
        // 在进度圆内画字
        canvas.rotate(-135, stopX, stopY);
        drawText(canvas, mRealProgress + "℃", stopX, stopY, mProgressPaint);
        canvas.rotate( 135 , stopX, stopY);
        //}
    }

    private void drawDottedLineArc(Canvas canvas) {
        mDottedLinePaint.setColor(mDottedDefaultColor);
        // 360 * Math.PI / 180
        float evenryDegrees = (float) (270.f / mDottedLineCount);

        float startDegress = (float) (135 * Math.PI / 180);
        float endDegress = (float) (225 * Math.PI / 180);

        for (int i = 0; i < mDottedLineCount + 1; i++) {
            float degrees = i * evenryDegrees;
//            // 过滤底部90度的弧长
//            if (degrees > startDegress && degrees < endDegress) {
//                continue;
//            }
            float startX;
            float startY;
            if( (i % 10 == 0 && i >= 10) || i == 0){// 画长刻度
                startX = mArcCenterX + (float) Math.cos(degrees  * Math.PI / 180) * mExternalSectionDottedLineRadius;
                startY = mArcCenterX + (float) Math.sin(degrees * Math.PI / 180) * mExternalSectionDottedLineRadius;
            }else{        // 画普通刻度
                startX = mArcCenterX + (float) Math.cos(degrees  * Math.PI / 180) * mInsideDottedLineRadius;
                startY = mArcCenterX + (float) Math.sin(degrees * Math.PI / 180) * mInsideDottedLineRadius;
            }
            float stopX;
            float stopY;
            stopX = mArcCenterX + (float) Math.cos(degrees  * Math.PI / 180) * mExternalDottedLineRadius;
            stopY = mArcCenterX + (float) Math.sin(degrees  * Math.PI / 180) * mExternalDottedLineRadius;
            canvas.drawLine(startX, startY, stopX, stopY, mDottedLinePaint);
        }
        // 画刻度文字
        for (int i = 0; i < mDottedLineCount + 1; i++) {
            float degrees = i * evenryDegrees;
            if((i % 10 == 0 && i >= 10) || i == 0){
                float startX;
                float startY;
                startX = (mArcCenterX ) + (float) Math.cos(degrees  * Math.PI / 180) * (mSectionTextRadius);
                startY = (mArcCenterX ) + (float) Math.sin(degrees * Math.PI / 180) * (mSectionTextRadius);
                // 旋转刻度文字
                canvas.rotate((float) (degrees + 90) , startX, startY);
                drawText(canvas, i + "",startX,startY, mTextHintPaint);
                canvas.rotate(-(float) (degrees + 90) , startX, startY);
            }
        }

    }

    private int[] getScreenWH() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int[] wh = {displayMetrics.widthPixels, displayMetrics.heightPixels};
        return wh;
    }

    private float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static void drawText(Canvas canvas, String text, float x, float y, Paint paint){
        //将基准点放在文本的中心（仍在基准线上）
        paint.setTextAlign(Paint.Align.CENTER);
        Rect bounds=new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);
        //求出基准线y坐标的值,y-bounds.height()/2得到文本最上沿的y坐标，再-bounds.top地到基准线y坐标
        float realY=y-bounds.height()/2-bounds.top;
        canvas.drawText(text,x ,realY,paint);
    }

}
