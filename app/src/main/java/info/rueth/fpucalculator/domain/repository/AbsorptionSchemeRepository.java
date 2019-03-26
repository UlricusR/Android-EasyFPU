package info.rueth.fpucalculator.domain.repository;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import info.rueth.fpucalculator.domain.model.AbsorptionBlock;
import info.rueth.fpucalculator.domain.model.AbsorptionScheme;
import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;

/**
 * Holds the currently valid absorption scheme and interacts with the AbsorptionSchemeLoader
 */
public class AbsorptionSchemeRepository {
    private AbsorptionSchemeLoader mAbsorptionSchemeLoader;
    private AbsorptionScheme mAbsorptionScheme;
    private static volatile AbsorptionSchemeRepository INSTANCE;

    /**
     * Singleton implementation to retrieve the absorption scheme
     * @param context The context it is called from
     * @return An instance of the AbsorptionSchemeRepository
     * @throws IOException In case the absorption scheme could not be loaded
     */
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

    /**
     * @return The absorption scheme
     */
    public AbsorptionScheme getAbsorptionScheme() {
        return mAbsorptionScheme;
    }

    /**
     * @return The view model of all absorption blocks as LiveData List
     */
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

    /**
     * Sets a new list of absorption blocks, completely replaces the existing ones
     * @param absorptionBlockVM The list of absorption blocks to set
     */
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

    /**
     * Persists the cached absorption blocks
     * @throws IOException In case the absorption blocks could not be saved
     */
    public void save() throws IOException {
        mAbsorptionSchemeLoader.save(mAbsorptionScheme);
    }

    public LiveData<List<AbsorptionBlockViewModel>> reset() throws IOException {
        mAbsorptionScheme = mAbsorptionSchemeLoader.loadDefault();
        save();
        return getAbsorptionBlockViewModel();
    }
}
