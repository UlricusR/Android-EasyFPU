package info.rueth.fpucalculator.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;
import info.rueth.fpucalculator.usecases.AbsorptionBlockDelete;

public class AbsorptionSchemeAdapter extends RecyclerView.Adapter<AbsorptionSchemeAdapter.AbsorptionBlockViewHolder> {

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

    public AbsorptionSchemeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
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
                try {
                    // Delete absorption block from data model
                    new AbsorptionBlockDelete(mInflater.getContext()).execute(mAbsorptionBlocks.get(position));

                    // ... and in parallel from cache and view
                    mAbsorptionBlocks.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                } catch (IOException e) {
                    Toast.makeText(mInflater.getContext(), R.string.absorptionblock_cannotdelete, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setAbsorptionBlocks(List<AbsorptionBlockViewModel> absorptionBlocks) {
        this.mAbsorptionBlocks = absorptionBlocks;
        notifyDataSetChanged();
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
