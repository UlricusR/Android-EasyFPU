package info.rueth.fpucalculator.domain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.rueth.fpucalculator.domain.model.AbsorptionBlock;
import info.rueth.fpucalculator.domain.model.AbsorptionScheme;
import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;

public class AbsorptionSchemeRepository {
    private AbsorptionSchemeLoader mAbsorptionSchemeLoader;
    private AbsorptionScheme mAbsorptionScheme;
    private static volatile AbsorptionSchemeRepository INSTANCE;

    public static AbsorptionSchemeRepository getInstance(Context context) throws IOException {
        if (INSTANCE == null) {
            synchronized (AbsorptionSchemeRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AbsorptionSchemeRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    private AbsorptionSchemeRepository(Context context) throws IOException {
        mAbsorptionSchemeLoader = new AbsorptionSchemeLoader(context);
        mAbsorptionScheme = mAbsorptionSchemeLoader.load();
    }

    public AbsorptionScheme getAbsorptionScheme() {
        return mAbsorptionScheme;
    }

    public LiveData<List<AbsorptionBlockViewModel>> getAbsorptionBlockViewModel() {
        List<AbsorptionBlockViewModel> absorptionBlockVM = new ArrayList<>();
        AbsorptionBlockViewModel absorptionBlockViewModel;
        for (AbsorptionBlock item : mAbsorptionScheme.getAbsorptionBlocks()) {
            absorptionBlockViewModel = createViewModel(item);
            absorptionBlockVM.add(absorptionBlockViewModel);
        }

        MutableLiveData<List<AbsorptionBlockViewModel>> liveData = new MutableLiveData<>();
        liveData.setValue(absorptionBlockVM);
        return liveData;
    }

    private AbsorptionBlockViewModel createViewModel(AbsorptionBlock absorptionBlock) {
        return new AbsorptionBlockViewModel(absorptionBlock.getMaxFPU(), absorptionBlock.getAbsorptionTime());
    }

    public void setAbsorptionBlockViewModel(List<AbsorptionBlockViewModel> absorptionBlockVM) {
        List<AbsorptionBlock> absorptionBlocks = new ArrayList<>();
        for (AbsorptionBlockViewModel viewModel : absorptionBlockVM) {
            absorptionBlocks.add(createAbsorptionBlock(viewModel));
        }
        mAbsorptionScheme.setAbsorptionBlocks(absorptionBlocks);
    }

    private AbsorptionBlock createAbsorptionBlock(AbsorptionBlockViewModel absorptionBlockVM) {
        return new AbsorptionBlock(absorptionBlockVM.getMaxFPU(), absorptionBlockVM.getAbsorptionTime());
    }

    public void save() throws IOException {
        mAbsorptionSchemeLoader.save(mAbsorptionScheme);
    }

    public LiveData<List<AbsorptionBlockViewModel>> reset() throws IOException {
        mAbsorptionScheme = mAbsorptionSchemeLoader.loadDefault();
        save();
        return getAbsorptionBlockViewModel();
    }
}
