package space.peihao.chuanchuan.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import space.peihao.chuanchuan.MainActivity;
import space.peihao.chuanchuan.R;
import space.peihao.chuanchuan.Record2_Activity;
import space.peihao.chuanchuan.Record_Activity;
import space.peihao.chuanchuan.constant.ConstantUtil;
import space.peihao.chuanchuan.sound.GameSoundPool;

public class ReadyView extends BaseView {
	
	private Bitmap background,button1,button3;
	private float bt1_x,bt1_y,bt2_x,bt2_y,bt3_x,bt3_y;
	private String button_text1,button_text2;
	private float eventx,eventy;
	Rect rect,rect1;
	private float rx;
	boolean isBtChange1=false,isBtChange2=false,isBtChange3=false;

	public ReadyView(Context context, GameSoundPool sounds) {
		super(context, sounds);
		thread = new Thread(this);
	}
	
	/**
	 *
	 */
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
	
	/**
	 *
	 */
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}
	
	/**
	 *
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
	}

	/**
	 *
	 */
	@Override
	public void initBitmap() {
		rx=(float)screen_width/320f;
		float ry=(float)screen_height/480f;
		paint.setColor(Color.WHITE);
		rx=rx<ry?rx:ry;
		paint.setTextSize(20 * rx);


		background = BitmapFactory.decodeResource(getResources(),  R.drawable.bg02);
		button1 = BitmapFactory.decodeResource(getResources(), R.drawable.button);
		//Log.i("aaaaaaaaa",""+button1.getWidth() * (screen_width / 320f)+"   "+button1.getWidth()+"   "+screen_width/320f);
		//button1 = BitmapFactory.decodeResource(getResources(), R.drawable.button1);
		button1=Bitmap.createScaledBitmap(button1, (int) (button1.getWidth() * (screen_width / 640f)), (int) (button1.getHeight() * (screen_height / 960f)), true);
		//button1=Bitmap.createScaledBitmap(button1,(int)(button1.getWidth()*(screen_width/640f)),(int)(button1.getHeight()*(screen_height/960f)),true);
		scalex = this.screen_width/background.getWidth();
		scaley = this.screen_height/background.getHeight();
		bt1_x = (this.screen_width-button1.getWidth())/2f;
		bt2_x = bt1_x;
		bt1_y = (this.screen_height)*1.0f/2.0f;
		bt2_y = bt1_y+button1.getHeight()+20*(screen_height/480f);
		bt3_x = (this.screen_width-button1.getWidth())/2f;
		bt3_y = bt2_y+button1.getHeight()+20*(screen_height/480f);
		button_text1 = "普通模式";
		button_text2 = "游戏结束";
		rect=new Rect();
		paint.getTextBounds(button_text1, 0, 4, rect);
		rect1=new Rect();
		paint.getTextBounds("英雄榜",0,3,rect1);
	}
	
	//
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			eventx=event.getX();
			eventy=event.getY();
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				//普通模式
				if(eventx<=(bt1_x+button1.getWidth())&&eventx>=bt1_x&&eventy>=bt1_y&&eventy<=(bt1_y+button1.getHeight())){
					isBtChange1=true;
				}
				else{
					isBtChange1=false;
				}
				//英雄榜
				if(eventx<=(bt2_x+button1.getWidth())&&eventx>=bt2_x&&eventy>=bt2_y&&eventy<=(bt2_y+button1.getHeight())){
					isBtChange2=true;
				}
				else{
					isBtChange2=false;
				}
				//游戏结束
				if(eventx<=(bt3_x+button1.getWidth())&&eventx>=bt2_x&&eventy>=bt3_y&&eventy<=(bt3_y+button1.getHeight())){
					isBtChange3=true;
				}
				else{
					isBtChange3=false;
				}
				break;
			case MotionEvent.ACTION_UP:
				//普通模式
				if(eventx<=(bt1_x+button1.getWidth())&&eventx>=bt1_x&&eventy>=bt1_y&&eventy<=(bt1_y+button1.getHeight())){
					mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
				}
				else{
					isBtChange1=false;
				}
				//英雄榜
				if(eventx<=(bt2_x+button1.getWidth())&&eventx>=bt2_x&&eventy>=bt2_y&&eventy<=(bt2_y+button1.getHeight())){
					//榜单
					Intent it=new Intent();
					it.setClass(mainActivity, Record_Activity.class);
					mainActivity.startActivity(it);
				}
				else{
					isBtChange2=false;
				}
				//游戏结束
				if(eventx<=(bt2_x+button1.getWidth())&&eventx>=bt2_x&&eventy>=bt3_y&&eventy<=(bt3_y+button1.getHeight())){
					mainActivity.alertDia();
					//mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
				}
				else{
					isBtChange3=false;
				}
				break;
			}
			return true;
		}
	
	/**
	 *
	 */
	@Override
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK); //
			canvas.save();
			canvas.scale(scalex, scaley, 0, 0); //
			canvas.drawBitmap(background, 0, 0, paint); //
			canvas.restore();
			if(isBtChange1){
				canvas.drawBitmap(button1, bt1_x, bt1_y, paint);
			}
			else canvas.drawBitmap(button1, bt1_x, bt1_y, paint);
			if(isBtChange2){
				canvas.drawBitmap(button1, bt2_x, bt2_y, paint);
			}else canvas.drawBitmap(button1, bt2_x, bt2_y, paint);
			if(isBtChange3){
				canvas.drawBitmap(button1,bt3_x,bt3_y,paint);
			}else canvas.drawBitmap(button1,bt3_x,bt3_y,paint);
			Rect rect1=new Rect();

			paint.setTextSize(24f*rx);
			paint.setColor(Color.GRAY);
			paint.getTextBounds("极限串串", 0, 4, rect1);
			canvas.drawText("极限串串", 0, 4, (screen_width - rect1.width()) / 2, (screen_height) / 3f, paint);
			paint.setTextSize(20f * rx);
			paint.setColor(Color.WHITE);
			canvas.drawText(button_text1, 0, 4, (screen_width - rect.width()) / 2, bt1_y + (button1.getHeight() + rect.height()) / 2, paint);
			canvas.drawText(button_text2, 0, 4, (screen_width - rect.width()) / 2, bt3_y + (button1.getHeight() + rect.height()) / 2, paint);
			paint.getTextBounds("英雄榜", 0, 3, rect1);
			canvas.drawText("英雄榜", 0, 3, (screen_width - rect1.width()) / 2, bt2_y + (button1.getHeight() + rect1.height()) / 2, paint);
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	//
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
