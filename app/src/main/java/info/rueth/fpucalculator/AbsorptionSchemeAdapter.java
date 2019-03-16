package info.rueth.fpucalculator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AbsorptionSchemeAdapter extends RecyclerView.Adapter<AbsorptionSchemeAdapter.AbsorptionBlockViewHolder> {

    class AbsorptionBlockViewHolder extends RecyclerView.ViewHolder {

        private final TextView fpuView;
        private final TextView absorptionTimeView;

        public AbsorptionBlockViewHolder(@NonNull View itemView) {
            super(itemView);
            fpuView = itemView.findViewById(R.id.absorption_block_fpu);
            absorptionTimeView = itemView.findViewById(R.id.absorption_block_hours);
        }
    }

    private LayoutInflater mInflater;
    private List<AbsorptionBlockViewModel> mAbsorptionBlocks;

    AbsorptionSchemeAdapter(Context context) {
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
            holder.fpuView.setText(absorptionBlock.getMaxFPU());
            holder.absorptionTimeView.setText(absorptionBlock.getAbsorptionTime());
        }
    }

    void setAbsorptionBlocks(List<AbsorptionBlockViewModel> absorptionBlocks) {
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
