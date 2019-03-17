package info.rueth.fpucalculator.usecases;

import android.content.Context;

import java.io.IOException;

import info.rueth.fpucalculator.domain.repository.AbsorptionSchemeRepository;
import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;

public class AbsorptionBlockDelete implements AbsorptionBlockUseCase {
    private AbsorptionSchemeRepository repository;

    public AbsorptionBlockDelete(Context context) throws IOException {
        repository = AbsorptionSchemeRepository.getInstance(context);
    }

    public void execute(AbsorptionBlockViewModel viewModel) throws IOException {
        int maxFPU = viewModel.getMaxFPU();
        repository.delete(maxFPU);
    }
}
