package info.rueth.fpucalculator.usecases;

import java.io.IOException;

import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;

public interface AbsorptionBlockUseCase {
    void execute(AbsorptionBlockViewModel viewModel) throws IOException;
}
