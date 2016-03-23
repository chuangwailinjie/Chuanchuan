package space.peihao.chuanchuan.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import space.peihao.chuanchuan.DbHelper;
import space.peihao.chuanchuan.R;
import space.peihao.chuanchuan.constant.ConstantUtil;
import space.peihao.chuanchuan.fruit.GameFruitFactory;
import space.peihao.chuanchuan.gameobj.GameObjct;
import space.peihao.chuanchuan.sound.GameSoundPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class RandView extends BaseView{

    private int[][] nums = new int[5][5];
    private Bitmap back1;
    private Bitmap border,board;
    private Random rd;
    private Bitmap stick_head,stick_tail;
    private float[][][] xys=new float[5][5][2];
    private float f_x,f_y;
    //al中保存的是在地图中随机显示的水果图片
    private ArrayList<GameObjct> al;
    private List<Bitmap> fruits;
    //cFruits中保存的是手指滑动
    private List<GameObjct> cFruits;
    private float eventx,eventy;
    private int stage;
    private int score;
    private int passScore=50;
    private boolean actionup=true;
    private boolean isTouched=false;
    private float selfY;
    private int tCount=0;
    private boolean isTouch=false;
    private int isBoom=0;

    public RandView(Context context, GameSoundPool sounds) {
        super(context, sounds);
        thread = new Thread(this);
        rd=new Random();
        al=new ArrayList<GameObjct>();
        cFruits=new ArrayList<GameObjct>();
        stage=3;
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        super.surfaceCreated(arg0);
        initBitmap();
        if(thread.isAlive()){
            thread.start();
        }
        else{
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.surfaceChanged(arg0, arg1, arg2, arg3);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        super.surfaceDestroyed(arg0);
    }

    @Override
    public void initBitmap() {
        float x1=(screen_height/480f)<(screen_width/320f)?(screen_height/480f):(screen_width/320f);
        paint.setTextSize(20*x1);
        paint.setColor(Color.WHITE);

        back1=BitmapFactory.decodeResource(getResources(), R.drawable.back1);
        border=BitmapFactory.decodeResource(getResources(), R.drawable.border);
        board=BitmapFactory.decodeResource(getResources(), R.drawable.board);
        stick_head=BitmapFactory.decodeResource(getResources(),R.drawable.stick_head);
        stick_tail=BitmapFactory.decodeResource(getResources(),R.drawable.stick_tail);

        board=Bitmap.createScaledBitmap(board, (int) (screen_width), (int) (screen_height/8f), true);
        f_x=(screen_width-23)/back1.getWidth();
        f_y = (screen_height - board.getHeight()-24) / back1.getHeight();

        back1 = Bitmap.createScaledBitmap(back1, (int) (screen_width - 23*f_x), (int) (screen_height - board.getHeight() - 24*f_y), true);
        border=Bitmap.createScaledBitmap(border, (int) screen_width, (int) (screen_height - board.getHeight()), true);
        stick_head=Bitmap.createScaledBitmap(stick_head,(int)(screen_width/10),(int)(0.33f*screen_height),true);
        stick_tail=Bitmap.createScaledBitmap(stick_tail,(int)(screen_width/8),(int)screen_height,true);
        fruits=new GameFruitFactory(this,back1.getWidth(),back1.getHeight()).getFruits();
        for(int i=0;i<16;i++){
            Bitmap bit=Bitmap.createScaledBitmap(fruits.get(i),(int)(48*screen_width/320f),(int)(screen_height*50/480f),true);
            fruits.set(i,bit);
        }


        //爆炸图片载入
        {
            boom1=Bitmap.createScaledBitmap(boom1,fruits.get(0).getWidth()*2,fruits.get(0).getHeight()*2,true);
            boom2=Bitmap.createScaledBitmap(boom2,fruits.get(0).getWidth()*2,fruits.get(0).getHeight()*2,true);
            boom3=Bitmap.createScaledBitmap(boom3,fruits.get(0).getWidth()*2,fruits.get(0).getHeight()*2,true);
            boom4=Bitmap.createScaledBitmap(boom4,fruits.get(0).getWidth()*2,fruits.get(0).getHeight()*2,true);

        }

        eventx=screen_width/2f;

        for(int i=0;i<nums.length;i++){
            for(int j=0;j<nums[0].length;j++){
                int id=rd.nextInt(8);
                float x=11*(screen_width/border.getWidth())+j*back1.getWidth()/5+(back1.getWidth()/5-fruits.get(id).getWidth())/2;
                float y=13*(screen_height/(border.getHeight()-board.getHeight()))+i*back1.getHeight()/7+(back1.getHeight()/7-fruits.get(id).getHeight())/2;
                GameObjct go=new GameObjct(fruits.get(id),fruits.get(id+8),x,y,fruits.get(id).getWidth(),fruits.get(id).getHeight());
                al.add(go);
                xys[i][j][0]=x;
                xys[i][j][1]=y;
            }
        }

        new Thread(){
            public void run(){
                while(true){
                    if(isTouch){
                        tCount++;
                        selfY=tCount*(screen_height/20f);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        tCount=0;
                    }
                }
            }
        }.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        eventx=event.getX();
        eventy=event.getY();
        if(!isTouched)isTouched=true;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                actionup=false;
                isTouch=true;
                break;
            case MotionEvent.ACTION_UP:
                //将手指点击的区域对应的水果手动添加到cFruits
                for(int i=al.size()-1;i>=0;i--){
                    if(al.get(i).getState()==0) {
                        //将点击事件区域的包括下方所为区域的水果入队列
                        if (al.get(i).actionConform(eventx, screen_height-stick_head.getHeight()-selfY)) {
                            al.get(i).setState(1);
                            cFruits.add(al.get(i));
                        }
                    }
                }

                //将穿起来的水果state置为2
                for(GameObjct go:al){
                    if(go.getState()==1){
                        go.setState(2);
                    }
                }
                actionup=true;
                isTouch=false;
                checkFruit();
                checkScore();
                randMode();
                drawSelf();
                break;
        }
        return true;
    }

    //设置随机效果模式
    private void randMode(){
        for(GameObjct go:al){
            if(go.getState()==0){
                int id=rd.nextInt(8);
                go.setBitmap(fruits.get(id));
                go.setImageChange(fruits.get(id+8));
            }
        }
    }


    private void checkScore(){
        //如果一次点击的说过图片超过7个，则游戏结束
        if(cFruits.size()>=7){
            //这里添加msg参数  中途叉子上的水果太多
            Message msg=mainActivity.getHandler().obtainMessage();
            msg.what= ConstantUtil.TO_END_VIEW;
            msg.arg1=10;
            mainActivity.getHandler().sendMessage(msg);
            sounds.playSound(3,0);
        }else{
            int count=0;
            for(GameObjct go:al){
                if(go.getState()==0)count++;
            }
            //如果所有的图片都已经点击
            if(count==0){
                //Log.i("cFruit.size()-------：","执行2");
                //水果全部清除之后
                Message msg=mainActivity.getHandler().obtainMessage();
                msg.what=ConstantUtil.TO_END_VIEW;
                //地图水果清完，分数为达到
                if(score<passScore){
                    msg.arg1=2;
                    mainActivity.getHandler().sendMessage(msg);
                    sounds.playSound(3,0);
                }
                else{
                    //分数达到
                    ContentValues value = new ContentValues();
                    value.put("score", score);
                    DbHelper dbHelper = new DbHelper(mainActivity, "record2", null, 1);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.insert("tb_record2", null, value);

                    msg.arg1=11;
                    msg.arg2=score;
                    mainActivity.getHandler().sendMessage(msg);
                    sounds.playSound(5,0);
                }
            }
        }
    }

    private void checkFruit(){
        //双重循环进行一次判断，从最后开始向前判断，贪婪模式
        for(int j=cFruits.size()-1;j>=2;j--){
            int count=0;
            for(int i=j-1;i>=0;i--){
                if(cFruits.get(j).getBitmap()==cFruits.get(i).getBitmap()){
                    //如果是与前一个水果相同
                    count++;
                }else{
                    break;
                }
            }

            if(count==6){
                score+=200;
                cFruits.remove(j);
                cFruits.remove(j-1);
                cFruits.remove(j-2);
                cFruits.remove(j-3);
                cFruits.remove(j-4);
                cFruits.remove(j-5);
                cFruits.remove(j-6);
                sounds.playSound(6, 0);
                isBoom=1;
                j=cFruits.size();
            }else if(count==5){
                score+=150;
                cFruits.remove(j);
                cFruits.remove(j-1);
                cFruits.remove(j-2);
                cFruits.remove(j-3);
                cFruits.remove(j-4);
                cFruits.remove(j-5);
                sounds.playSound(6, 0);
                isBoom=1;
                j=cFruits.size();
            }else if(count==4){
                score+=75;
                cFruits.remove(j);
                cFruits.remove(j-1);
                cFruits.remove(j-2);
                cFruits.remove(j-3);
                cFruits.remove(j-4);
                sounds.playSound(6, 0);
                isBoom=1;
                j=cFruits.size();
            }else if(count==3){
                score+=35;
                cFruits.remove(j);
                cFruits.remove(j-1);
                cFruits.remove(j-2);
                cFruits.remove(j-3);
                sounds.playSound(6, 0);
                isBoom=1;
                j=cFruits.size();
            }else if(count==2){
                score+=10;
                cFruits.remove(j);
                cFruits.remove(j-1);
                cFruits.remove(j-2);
                sounds.playSound(6, 0);
                isBoom=1;
                j=cFruits.size();
            }else if(count==1||count==0){
                isBoom=0;
                continue;
            }
            //cnum=j;
        }
    }
    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            //canvas.drawColor(Color.WHITE); // 背景颜色
            //格子背景
            canvas.drawBitmap(back1, 10, 10, paint);
            //叉子以及叉子上的水果
            if(actionup) {
                canvas.drawBitmap(stick_head,eventx-stick_head.getWidth()/2.0f,screen_height-stick_head.getHeight(),paint);
                if(cFruits.size()>=1)
                    canvas.drawBitmap(cFruits.get(cFruits.size()-1).getBitmap(),eventx-fruits.get(0).getWidth()/2f,screen_height-(board.getHeight()+20+cFruits.get(cFruits.size()-1).getHeight()),paint);
            }
            else{
                canvas.drawBitmap(stick_tail, eventx - stick_tail.getWidth() / 2.0f, screen_height-stick_head.getHeight()-selfY,paint);
                if(cFruits.size()>=1){
                    for(int i=cFruits.size()-1;i>=0;i--){
                        GameObjct go=cFruits.get(i);
                        canvas.drawBitmap(go.getBitmap(),eventx-fruits.get(0).getWidth()/2f,(screen_height-stick_head.getHeight()-selfY)+10+go.getBitmap().getHeight()*(cFruits.size()-1-i),paint);
                    }
                }
            }

            //计分板
            canvas.drawBitmap(board, 0, screen_height-board.getHeight(), paint);

            for(GameObjct go:al){
                if(go.getState()==0){
                    canvas.drawBitmap(go.getBitmap(),go.getX(),go.getY(),paint);
                }else if(go.getState()==1){
                    canvas.drawBitmap(go.getImageChange(),go.getX(),go.getY(),paint);
                }
            }

            //边界
            canvas.drawBitmap(border, 0, 0, paint);

            canvas.drawText(String.valueOf(stage),screen_width*(40f/320), border.getHeight()+(85/110f)*board.getHeight(),paint);
            canvas.drawText(String.valueOf(score),screen_width*(105f/320), border.getHeight() + (85 / 110f) * board.getHeight(), paint);
            canvas.drawText(String.valueOf(cFruits.size()),screen_width*(158f/320), border.getHeight()+(70/110f)*board.getHeight(),paint);
            canvas.drawText(String.valueOf(7), screen_width * (185f / 320), border.getHeight() + (75 / 110f) * board.getHeight(), paint);

            //爆炸特效
            if(isBoom>0){
                if(isBoom==1)canvas.drawBitmap(boom1,eventx-boom2.getWidth()/2f,screen_height-stick_head.getHeight()-selfY,paint);
                else if(isBoom==2)canvas.drawBitmap(boom2,eventx-boom2.getWidth()/2f,screen_height-stick_head.getHeight()-selfY,paint);
                else if(isBoom==3)canvas.drawBitmap(boom3,eventx-boom2.getWidth()/2f,screen_height-stick_head.getHeight()-selfY,paint);
                else if(isBoom==4)canvas.drawBitmap(boom4,eventx-boom2.getWidth()/2f,screen_height-stick_head.getHeight()-selfY,paint);
                isBoom++;
                if(isBoom==5)isBoom=0;
            }

            if(!isTouched){
                float x=screen_width/320f;
                float y=screen_height/480f;
                x=x<y?x:y;
                paint.setTextSize(60 * x);
                Rect smrect=new Rect();
                paint.getTextBounds("Ready",0,5,smrect);
                canvas.drawText("Ready", (screen_width - smrect.width()) / 2f, (screen_height - smrect.height()) / 2, paint);
                paint.setTextSize(20*x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            drawSelf();
            long endTime = System.currentTimeMillis();
            try {
                if (endTime - startTime < 400)
                    Thread.sleep(400 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
    }
}
