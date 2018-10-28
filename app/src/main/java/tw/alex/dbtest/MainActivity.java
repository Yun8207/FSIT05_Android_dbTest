package tw.alex.dbtest;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private Resources res;
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();
        data = findViewById(R.id.data);

        myHelper = new MyHelper(this, "alex", null, 1);
        db = myHelper.getReadableDatabase();//check the difference between readable writeable

    }

    public void test1(View view) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(res.openRawResource(R.raw.alex)));

            String line;
            while((line= reader.readLine()) != null){
                Log.v("alex",line);
            }

            reader.close();
        }catch(Exception e){
            Log.v("alex", e.toString());
        }
    }

    public void test2(View view) {

        //Cursor cursor = db.query("cust",null,null,null,null,null,null);
        Cursor cursor = db.query("cust",new String[]{"id", "tel", "cname as name"}, "id > ?",new String[]{"4"},null,null,"cname desc");
        int count = cursor.getCount();
        data.setText("");
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);//cursor.getString(cursor.getColumenIndex("name");
            String tel = cursor.getString(2);
           // String birthday = cursor.getString(3);

            

            Log.v("alex", id +":"+ name + ":"+ tel);

            data.append(name + ":" + tel + ":" + "\n");
        }
        Log.v("alex", "Count: " + count);

    }

    public void test3(View view) {
        ContentValues values = new ContentValues();

        values.put("cname","alex");
        values.put("tel","123");
        values.put("birthday","1993-01-01");
        db.insert("cust",null, values);
        test2(null);
    }

    public void test4(View view) {
        //delete from cust where id = 3 and cname = 'brad'
        db.delete("cust","id = ? and cname = ?", new String[]{"3","alex"});

        test2(null);
    }


    public void test5(View view) {
        //update cust set cname='tony', tel='321', birthday='xxx' wher id = 7
        ContentValues values = new ContentValues();

        values.put("cname","eric");
        values.put("tel","321");
        values.put("birthday","1993-01-05");


        db.update("cust", values,"id = ? ", new String[]{"7"});

        test2(null);
    }
}
