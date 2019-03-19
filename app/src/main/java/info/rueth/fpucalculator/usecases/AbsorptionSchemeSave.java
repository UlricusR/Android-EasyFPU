package info.rueth.fpucalculator.usecases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import info.rueth.fpucalculator.domain.repository.AbsorptionSchemeRepository;
import info.rueth.fpucalculator.presentation.adapter.AbsorptionBlockAdapter;
import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;

public class AbsorptionSchemeSave implements AbsorptionSchemeUseCase {

    private AbsorptionSchemeRepository repository;

    public AbsorptionSchemeSave(Context context) throws IOException {
        repository = AbsorptionSchemeRepository.getInstance(context);
    }

    @Override
    public void execute(RecyclerView viewModel) throws IOException {
        // Get the values and set it to the absorption scheme
        AbsorptionBlockAdapter adapter = (AbsorptionBlockAdapter) viewModel.getAdapter();
        List<AbsorptionBlockViewModel> absorptionBlocks = adapter.getAbsorptionBlocks();
        repository.setAbsorptionBlockViewModel(absorptionBlocks);

        // Save the file
        repository.save();
    }
}
