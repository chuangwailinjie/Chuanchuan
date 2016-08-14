package space.peihao.xchuanchuan;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ItemView extends ImageView implements View.OnClickListener{
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */

    int height,weight;
    int resourceId;
    int itemID;
    ExplosionField efx;
    int resourcesID[]={R.drawable.x1,R.drawable.x2,R.drawable.x3,R.drawable.x4,R.drawable.x5,R.drawable.x6,R.drawable.x7,R.drawable.x8};
    //Item状态信息，state=0默认显示，state=1不可见
    int state;
    Random random=new Random();

    public ItemView(Context context,ExplosionField ef) {
        super(context);
        efx=ef;
        setResourceId();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId() {
        state=0;
        int id=random.nextInt(4);
        this.resourceId =id;
        this.setImageResource(resourcesID[id]);
        this.setVisibility(View.VISIBLE);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        efx.explode(this);
    }
}