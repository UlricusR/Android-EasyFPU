package info.rueth.fpucalculator.usecases;

import android.app.Application;

import info.rueth.fpucalculator.domain.model.Food;
import info.rueth.fpucalculator.domain.repository.FoodDataRepository;
import info.rueth.fpucalculator.presentation.viewmodels.FoodViewModel;

public class FoodUpdate implements FoodUseCase {
    private FoodDataRepository repository;

    public FoodUpdate(Application application) {
        repository = FoodDataRepository.getInstance(application);
    }

    public void execute(FoodViewModel viewModel) {
        int id = viewModel.getId();
        Food data = repository.getFoodByID(id);
        setFoodData(data, viewModel);
        repository.update(data);
    }

    private void setFoodData(Food data, FoodViewModel viewModel) {
        data.setName(viewModel.getName());
        data.setFavorite(viewModel.isFavorite());
        data.setCaloriesPer100g(viewModel.getCaloriesPer100g());
        data.setCarbsPer100g(viewModel.getCarbsPer100g());
        data.setAmount(viewModel.getAmount());
        data.setAmountSmall(viewModel.getAmountSmall());
        data.setAmountMedium(viewModel.getAmountMedium());
        data.setAmountLarge(viewModel.getAmountLarge());
        data.setCommentSmall(viewModel.getCommentSmall());
        data.setCommentMedium(viewModel.getCommentMedium());
        data.setCommentLarge(viewModel.getCommentLarge());
    }
}
