package com.workingtogether.android.entity.dao;

import java.util.List;

/**
 * Specifies all methods that should be implemented on dao entities classes
 *
 * @param <T> the object type of entities classes
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public interface Dao<T> {

    List<T> getAll();

    T get(int id);

    T create(T t);

    boolean update(T t);

    boolean delete(T t);

	void closeDatabaseHelper();

}
