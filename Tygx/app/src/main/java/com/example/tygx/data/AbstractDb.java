package com.example.tygx.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AbstractDb {
    private SQLiteDatabase abstractDb;  //类的成员
    private AbstractDbHelper abstractDbHelper;  //类的成员

    public AbstractDb(Context context) {  //构造方法，参数为上下文对象
        //第1参数为上下文，第2参数为数据库名
        abstractDbHelper = new AbstractDbHelper(context,"abstracts.db",null,1);
    }

    public Cursor allQuery(){    //查询所有记录
        abstractDb = abstractDbHelper.getReadableDatabase();
        return abstractDb.rawQuery("select * from Abstracts",null);
    }

    public  int getAbstractsNumber(){  //返回数据表记录数
        abstractDb = abstractDbHelper.getReadableDatabase();
        Cursor cursor= abstractDb.rawQuery("select * from Abstracts",null);
        return cursor.getCount();
    }

    public void insertInfo(String url, String title, String duration, String abstracts, String pictures){  //插入记录
        abstractDb = abstractDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("url", url);
        values.put("title", title);
        values.put("duration", duration);
        values.put("abstract", abstracts);
        values.put("pictures", pictures);
        long rowid=abstractDb.insert(AbstractDbHelper.TB_NAME, null, values);
        if(rowid==-1)
            Log.i("abstractDb", "数据插入失败！");
        else
            Log.i("abstractDb", "数据插入成功！"+rowid);
    }

    public void deleteInfo(String selId){  //删除记录
        String where = "id=" + selId;
        int i = abstractDb.delete(AbstractDbHelper.TB_NAME, where, null);
        if (i > 0)
            Log.i("abstractDb", "数据删除成功！");
        else
            Log.i("abstractDb", "数据未删除！");
    }

    public void updateInfo(String url, String title, String duration, String abstracts, String pictures,String selId){  //修改记录
        //方法中的第三参数用于修改选定的记录
        ContentValues values = new ContentValues();
        values.put("url", url);
        values.put("title", title);
        values.put("duration", duration);
        values.put("abstract", abstracts);
        values.put("pictures", pictures);
        String where="id="+selId;
        int i=abstractDb.update(AbstractDbHelper.TB_NAME, values, where, null);

        //上面几行代码的功能可以用下面的一行代码实现
        //myDb.execSQL("update friends set name = ? ,age = ? where id = ?",new Object[]{name,age,selId});

        if(i>0)
            Log.i("abstractDb","数据更新成功！");
        else
            Log.i("abstractDb","数据未更新！");
    }

}

