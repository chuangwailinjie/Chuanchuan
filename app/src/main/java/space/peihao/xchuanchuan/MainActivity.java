package space.peihao.xchuanchuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends Activity implements ChuanchuanLayout.onChuanchaunListener {

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

    @Override
    public void passGame() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Congratulation");
        builder.setMessage("Congratulate You，Pass the stage-");
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
