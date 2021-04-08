package com.example.tygx.data.repository;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "abstracts", primaryKeys = "jobId", indices = {@Index(value = {"jobId"}, unique = true)})
public class Abstract {

    @NonNull
    private String jobId;

    @ColumnInfo(name = "result")
    public String result;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "message")
    public String message;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "keywords")
    public String keywords;

    public Abstract(String jobId, String result, String status, String type, String url, String message){
        this.jobId = jobId;
        this.result = result;
        this.status = status;
        this.type = type;
        this.url = url;
        this.message = message;
        this.title = "";
        this.keywords = "";
    }

    @Ignore
    public Abstract(String jobId, String url){
        this.jobId = jobId;
        this.result = "";
        this.status = "";
        this.type = "";
        this.url = url;
        this.message = "";
        this.title = "";
        this.keywords = "";
    }

    @Ignore
    public Abstract(String jobId, String url, String type){
        this.jobId = jobId;
        this.result = "";
        this.status = "";
        this.type = type;
        this.url = url;
        this.message = "";
        this.title = "";
        this.keywords = "";
    }

    @Ignore
    public Abstract(String jobId, String url, String type, String keywords){
        this.jobId = jobId;
        this.result = "";
        this.status = "";
        this.type = type;
        this.url = url;
        this.message = "";
        this.title = "";
        this.keywords = keywords;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId){
        this.jobId = jobId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result){
        this.result = result;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
