package space.peihao.chuanchuan.fruit;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import space.peihao.chuanchuan.R;
import space.peihao.chuanchuan.view.MainView;

public class GameFruitFactory {
	private View mainView;
	private List<Bitmap> fruits;
	private float x,y;
	public GameFruitFactory(View mainView,float x,float y) {
		this.mainView = mainView;
		this.x=x;
		this.y=y;
		fruits = new ArrayList<Bitmap>();
		CreateFruit();
	}
	private void CreateFruit(){
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_1_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_2_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_3_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_4_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_5_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_6_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_7_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_8_1));

		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_1_2));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_2_2));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_3_2));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_4_2));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_5_2));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_6_2));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_7_2));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_8_2));
	}
	public List<Bitmap> getFruits() {
		return fruits;
	}
}
