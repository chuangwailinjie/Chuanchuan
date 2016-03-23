package space.peihao.chuanchuan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        /*if (StepService.FLAG || StepDetector.CURRENT_SETP > 0) {// 程序已经启动，直接跳转到运行界面
            Intent intent = new Intent(splash.this, Main.class); //创建一个新的Intent，指定当前应用程序上下文
            //如果已经启动软件  直接跳转
            startActivity(intent);												//传递这个intent给startActivity
            this.finish();
        } else */{
            //CountDownTimer(long millisInFuture, long countDownInterval)
            /*
            Schedule a countdown until a time in the future, with regular notifications on intervals along the way. Example of showing a 30 second countdown in a text field:

            new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                }

            public void onFinish() {
                mTextField.setText("done!");
                }
            }.start();
            countDownInterval	The interval along the way to receive onTick(long) callbacks.
            * */
            new CountDownTimer(2000L, 1000L)
            {
                //定时启动  在2000L之后启动onFinish
                public void onFinish()
                {
                    //启动界面淡入淡出效果
                    Intent intent = new Intent();
                    intent.setClass(splash.this, MainActivity.class);
                    startActivity(intent);
                    //淡入淡出
                    //Call immediately after one of the flavors of startActivity(Intent) or finish()
                    //to specify an explicit transition animation to perform next.
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();

                }

                //一直执行  millisUntilFinished每次countDownInterval更新
                public void onTick(long millisUntilFinished)
                {
                    //millisUntilFinished之后启动
                    //tv.setText("seconds remaining: " + millisUntilFinished / 1000);
                }
            }.start();
        }
    }
}