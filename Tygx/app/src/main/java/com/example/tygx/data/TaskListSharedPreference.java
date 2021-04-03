package com.example.tygx.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;


public class TaskListSharedPreference {
    public static void saveTaskListInfo(Context context, Set<String> unreadTaskSet, Set<String> readTaskSet) {
        /**
         * SharedPreferences将用户的数据存储到该包下的shared_prefs/taskList.xml文件中，
         * 并且设置该文件的读取方式为私有，即只有该软件自身可以访问该文件
         */
        SharedPreferences sPreferences = context.getSharedPreferences("taskList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPreferences.edit();
        //当然sharepreference会对一些特殊的字符进行转义，使得读取的时候更加准确
        editor.putStringSet("unreadTaskSet", unreadTaskSet);
        editor.putStringSet("readTaskSet", readTaskSet);
        editor.putString("unreadNums",String.valueOf(unreadTaskSet.size()));
        editor.putString("readNums",String.valueOf(readTaskSet.size()));
        //用apply方法将数据写入文件
        editor.apply();
    }

    public static void addUnreadTask(Context context, String unreadTask){
        SharedPreferences sPreferences = context.getSharedPreferences("taskList", Context.MODE_PRIVATE);
        Set<String> unreadTaskSet;
        Set<String> readTaskSet;
        int unreadNums;
        int readNums;
        if(sPreferences.contains("unreadTaskSet")) {
            unreadTaskSet = sPreferences.getStringSet("unreadTaskSet", new HashSet<String>());
            unreadNums = unreadTaskSet.size();
        }else {
            unreadTaskSet = new HashSet<>();
            unreadNums = 0;
        }
        if(sPreferences.contains("readTaskSet")) {
            readTaskSet = sPreferences.getStringSet("readTaskSet", new HashSet<String>());
            readNums = readTaskSet.size();
        }else {
            readTaskSet = new HashSet<>();
            readNums = 0;
        }
        unreadTaskSet.add(unreadTask);
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putStringSet("unreadTaskSet", unreadTaskSet);
        editor.putStringSet("readTaskSet", readTaskSet);
        editor.putString("unreadNums",String.valueOf(unreadNums));
        editor.putString("readNums",String.valueOf(readNums));
        //用apply方法将数据写入文件
        editor.apply();
    }

    public static void changeTaskToREAD(Context context, String readTask){
        SharedPreferences sPreferences = context.getSharedPreferences("taskList", Context.MODE_PRIVATE);
        Set<String> unreadTaskSet;
        Set<String> readTaskSet;
        int unreadNums;
        int readNums;
        if(sPreferences.contains("unreadTaskSet")) {
            unreadTaskSet = sPreferences.getStringSet("unreadTaskSet", new HashSet<String>());
            unreadNums = unreadTaskSet.size();
        }else {
            unreadTaskSet = new HashSet<>();
            unreadNums = 0;
        }
        if(sPreferences.contains("readTaskSet")) {
            readTaskSet = sPreferences.getStringSet("readTaskSet", new HashSet<String>());
            readNums = readTaskSet.size();
        }else {
            readTaskSet = new HashSet<>();
            readNums = 0;
        }
        unreadTaskSet.remove(readTask);
        if(readTaskSet.contains(readTask)){
            readTaskSet.remove(readTask);
        }
        readTaskSet.add(readTask);
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putStringSet("unreadTaskSet", unreadTaskSet);
        editor.putStringSet("readTaskSet", readTaskSet);
        editor.putString("unreadNums",String.valueOf(unreadNums));
        editor.putString("readNums",String.valueOf(readNums));
        //用apply方法将数据写入文件
        editor.apply();
    }
}
