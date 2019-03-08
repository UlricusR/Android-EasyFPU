package info.rueth.fpucalculator;

import android.app.Application;

public class FoodDelete implements FoodUseCase {
    FoodDataRepository repository;

    public FoodDelete(Application application) {
        repository = FoodDataRepository.getInstance(application);
    }

    public void execute(FoodViewModel viewModel) {
        String name = viewModel.getName();
        repository.delete(name);
    }
}
