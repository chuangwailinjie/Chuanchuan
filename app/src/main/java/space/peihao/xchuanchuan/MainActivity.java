package space.peihao.xchuanchuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity implements ChuanchuanLayout.onChuanchaunListener {

    ChuanchuanLayout chuan;
    ItemView iv=null;
    ExplosionField ef;
    private TextView tv_score;
    private TextView tv_length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chuan=(ChuanchuanLayout) findViewById(R.id.chuanchuan);
        tv_score=(TextView)findViewById(R.id.tv_score);
        tv_length=(TextView)findViewById(R.id.tv_listsize);
        ef=ExplosionField.attach2Window(MainActivity.this);
        chuan.bind(ef);
        chuan.setLayoutListener(this);
        onScorechange(0,0);
    }

    @Override
    public void onScorechange(int score,int length) {
        tv_score.setText("Score: "+score);
        tv_length.setText("Length: "+length);
    }

    public void restart(){
        this.recreate();
    }

    @Override
    public void gameOver() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("窗外临街");
        builder.setMessage("Small ZhaZha，GameOver!Go Home?");
        builder.setPositiveButton("Restart",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restart();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
