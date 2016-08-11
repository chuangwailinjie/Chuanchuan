package space.peihao.xchuanchuan;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity{

    ChuanchuanLayout chuan;
    ItemView iv=null;
    ExplosionField ef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chuan=(ChuanchuanLayout) findViewById(R.id.chuanchuan);
        ef=ExplosionField.attach2Window(MainActivity.this);
        chuan.bind(ef);
    }
}
