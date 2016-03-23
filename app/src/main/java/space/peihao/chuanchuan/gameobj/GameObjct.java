package space.peihao.chuanchuan.gameobj;

import android.graphics.Bitmap;

public class GameObjct {
	private Bitmap bitmap;
	private float x,y;
	private Bitmap imageChange;

	public GameObjct(Bitmap bitmap,Bitmap imagechage,float x,float y,float height,float width){
		this.bitmap=bitmap;
		this.imageChange=imagechage;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.state=0;
	}

	public GameObjct(){
		this.state=0;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	private int state;
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap getImageChange() {
		return imageChange;
	}

	public void setImageChange(Bitmap imageChange) {
		this.imageChange = imageChange;
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	private float width,height;

	public boolean actionConform(float cx,float cy){
		if((cx>=x)&&(cx<=x+bitmap.getWidth())&&cy<=y+bitmap.getHeight()){
			return true;
		}
		return false;
	}

}
