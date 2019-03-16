package info.rueth.fpucalculator;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AbsorptionSchemeRepository {
    private AbsorptionSchemeLoader mAbsorptionSchemeLoader;
    private List<AbsorptionBlock> mAbsorptionBlocks;
    private static volatile AbsorptionSchemeRepository INSTANCE;

    static AbsorptionSchemeRepository getInstance(Application application) throws IOException {
        if (INSTANCE == null) {
            synchronized (AbsorptionSchemeRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AbsorptionSchemeRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    private AbsorptionSchemeRepository(Application application) throws IOException {
        mAbsorptionSchemeLoader = new AbsorptionSchemeLoader(application.getBaseContext());
        this.mAbsorptionBlocks = mAbsorptionSchemeLoader.load();
    }

    List<AbsorptionBlock> getAbsorptionBlocks() {
        return this.mAbsorptionBlocks;
    }

    LiveData<List<AbsorptionBlockViewModel>> getAbsorptionBlockViewModel() {
        List<AbsorptionBlockViewModel> absorptionBlockVM = new ArrayList<>();
        AbsorptionBlockViewModel absorptionBlockViewModel;
        for (AbsorptionBlock item : mAbsorptionBlocks) {
            absorptionBlockViewModel = createViewModel(item);
            absorptionBlockVM.add(absorptionBlockViewModel);
        }

        MutableLiveData<List<AbsorptionBlockViewModel>> liveData = new MutableLiveData<>();
        liveData.setValue(absorptionBlockVM);
        return liveData;
    }

    private AbsorptionBlockViewModel createViewModel(AbsorptionBlock absorptionBlock) {
        AbsorptionBlockViewModel viewModel = new AbsorptionBlockViewModel(absorptionBlock.getMaxFPU(), absorptionBlock.getAbsorptionTime());
        return viewModel;
    }
}
