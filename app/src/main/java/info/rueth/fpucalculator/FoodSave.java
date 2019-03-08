package info.rueth.fpucalculator;

import android.app.Application;

public class FoodSave implements FoodUseCase {
    FoodDataRepository repository;

    public FoodSave(Application application) {
        repository = FoodDataRepository.getInstance(application);
    }

    public void execute(FoodViewModel viewModel) {
        Food data = getFoodData(viewModel);
        repository.insert(data);
    }

    Food getFoodData(FoodViewModel viewModel) {
        Food data = new Food();
        data.setName(viewModel.getName());
        data.setFavorite(viewModel.isFavorite());
        data.setCaloriesPer100g(viewModel.getCaloriesPer100g());
        data.setCarbsPer100g(viewModel.getCarbsPer100g());
        data.setAmountSmall(viewModel.getAmountSmall());
        data.setAmountMedium(viewModel.getAmountMedium());
        data.setAmountLarge(viewModel.getAmountLarge());
        data.setCommentSmall(viewModel.getCommentSmall());
        data.setCommentMedium(viewModel.getCommentMedium());
        data.setCommentLarge(viewModel.getCommentLarge());
        return data;
    }
}
