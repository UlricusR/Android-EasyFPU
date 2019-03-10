package info.rueth.fpucalculator;

import android.app.Application;

public class FoodDelete implements FoodUseCase {
    private FoodDataRepository repository;

    FoodDelete(Application application) {
        repository = FoodDataRepository.getInstance(application);
    }

    public void execute(FoodViewModel viewModel) {
        int id = viewModel.getId();
        repository.delete(id);
    }
}
