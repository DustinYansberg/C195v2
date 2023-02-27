package DAO;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;

import java.sql.SQLException;

public interface DAO<T> {

    ObservableList<T> get() throws SQLException;

    T get(int id) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean insert(T t) throws SQLException;

    boolean update(T t) throws SQLException;
}



