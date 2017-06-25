package ir.mehdi.kelid.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ir.mehdi.kelid.R;

public class CardItemListAdapter extends RecyclerView.Adapter<CardItemListAdapter.MyViewHolder> {
    View.OnClickListener showInfoCLick;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public CardItemListAdapter(View.OnClickListener showInfoCLick) {
        this.showInfoCLick = showInfoCLick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_layout, parent, false);

//        view.setOnClickListener(Main2.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;
        imageView.setOnClickListener(showInfoCLick);

        textViewName.setText("11111111");
        textViewVersion.setText("111111111");
        imageView.setImageResource(R.drawable.a2);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}