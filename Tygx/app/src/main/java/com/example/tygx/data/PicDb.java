package com.example.tygx.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class PicDb {
    private SQLiteDatabase picDb;  //类的成员
    private PicDbHelper picDbHelper;  //类的成员

    public PicDb(Context context) {  //构造方法，参数为上下文对象
        //第1参数为上下文，第2参数为数据库名
        picDbHelper = new PicDbHelper(context,"pictures.db",null,1);
    }

    public Cursor allQuery(){    //查询所有记录
        picDb = picDbHelper.getReadableDatabase();
        return picDb.rawQuery("select * from Pictures",null);
    }

    public  int getPicturesNumber(){  //返回数据表记录数
        picDb = picDbHelper.getReadableDatabase();
        Cursor cursor= picDb.rawQuery("select * from Pictures",null);
        return cursor.getCount();
    }

    /*
    * 读取方法
    * byte[] in = cursor.getBlob(cursor.getColumnIndex("uimgae"));
    * Bitmap bit = BitmapFactory.decodeByteArray(in, 0, in.length);
    * */
    public long insertInfo(Bitmap bit){  //插入记录
        picDb = picDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, os);
        values.put("picture", os.toByteArray());
        long rowid=picDb.insert(PicDbHelper.TB_NAME, null, values);
        if(rowid==-1)
            Log.i("picDb", "数据插入失败！");
        else
            Log.i("picDb", "数据插入成功！"+rowid);
        return rowid;
    }

    public void deleteInfo(String selId){  //删除记录
        String where = "id=" + selId;
        int i = picDb.delete(PicDbHelper.TB_NAME, where, null);
        if (i > 0)
            Log.i("picDb", "数据删除成功！");
        else
            Log.i("picDb", "数据未删除！");
    }

    public void updateInfo(Bitmap bit,String selId){  //修改记录
        //方法中的第三参数用于修改选定的记录
        picDb = picDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, os);
        values.put("picture", os.toByteArray());
        String where="id="+selId;
        int i=picDb.update(PicDbHelper.TB_NAME, values, where, null);

        //上面几行代码的功能可以用下面的一行代码实现
        //myDb.execSQL("update friends set name = ? ,age = ? where id = ?",new Object[]{name,age,selId});

        if(i>0)
            Log.i("picDb","数据更新成功！");
        else
            Log.i("picDb","数据未更新！");
    }

}

