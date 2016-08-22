package space.peihao.xchuanchuan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ChuanchuanLayout extends RelativeLayout implements View.OnTouchListener{

    private int row=5;
    private int column=5;
    private int padding,margin;
    private ItemView items[];
    private int size[]=new int[4];
    private ExplosionField efx;
    private int score=0;
    private int addscore[]={10,35,75,150,300};
    //当第一次调用onMeasure时会...
    private boolean isOnce=false;

    private int height;

    Bitmap back1,back2;

    private ImageView tail,tail_x1;

    //callback接口设置
    private onChuanchaunListener chuanlistener;

    //list中保存屏幕中column*row个ItemView对象
    ArrayList<ItemView> list;

    //currentList保存当前叉子上的ItemView
    ArrayList<ItemView> currentList;

    public interface onChuanchaunListener{
        void onScorechange(int score,int length);
        void gameOver();
        void passGame();
    }


    public void setLayoutListener(onChuanchaunListener listener){
        this.chuanlistener=listener;
    }
    public ChuanchuanLayout(Context context) {
        this(context,null);
    }

    public ItemView[] getItems() {
        return items;
    }

    public ChuanchuanLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChuanchuanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                margin,
                getResources().getDisplayMetrics());
        padding = min(getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom());
        list=new ArrayList<>();
        currentList=new ArrayList<>();
        this.setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        this.height = wm.getDefaultDisplay().getHeight();
        if(!isOnce) {
            if (items == null) {
                items = new ItemView[column * row];
                //设置layout长宽
                size[0] = getMeasuredWidth();
                size[1] = (int) (height * 0.6);
                //设置item长宽
                size[2] = (size[0] - padding * 2 - (column - 1) * margin) / column;
                size[3] = (size[1] - padding * 2 - (row - 1) * margin) / row;


                for (int i = 0; i < items.length; i++) {
                    ItemView item = new ItemView(getContext(), efx);
                    items[i] = item;

                    //格局Id设置布局
                    item.setId(i + 1);
                    item.setScaleType(ImageView.ScaleType.FIT_XY);
                    RelativeLayout.LayoutParams layoutParams = new LayoutParams(size[2], size[3]);

                    if ((i + 1) % column != 0)//如果不是最后一列
                        layoutParams.rightMargin = margin;
                    if (i % column != 0) {//如果不是第一列 设置相对位置
                        layoutParams.addRule(RIGHT_OF, items[i - 1].getId());
                    }

                    if ((i + 1) > column) {//如果Item不在第一行，则设置margin、相对位置
                        layoutParams.topMargin = margin;
                        layoutParams.addRule(BELOW, items[i - column].getId());
                    }
                    addView(item, layoutParams);
                    list.add(item);
                }
            }
            if(tail==null){
                tail=new ImageView(getContext());
                tail.setImageResource(R.drawable.stick_tail);
                RelativeLayout.LayoutParams layoutParams = new LayoutParams(size[0], height);
                layoutParams.addRule(CENTER_HORIZONTAL);
                layoutParams.addRule(BELOW,items[0].getId());
                addView(tail,layoutParams);
            }
        }
        tail.setY((int)(0.6*height));
        isOnce=true;
        setMeasuredDimension(size[0],height);
    }

    int min(int...props){
        int mins=props[0];
        for(int item:props){
            if(item<mins)
                mins=item;
        }
        return mins;
    }


    public void bind(ExplosionField ef){
        this.efx=ef;
    }

    OnClickListener mylistener=new OnClickListener(){
        @Override
        public void onClick(View v) {
            efx.explode(v);
        }
    };

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float eventx=event.getX();
        float eventy=event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                tail.setY(eventy);
                tail.setX(eventx-tail.getMeasuredWidth()/2);
                break;
            case MotionEvent.ACTION_UP:
                tail.setY((int)(0.6*height));
                tail.setX((int)(0.5*getMeasuredWidth()-tail.getMeasuredWidth()/2));
                Log.e("xxxxx","*"+tail.getY());
                //将手指点击的区域对应的水果手动添加到cFruits
                int i=0;
                for(;i<list.size();i++){
                    ItemView iv=list.get(i);
                    float x=iv.getX();
                    float y=iv.getY();
                    if(eventx>x&&eventx<x+iv.getMeasuredWidth()&&eventy>y&&eventy<y+iv.getMeasuredHeight()) {
                        for(;i<column*row;i+=column){
                            if(list.get(i).getState()==0){
                                efx.explode(list.get(i));
                                list.get(i).setVisibility(View.INVISIBLE);
                                currentList.add(list.get(i));
                            }
                            list.get(i).setState(1);
                        }
                        break;
                    }
                }

                checkGame();
                chuanlistener.onScorechange(score,currentList.size());
                checkScore();
                break;
        }
        return true;
    }

    //检测游戏进度
    private void checkGame(){
        for(int i=currentList.size()-1;i>=2;i--){
            int chainLength=0;
            for(int j=i-1;j>=0;j--){
                if(currentList.get(i).getResourceId()==currentList.get(j).getResourceId()){
                    chainLength++;
                }else{
                    break;
                }
            }
            if(chainLength>1){
                score+=addscore[chainLength-2];
                for(int x=0;x<=chainLength;x++){
                    currentList.remove(i-x);
                }
                i=currentList.size();
            }
            else{
                continue;
            }
        }
    }

    //维持分数
    private void checkScore(){
        if(currentList.size()>=7){
            chuanlistener.gameOver();
        }
        boolean pass=false;
        for(ItemView item:list){
            if(item.getState()==0)
                pass=true;
        }
        if(!pass)
            chuanlistener.passGame();
    }

    public static Bitmap drawableToBitmap(Drawable drawable,int...props) {

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(props[2], props[3], config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(props[0], props[1], props[2], props[3]);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
