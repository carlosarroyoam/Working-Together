package com.workingtogether.workingtogether.entity.dao.interfaces;

import java.util.List;

/**
 * Specifies all methods that should be implemented on dao entities classes
 *
 * @author Carlos Alberto Arroyo Martinez <carlosarroyoam@gmail.com>
 */
public interface DAOInterface<T> {
    List<T> getAll();

    T get(int id);

    T create(T t);

    boolean update(T t);

    boolean delete(T t);

    void closeDBHelper();
}
