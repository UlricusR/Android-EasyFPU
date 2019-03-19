package info.rueth.fpucalculator.usecases;

import android.support.v7.widget.RecyclerView;

import java.io.IOException;

public interface AbsorptionSchemeUseCase {
    void execute(RecyclerView viewModel) throws IOException;
}
