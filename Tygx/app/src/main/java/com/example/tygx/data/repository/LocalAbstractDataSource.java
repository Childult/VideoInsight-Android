//package com.example.tygx.data.repository;
//
//import java.util.List;
//
//import io.reactivex.Completable;
//import io.reactivex.Flowable;
//import io.reactivex.Single;
//
//public class LocalAbstractDataSource implements AbstractDataSource {
//
//    private final AbstractsDao mAbstractsDao;
//
//    public LocalAbstractDataSource(AbstractsDao mAbstractsDao) {
//        this.mAbstractsDao = mAbstractsDao;
//    }
//
//    @Override
//    public Single<Abstract> loadByJobId(String jobId) {
//        return mAbstractsDao.loadByJobId(jobId);
//    }
//
//    @Override
//    public Flowable<Abstract> loadByJobIdFlow(String jobId) {
//        return mAbstractsDao.loadByJobIdFlow(jobId);
//    }
//
//    @Override
//    public Single<List<Abstract>> loadByType(String type){
//        return mAbstractsDao.loadByType(type);
//    }
//
//    @Override
//    public Flowable<List<Abstract>> loadByTypeFlow(String type){
//        return mAbstractsDao.loadByTypeFlow(type);
//    }
//
//    @Override
//    public Completable insert(Abstract mAbstract){
//        return mAbstractsDao.insert(mAbstract);
//    }
//
//    @Override
//    public Completable update(Abstract mAbstract){
//        return mAbstractsDao.update(mAbstract);
//    }
//
//    @Override
//    public Completable delete(Abstract mAbstract){
//        return mAbstractsDao.delete(mAbstract);
//    }
//}
