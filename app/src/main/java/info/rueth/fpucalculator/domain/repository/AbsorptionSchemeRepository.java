package info.rueth.fpucalculator.domain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.rueth.fpucalculator.domain.model.AbsorptionBlock;
import info.rueth.fpucalculator.domain.model.AbsorptionScheme;
import info.rueth.fpucalculator.domain.model.AbsorptionSchemeException;
import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;

public class AbsorptionSchemeRepository {
    private AbsorptionSchemeLoader mAbsorptionSchemeLoader;
    private AbsorptionScheme mAbsorptionScheme;
    private static volatile AbsorptionSchemeRepository INSTANCE;

    public static AbsorptionSchemeRepository getInstance(Context context) throws IOException, AbsorptionSchemeException {
        if (INSTANCE == null) {
            synchronized (AbsorptionSchemeRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AbsorptionSchemeRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    private AbsorptionSchemeRepository(Context context) throws IOException, AbsorptionSchemeException {
        mAbsorptionSchemeLoader = new AbsorptionSchemeLoader(context);
        mAbsorptionScheme = mAbsorptionSchemeLoader.load();
    }

    public AbsorptionScheme getAbsorptionScheme() {
        return mAbsorptionScheme;
    }

    public List<AbsorptionBlock> getAbsorptionBlocks() {
        return mAbsorptionScheme.getAbsorptionBlocks();
    }

    public LiveData<List<AbsorptionBlockViewModel>> getAbsorptionBlockViewModel() {
        List<AbsorptionBlockViewModel> absorptionBlockVM = new ArrayList<>();
        AbsorptionBlockViewModel absorptionBlockViewModel;
        for (AbsorptionBlock item : getAbsorptionBlocks()) {
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

    public void delete(int maxFPU) throws IOException {
        if (mAbsorptionScheme.delete(maxFPU)) {
            // Removing was successful, so save new absorption scheme
            mAbsorptionSchemeLoader.save(mAbsorptionScheme);
        }
    }

    public boolean add(AbsorptionBlock absorptionBlock) {
        return mAbsorptionScheme.add(absorptionBlock);
    }

    public LiveData<List<AbsorptionBlockViewModel>> reset() throws IOException, AbsorptionSchemeException {
        mAbsorptionScheme = mAbsorptionSchemeLoader.reset();
        return getAbsorptionBlockViewModel();
    }
}
