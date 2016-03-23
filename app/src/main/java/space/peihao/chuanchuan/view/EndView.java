package space.peihao.chuanchuan.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import space.peihao.chuanchuan.R;
import space.peihao.chuanchuan.Record2_Activity;
import space.peihao.chuanchuan.Record3_Activity;
import space.peihao.chuanchuan.Record_Activity;
import space.peihao.chuanchuan.constant.ConstantUtil;
import space.peihao.chuanchuan.sound.GameSoundPool;

public class EndView extends BaseView{

	private Bitmap background,button1;
	private float bt1_x,bt1_y,bt2_x,bt2_y,bt3_x,bt3_y;
	private String button_text1,button_text2;
	private float eventx,eventy;
	private Rect rect;
	private int arg;
	private int score;

	public EndView(Context context, GameSoundPool sounds,int arg,int score) {
		super(context, sounds);
		this.arg=arg;
		this.score=score;
		thread = new Thread(this);
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
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
	}
	
	@Override
	public void initBitmap() {
		float rx=(float)screen_width/320f;
		float ry=(float)screen_height/480f;
		rx=rx<ry?rx:ry;
		paint.setTextSize(20*rx);

		background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
		button1 = BitmapFactory.decodeResource(getResources(), R.drawable.button);

		button1=Bitmap.createScaledBitmap(button1, (int) (button1.getWidth() * (screen_width / 640f)), (int) (button1.getHeight() * (screen_height / 960f)), true);
		scalex = screen_width/background.getWidth();
		scaley = screen_height/background.getHeight();
		bt1_x = (screen_width-button1.getWidth())/2f;
		bt2_x = (screen_width-button1.getWidth())/2f;
		bt1_y = (screen_height)*1.0f/2.0f;
		bt2_y = bt1_y+button1.getHeight()+20*(screen_height/480f);
		bt3_x = (screen_width-button1.getWidth())/2f;
		bt3_y = bt2_y+button1.getHeight()+20*(screen_height/480f);
		rect=new Rect();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		eventx=event.getX();
		eventy=event.getY();
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_UP:
				//正常模式  穿太多结束
				if(this.arg==1){
					if(eventx<=(bt1_x+button1.getWidth())&&eventx>=bt1_x&&eventy>=bt1_y&&eventy<=(bt1_y+button1.getHeight())){
						//再来一次
						mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
					}
				}
				//正常模式  成功
				if(this.arg==3){
					if(eventx<=(bt1_x+button1.getWidth())&&eventx>=bt1_x&&eventy>=bt1_y&&eventy<=(bt1_y+button1.getHeight())){
						//下一关的入口 进无尽模式
						mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_ENDLESS_VIEW);
					}
					if(eventx<=(bt2_x+button1.getWidth())&&eventx>=bt2_x&&eventy>=bt2_y&&eventy<=(bt2_y+button1.getHeight())){
						//英雄榜
						Intent it=new Intent();
						it.setClass(mainActivity, Record_Activity.class);
						mainActivity.startActivity(it);
					}
				}
				//无尽模式  成功
				if(this.arg==6){
					if(eventx<=(bt1_x+button1.getWidth())&&eventx>=bt1_x&&eventy>=bt1_y&&eventy<=(bt1_y+button1.getHeight())){
						//下一关入口  进入随机模式
						mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_RAND_VIEW);
					}
					if(eventx<=(bt2_x+button1.getWidth())&&eventx>=bt2_x&&eventy>=bt2_y&&eventy<=(bt2_y+button1.getHeight())){
						//英雄榜
						Intent it=new Intent();
						it.setClass(mainActivity,Record2_Activity.class);
						mainActivity.startActivity(it);
					}
				}
				//无尽模式  穿太多
				if(this.arg==9){
					if(eventx<=(bt1_x+button1.getWidth())&&eventx>=bt1_x&&eventy>=bt1_y&&eventy<=(bt1_y+button1.getHeight())){
						//再来一次
						mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_ENDLESS_VIEW);
					}
				}
				//随机模式  穿太多
				if(this.arg==10){
					if(eventx<=(bt1_x+button1.getWidth())&&eventx>=bt1_x&&eventy>=bt1_y&&eventy<=(bt1_y+button1.getHeight())){
						//再来一次
						mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_RAND_VIEW);
					}
				}
				//随机模式  成功
				if(this.arg==11){
					if(eventx<=(bt1_x+button1.getWidth())&&eventx>=bt1_x&&eventy>=bt1_y&&eventy<=(bt1_y+button1.getHeight())){
						//再玩一次
						mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_RAND_VIEW);
					}
					if(eventx<=(bt2_x+button1.getWidth())&&eventx>=bt2_x&&eventy>=bt2_y&&eventy<=(bt2_y+button1.getHeight())){
						//英雄榜
						Intent it=new Intent();
						it.setClass(mainActivity,Record3_Activity.class);
						mainActivity.startActivity(it);
					}
				}
				if(eventx<=(bt3_x+button1.getWidth())&&eventx>=bt3_x&&eventy>=bt3_y&&eventy<=(bt3_y+button1.getHeight())){
					mainActivity.alertDia();
					//mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
				}
				break;
		}
		return true;
	}
	
	@Override
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK); //
			canvas.save();
			canvas.scale(scalex, scaley, 0, 0); //
			canvas.drawBitmap(background, 0, 0, paint); //
			canvas.restore();

			if(this.arg==1){
				paint.getTextBounds("闯关失败", 0, 4, rect);
				canvas.drawText("闯关失败", 0, 4, (screen_width - rect.width()) / 2, (screen_height) / 3f, paint);
				canvas.drawBitmap(button1, bt1_x, bt1_y, paint);
				canvas.drawBitmap(button1, bt3_x, bt3_y, paint);
				paint.setColor(Color.WHITE);
				canvas.drawText("再来一次", 0, 4, (screen_width - rect.width()) / 2, bt1_y + (button1.getHeight() + rect.height()) / 2, paint);
				canvas.drawText("游戏结束", 0, 4, (screen_width-rect.width())/2, bt3_y+(button1.getHeight()+rect.height())/2, paint);
				paint.setColor(Color.BLACK);
			}else if(this.arg==3){
				paint.getTextBounds("闯关成功", 0, 4, rect);
				canvas.drawText("闯关成功", 0, 4, (screen_width - rect.width()) / 2, (screen_height) / 3f, paint);
				Rect rect1=new Rect();
				paint.setColor(Color.WHITE);
				paint.getTextBounds("" + score, 0, String.valueOf(score).length(), rect1);
				canvas.drawText("" + score, 0, String.valueOf(score).length(), (screen_width - rect1.width()) / 2, 0.4f * (screen_height), paint);
				canvas.drawBitmap(button1, bt1_x, bt1_y, paint);
				canvas.drawBitmap(button1, bt2_x, bt2_y, paint);
				canvas.drawBitmap(button1, bt3_x, bt3_y, paint);
				canvas.drawText("无尽模式", 0, 4, (screen_width - rect.width()) / 2, bt1_y + (button1.getHeight() + rect.height()) / 2, paint);
				paint.getTextBounds("英雄榜", 0, 3, rect1);
				canvas.drawText("英雄榜", 0, 3, (screen_width - rect1.width()) / 2, bt2_y + (button1.getHeight() + rect1.height()) / 2, paint);
				canvas.drawText("游戏结束", 0, 4, (screen_width-rect.width())/2, bt3_y+(button1.getHeight()+rect.height())/2, paint);
				paint.setColor(Color.BLACK);
			}else if(this.arg==6){
				paint.getTextBounds("闯关成功", 0, 4, rect);
				canvas.drawText("闯关成功", 0, 4, (screen_width - rect.width()) / 2, (screen_height) / 3f, paint);
				Rect rect1=new Rect();
				paint.setColor(Color.WHITE);
				paint.getTextBounds("" + score, 0, String.valueOf(score).length(), rect1);
				canvas.drawText("" + score, 0, String.valueOf(score).length(), (screen_width - rect1.width()) / 2, 0.4f * (screen_height), paint);
				canvas.drawBitmap(button1, bt1_x, bt1_y, paint);
				canvas.drawBitmap(button1, bt2_x, bt2_y, paint);
				canvas.drawBitmap(button1, bt3_x, bt3_y, paint);
				paint.getTextBounds("英雄榜", 0, 3, rect1);
				canvas.drawText("随机模式", 0, 4, (screen_width - rect.width()) / 2, bt1_y + (button1.getHeight() + rect.height()) / 2, paint);
				canvas.drawText("英雄榜", 0, 3, (screen_width - rect1.width()) / 2, bt2_y + (button1.getHeight() + rect1.height()) / 2, paint);
				canvas.drawText("游戏结束", 0, 4, (screen_width - rect.width()) / 2, bt3_y + (button1.getHeight() + rect.height()) / 2, paint);
				paint.setColor(Color.BLACK);
			}
			else if(this.arg==9){
				//无尽失败
				Rect rect=new Rect();
				paint.getTextBounds("闯关失败", 0, 4, rect);
				canvas.drawText("闯关失败", 0, 4, (screen_width - rect.width()) / 2, (screen_height) / 3f, paint);
				canvas.drawBitmap(button1, bt1_x, bt1_y, paint);
				canvas.drawBitmap(button1, bt3_x, bt3_y, paint);
				paint.setColor(Color.WHITE);
				canvas.drawText("再来一次", 0, 4, (screen_width - rect.width()) / 2, bt1_y + (button1.getHeight() + rect.height()) / 2, paint);
				canvas.drawText("游戏结束", 0, 4, (screen_width-rect.width())/2, bt3_y+(button1.getHeight()+rect.height())/2, paint);
				paint.setColor(Color.BLACK);
			}
			else if(this.arg==10){
				//随机模式  失败
				Rect rect=new Rect();
				paint.getTextBounds("闯关失败", 0, 4, rect);
				canvas.drawText("闯关失败", 0, 4, (screen_width - rect.width()) / 2, (screen_height) / 3f, paint);
				canvas.drawBitmap(button1, bt1_x, bt1_y, paint);
				canvas.drawBitmap(button1, bt3_x, bt3_y, paint);
				paint.setColor(Color.WHITE);
				canvas.drawText("再来一次", 0, 4, (screen_width - rect.width()) / 2, bt1_y + (button1.getHeight() + rect.height()) / 2, paint);
				canvas.drawText("游戏结束", 0, 4, (screen_width-rect.width())/2, bt3_y+(button1.getHeight()+rect.height())/2, paint);
				paint.setColor(Color.BLACK);
			}else if(this.arg==11){
				//随机模式  成功
				Rect rect=new Rect();
				paint.getTextBounds("闯关成功", 0, 4, rect);
				canvas.drawText("闯关成功", 0, 4, (screen_width - rect.width()) / 2, (screen_height) / 3f, paint);
				canvas.drawBitmap(button1, bt1_x, bt1_y, paint);
				canvas.drawBitmap(button1, bt2_x, bt2_y, paint);
				canvas.drawBitmap(button1, bt3_x, bt3_y, paint);
				paint.setColor(Color.WHITE);
				canvas.drawText("再来一次", 0, 4, (screen_width - rect.width()) / 2, bt1_y + (button1.getHeight() + rect.height()) / 2, paint);
				canvas.drawText("游戏结束", 0, 4, (screen_width - rect.width()) / 2, bt3_y + (button1.getHeight() + rect.height()) / 2, paint);
				paint.getTextBounds("英雄榜", 0, 3, rect);
				canvas.drawText("英雄榜", 0, 3, (screen_width - rect.width()) / 2, bt2_y + (button1.getHeight() + rect.height()) / 2, paint);
				paint.setColor(Color.BLACK);
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
