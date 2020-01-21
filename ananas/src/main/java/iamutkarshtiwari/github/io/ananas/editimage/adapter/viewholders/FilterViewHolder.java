package iamutkarshtiwari.github.io.ananas.editimage.adapter.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import iamutkarshtiwari.github.io.ananas.R;

public class FilterViewHolder extends RecyclerView.ViewHolder {


    public ImageView icon;
    public TextView text;

    public FilterViewHolder(@NonNull View itemView) {
        super(itemView);

        icon = itemView.findViewById(R.id.filter_image);
        text = itemView.findViewById(R.id.filter_name);
    }
}