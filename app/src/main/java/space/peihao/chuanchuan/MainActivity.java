package space.peihao.chuanchuan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import space.peihao.chuanchuan.constant.ConstantUtil;
import space.peihao.chuanchuan.sound.GameSoundPool;
import space.peihao.chuanchuan.view.EndView;
import space.peihao.chuanchuan.view.EndlessView;
import space.peihao.chuanchuan.view.MainView;
import space.peihao.chuanchuan.view.RandView;
import space.peihao.chuanchuan.view.ReadyView;

public class MainActivity extends Activity {
    private ReadyView readyView;
    private MainView mainView;
    private EndView endview;
    private EndlessView endlessView;
    private GameSoundPool sounds;
    private MediaPlayer mediaPlayer;
    private RandView randView;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == ConstantUtil.TO_MAIN_VIEW){
                toMainView();
            }
            else  if(msg.what == ConstantUtil.TO_END_VIEW){
                if(msg.arg1==6||msg.arg1==3);
                else msg.arg2=0;
                //Log.i("msg.args","msg.arg1="+msg.arg1+"  msg.arg2="+msg.arg2);
                    toEndView(msg.arg1, msg.arg2);
            }
            else  if(msg.what == ConstantUtil.END_GAME){
                endGame();
            }
            else if(msg.what==ConstantUtil.TO_ENDLESS_VIEW){
                toEndlessView(0);
            }
            else if(msg.what==ConstantUtil.TO_RAND_VIEW){
                toRandView(0);
            }
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sounds = new GameSoundPool(this);
        sounds.initGameSound();
        readyView = new ReadyView(this,sounds);
        setContentView(readyView);
    }

    /**
     *
     */
    private void endGame() {
        this.finish();
        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }

    /**
     *
     * @param arg1
     */
    private void toEndView(int arg1,int score) {
        if( endview== null){
            endview = new EndView(this,sounds,arg1,score);
        }
        setContentView(endview);
        try {
            AssetManager am = getAssets();
            AssetFileDescriptor fileDescriptor = am.openFd("bgm.mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        readyView = null;
        mainView=null;
        endlessView=null;
        randView=null;
        endview=null;
    }

    /**
     *
     */
    private void toMainView() {
        if(mainView == null){
            mainView = new MainView(this,sounds);
        }
        setContentView(mainView);
        try {
            AssetManager am = getAssets();
            AssetFileDescriptor fileDescriptor = am.openFd("bgm.mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        readyView = null;
        endlessView=null;
        randView=null;
        mainView=null;
        endview=null;
    }

    private void toEndlessView(int arg1) {
        if( endlessView== null){
            endlessView = new EndlessView(this,sounds);
        }
        setContentView(endlessView);
        try {
            AssetManager am = getAssets();
            AssetFileDescriptor fileDescriptor = am.openFd("bgm.mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        readyView = null;
        mainView=null;
        endview=null;
        endlessView=null;
        randView=null;
    }

    private void toRandView(int arg1) {
        if( randView== null){
            randView = new RandView(this,sounds);
        }
        setContentView(randView);
        try {
            AssetManager am = getAssets();
            AssetFileDescriptor fileDescriptor = am.openFd("bgm.mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        readyView = null;
        mainView=null;
        endlessView=null;
        endview=null;
        randView=null;
    }
    public void alertDia(){
        new AlertDialog.Builder(MainActivity.this).setTitle("游戏提示")//设置对话框标题

                .setMessage("确认是否退出游戏！")//设置显示的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        MainActivity.this.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
                    }
                }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮
            public void onClick(DialogInterface dialog, int which) {//响应事件
                //Log.i("alertdialog", " 请保存数据！");
            }
        }).show();//在按键响应事件中显示此对话框
    }
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

}
