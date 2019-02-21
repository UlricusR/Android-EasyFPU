package info.rueth.fpucalculator;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private LiveData<List<Food>> allFood;

    public DataViewModel(Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        allFood = dataRepository.getAllFood();
    }

    LiveData<List<Food>> getAllFood() {
        return allFood;
    }

    public void insert(Food food) {
        dataRepository.insert(food);
    }

    public void delete(Food food) {
        dataRepository.delete(food);
    }
}
