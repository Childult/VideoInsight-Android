package com.example.tygx.data.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface AbstractsDao {

//    @Query("SELECT * FROM abstracts WHERE jobId = :jobId LIMIT 1")
//    public Single<Abstract> loadByJobId(String jobId);
//
//    @Query("SELECT * FROM abstracts WHERE jobId = :jobId LIMIT 1")
//    public Flowable<Abstract> loadByJobIdFlow(String jobId);
//
//    @Query("SELECT * FROM abstracts WHERE type = :type")
//    public Single<List<Abstract>> loadByType(String type);
//
//    @Query("SELECT * FROM abstracts WHERE type = :type")
//    public Flowable<List<Abstract>> loadByTypeFlow(String type);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public Completable insert(Abstract mAbstract);
//
//    @Update
//    public Completable update(Abstract mAbstract);
//
//    @Delete()
//    public Completable delete(Abstract mAbstract);

    @Query("SELECT * FROM abstracts WHERE jobId = :jobId LIMIT 1")
    public Abstract loadByJobId(String jobId);

    @Query("SELECT * FROM abstracts WHERE type = :type")
    public List<Abstract> loadByType(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Abstract mAbstract);

    @Update
    public void update(Abstract mAbstract);

    @Delete()
    public void delete(Abstract mAbstract);
}
