package info.rueth.fpucalculator.usecases;

import java.io.IOException;

import androidx.recyclerview.widget.RecyclerView;

public interface AbsorptionSchemeUseCase {
    void execute(RecyclerView viewModel) throws IOException;
}
