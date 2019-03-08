package info.rueth.fpucalculator;

import android.app.Application;

public class FoodUpdate implements FoodUseCase {
    FoodDataRepository repository;

    public FoodUpdate(Application application) {
        repository = FoodDataRepository.getInstance(application);
    }

    public void execute(FoodViewModel viewModel) {
        String name = viewModel.getName();
        Food data = repository.getFoodByName(name);
        setFoodData(data, viewModel);
        repository.update(data);
    }

    private void setFoodData(Food data, FoodViewModel viewModel) {
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
    }
}
