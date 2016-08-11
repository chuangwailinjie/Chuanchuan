package space.peihao.xchuanchuan;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

    public ItemView(Context context,ExplosionField ef) {
        super(context);
        efx=ef;
        //this.setOnClickListener(this);
    }



    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        efx.explode(this);
        Toast.makeText(getContext(),"X:"+getX()+"Y:"+getY(),Toast.LENGTH_SHORT).show();
    }
}