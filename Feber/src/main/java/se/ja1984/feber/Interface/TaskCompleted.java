package se.ja1984.feber.Interface;

/**
 * Created by jonathan on 2014-05-16.
 */
public interface TaskCompleted<T> {
    void onTaskComplete(T result);
}
