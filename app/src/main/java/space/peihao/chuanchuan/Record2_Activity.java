package space.peihao.chuanchuan;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class Record2_Activity extends Activity {

    private ListView list;
    private DbHelper dbhelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_record2_);

        list=(ListView)findViewById(R.id.list);

        dbhelper=new DbHelper(this,"record1",null,1);
        db=dbhelper.getReadableDatabase();
        Cursor c=db.query("tb_record1", new String[]{"_id", "score"}, null, null, null, null, "score desc", "6");
        SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,R.layout.list,
                c,new String[]{"score"},new int[]{R.id.score});

        this.list.setAdapter(adapter);
    }
}
