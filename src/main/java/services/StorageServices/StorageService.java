package services.StorageServices;

import java.util.List;

public interface StorageService<T> {
    List<T> loadData();

    void saveData(List<T> data);
}
