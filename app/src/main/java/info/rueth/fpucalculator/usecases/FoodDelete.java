package info.rueth.fpucalculator.usecases;

import android.app.Application;

import info.rueth.fpucalculator.domain.repository.FoodDataRepository;
import info.rueth.fpucalculator.presentation.viewmodels.FoodViewModel;

public class FoodDelete implements FoodUseCase {
    private FoodDataRepository repository;

    public FoodDelete(Application application) {
        repository = FoodDataRepository.getInstance(application);
    }

    public void execute(FoodViewModel viewModel) {
        int id = viewModel.getId();
        repository.delete(id);
    }
}
