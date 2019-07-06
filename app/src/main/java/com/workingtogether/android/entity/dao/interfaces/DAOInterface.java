package com.workingtogether.android.entity.dao.interfaces;

import java.util.List;

/**
 * Specifies all methods that should be implemented on dao entities classes
 *
 * @param <T> Object type
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public interface DAOInterface<T> {
    List<T> getAll();

    T get(int id);

    T create(T t);

    boolean update(T t);

    boolean delete(T t);

    void closeDBHelper();
}
