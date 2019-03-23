package info.rueth.fpucalculator.presentation.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;

public class AbsorptionBlockAdapter extends RecyclerView.Adapter<AbsorptionBlockAdapter.AbsorptionBlockViewHolder> {

    public class AbsorptionBlockViewHolder extends RecyclerView.ViewHolder {

        private final TextView fpuView;
        private final TextView absorptionTimeView;
        private final Button buttonRemove;

        public AbsorptionBlockViewHolder(@NonNull View itemView) {
            super(itemView);
            fpuView = itemView.findViewById(R.id.absorption_block_fpu);
            absorptionTimeView = itemView.findViewById(R.id.absorption_block_hours);
            buttonRemove = itemView.findViewById(R.id.button_absorptionblock_remove);
        }
    }

    private LayoutInflater mInflater;
    private List<AbsorptionBlockViewModel> mAbsorptionBlocks;
    private static final String MAX_FPU = "20";
    private static final String MAX_ABSORPTIONTIME = "10";
    private int maxFPUPreference;
    private int maxAbsorptionTimePreference;

    public AbsorptionBlockAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        // Get maximum FPU and absorption time from SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        maxFPUPreference = Integer.valueOf(prefs.getString(context.getString(R.string.preference_maxfpu_key), MAX_FPU));
        maxAbsorptionTimePreference = Integer.valueOf(prefs.getString(context.getString(R.string.preference_maxabsorptiontime_key), MAX_ABSORPTIONTIME));
    }
    
    @NonNull
    @Override
    public AbsorptionBlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create the absorption block item
        View itemView = mInflater.inflate(R.layout.absorption_block_item, parent, false);
        return new AbsorptionBlockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsorptionBlockViewHolder holder, int position) {
        if (mAbsorptionBlocks != null) {
            // Get the current absorption block
            AbsorptionBlockViewModel absorptionBlock = mAbsorptionBlocks.get(position);

            // Set FPUs and absorption time
            holder.fpuView.setText(String.valueOf(absorptionBlock.getMaxFPU()));
            holder.absorptionTimeView.setText(String.valueOf(absorptionBlock.getAbsorptionTime()));

            // Set onClickListener to remove button
            holder.buttonRemove.setOnClickListener(v -> {
                // Delete absorption block from cache and view, not yet from data repository
                // (this will only be done on save)
                mAbsorptionBlocks.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            });
        }
    }

    public void setAbsorptionBlocks(List<AbsorptionBlockViewModel> absorptionBlocks) {
        this.mAbsorptionBlocks = absorptionBlocks;
        notifyDataSetChanged();
    }

    public List<AbsorptionBlockViewModel> getAbsorptionBlocks() {
        return mAbsorptionBlocks;
    }

    public ArrayList<Integer> getFreeFPUValues() {
        ArrayList<Integer> freeValues = new ArrayList<>();

        // First create the full range from 1 to MAX
        for (int i = 1; i <= maxFPUPreference; i++) {
            freeValues.add(Integer.valueOf(i));
        }

        // Then remove existing FPUs
        for (AbsorptionBlockViewModel viewModel : mAbsorptionBlocks) {
            freeValues.remove(Integer.valueOf(viewModel.getMaxFPU()));
        }

        return freeValues;
    }

    public ArrayList<Integer> getFreeAbsorptionTimeValues() {
        ArrayList<Integer> freeValues = new ArrayList<>();

        // First create the full range from 1 to MAX
        for (int i = 1; i <= maxAbsorptionTimePreference; i++) {
            freeValues.add(Integer.valueOf(i));
        }

        // Then remove existing absorption times
        for (AbsorptionBlockViewModel viewModel : mAbsorptionBlocks) {
            freeValues.remove(Integer.valueOf(viewModel.getAbsorptionTime()));
        }

        return freeValues;
    }

    /**
     * Adds an absorption block
     * @param absorptionBlockNew
     * @return An error message if the absorption block could not be added, otherwise null
     */
    public String addAbsorptionBlock(AbsorptionBlockViewModel absorptionBlockNew) {
        // Check no. 1: If the list only has one element, then everything is fine, as the new block is the first one
        if (mAbsorptionBlocks.size() == 0) {
            mAbsorptionBlocks.add(absorptionBlockNew);
            notifyItemInserted(0);
            notifyItemRangeChanged(0, getItemCount());
            return null;
        }

        // Check no. 2: There are existing blocks, so we must check to not have identical maxFPU values
        for (AbsorptionBlockViewModel absorptionBlock : mAbsorptionBlocks) {
            if (absorptionBlock.getMaxFPU() == absorptionBlockNew.getMaxFPU()) {
                // Duplicate maxFPU values not allowed
                return mInflater.getContext().getString(R.string.err_newabsorptionblock_maxfpuidentical);
            }
        }

        // Now we're sure the new maxFPU is not identical, therefore we add new absorption block and sort
        mAbsorptionBlocks.add(absorptionBlockNew);
        Collections.sort(mAbsorptionBlocks, (o1, o2) -> o1.getMaxFPU() - o2.getMaxFPU());

        // Check no. 3: The absorption block before the new one must have a lower, the one after a higher absorption time
        int newBlockIndex = mAbsorptionBlocks.indexOf(absorptionBlockNew);

        // Case 3a: It's the first element, so just check the block after -
        // we have already excluded the case that the new block is the only element in check no. 1!
        if (newBlockIndex == 0) {
            if (absorptionBlockNew.getAbsorptionTime() >= mAbsorptionBlocks.get(1).getAbsorptionTime()) {
                // Error: The new block's absorption time is equals or larger than of the one after
                mAbsorptionBlocks.remove(absorptionBlockNew);
                return mInflater.getContext().getString(R.string.err_newabsorptionblock_absorptiontime);
            } else {
                notifyItemInserted(newBlockIndex);
                notifyItemRangeChanged(newBlockIndex, getItemCount());
                return null;
            }
        }

        // Case 3b: It's the last element, so just check the block before
        if (newBlockIndex == mAbsorptionBlocks.size() - 1) {
            if (absorptionBlockNew.getAbsorptionTime() <= mAbsorptionBlocks.get(mAbsorptionBlocks.size() - 2).getAbsorptionTime()) {
                // Error: The new block's absorption time is equals or less than of the one before
                mAbsorptionBlocks.remove(absorptionBlockNew);
                return mInflater.getContext().getString(R.string.err_newabsorptionblock_absorptiontime);
            } else {
                notifyItemInserted(newBlockIndex);
                notifyItemRangeChanged(newBlockIndex, getItemCount());
                return null;
            }
        }

        // Case 3c: It's somewhere in the middle
        if (!(absorptionBlockNew.getAbsorptionTime() > mAbsorptionBlocks.get(newBlockIndex - 1).getAbsorptionTime() &&
              absorptionBlockNew.getAbsorptionTime() < mAbsorptionBlocks.get(newBlockIndex + 1).getAbsorptionTime())) {
            mAbsorptionBlocks.remove(absorptionBlockNew);
            return mInflater.getContext().getString(R.string.err_newabsorptionblock_absorptiontime);
        } else {
            notifyItemInserted(newBlockIndex);
            notifyItemRangeChanged(newBlockIndex, getItemCount());
            return null;
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mAbsorptionBlocks has not been updated (means initially, it's null, and we can't return null)
    @Override
    public int getItemCount() {
        if (mAbsorptionBlocks != null)
            return mAbsorptionBlocks.size();
        else return 0;
    }
}
