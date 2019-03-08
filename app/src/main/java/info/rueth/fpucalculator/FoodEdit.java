package info.rueth.fpucalculator;

import android.app.Application;

public class FoodEdit implements FoodUseCase {
    FoodDataRepository repository;

    public FoodEdit(Application application) {
        repository = FoodDataRepository.getInstance(application);
    }

    public void execute(FoodViewModel viewModel) {
        String name = viewModel.getName();
        fillViewModelData(viewModel, name);
    }

    private void fillViewModelData(FoodViewModel viewModel, String name) {
        Food data = repository.getFoodByName(name);
        viewModel.setName(data.getName());
        viewModel.setFavorite(data.isFavorite());
        viewModel.setCaloriesPer100g(data.getCaloriesPer100g());
        viewModel.setCarbsPer100g(data.getCarbsPer100g());
        viewModel.setAmountSmall(data.getAmountSmall());
        viewModel.setAmountMedium(data.getAmountMedium());
        viewModel.setAmountLarge(data.getAmountLarge());
        viewModel.setCommentSmall(data.getCommentSmall());
        viewModel.setCommentMedium(data.getCommentMedium());
        viewModel.setCommentLarge(data.getCommentLarge());
    }
}
