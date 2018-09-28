package cn.hwwwwh.circleprogress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by xuyong on 17/7/17.
 *
 * modify by hwhong on 18/9/20.
 */
public class MainActivity extends AppCompatActivity {

    private ArcProgressBar mArcProgressBar;
    private Button btnInit;
    private Button btnRandom;
    private int lastValue = 0;
    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mArcProgressBar = (ArcProgressBar) findViewById(R.id.arcProgressBar);
        btnInit = (Button)findViewById(R.id.btn_init);
        btnRandom = (Button)findViewById(R.id.btn_random);

        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArcProgressBar.reset();
                lastValue = 0;
            }
        });

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = (int) ((Math.random() * 100));
                setProgressView(num);
                lastValue = num;
            }
        });
    }

    public void setProgressView(int value){
        valueAnimator = ValueAnimator.ofInt(lastValue, value);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcProgressBar.setProgress((int) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        if(value - lastValue >= 30){
            valueAnimator.setDuration(2000);
        }else{
            valueAnimator.setDuration(1000);
        }
        valueAnimator.start();
    }


}
