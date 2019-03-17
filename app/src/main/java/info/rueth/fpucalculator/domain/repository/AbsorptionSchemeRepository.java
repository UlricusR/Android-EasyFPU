package info.rueth.fpucalculator.domain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.rueth.fpucalculator.domain.model.AbsorptionBlock;
import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;

public class AbsorptionSchemeRepository {
    private AbsorptionSchemeLoader mAbsorptionSchemeLoader;
    private List<AbsorptionBlock> mAbsorptionBlocks;
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
        this.mAbsorptionBlocks = mAbsorptionSchemeLoader.load();
    }

    public List<AbsorptionBlock> getAbsorptionBlocks() {
        return this.mAbsorptionBlocks;
    }

    public LiveData<List<AbsorptionBlockViewModel>> getAbsorptionBlockViewModel() {
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

    public void delete(int maxFPU) throws IOException {
        for (AbsorptionBlock absorptionBlock : mAbsorptionBlocks) {
            if (absorptionBlock.getMaxFPU() == maxFPU) {
                mAbsorptionBlocks.remove(absorptionBlock);
                mAbsorptionSchemeLoader.save(mAbsorptionBlocks);
                return;
            }
        }
    }

    public LiveData<List<AbsorptionBlockViewModel>> reset() throws IOException {
        mAbsorptionBlocks = mAbsorptionSchemeLoader.reset();
        return getAbsorptionBlockViewModel();
    }
}
