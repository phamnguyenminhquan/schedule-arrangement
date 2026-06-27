package services.StorageServices;

import java.util.List;

import services.responses.Result;
import services.responses.ResultWithData;

public interface StorageService<T> {
    ResultWithData<List<T>> loadData();

    Result saveData(List<T> data);
}
