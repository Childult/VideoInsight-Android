package com.example.tygx.data.repository;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Access point for managing abstracts data.
 */
public interface AbstractDataSource {

    /**
     * Gets the abstract from the data source by jobId.
     *
     * @return the abstract from the data source.
     */
    Single<Abstract> loadByJobId(String jobId);
    Flowable<Abstract> loadByJobIdFlow(String jobId);

    /**
     * Gets the abstracts from the data source by type.
     *
     * @return the abstracts from the data source.
     */
    Single<List<Abstract>> loadByType(String type);
    Flowable<List<Abstract>> loadByTypeFlow(String type);

    /**
     * Inserts the abstract into the data source, or, if this is an existing abstract, updates it.
     *
     * @param mAbstract the abstract to be inserted or updated.
     */
//    Completable insert(Abstract mAbstract);
    Completable update(Abstract mAbstract);

    /**
     * Deletes an abstract from the data source.
     */
    Completable delete(Abstract mAbstract);
}

