package ir.mehdi.kelid.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mehdi.kelid.R;
import ir.mehdi.kelid.ui.CardItemListAdapter;

/**
 * Created by Iman on 3/2/2017.
 */
public class ListItemFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int type;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_list_layout, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CardItemListAdapter();
        recyclerView.setAdapter(adapter);
        switch (type)
        {
            case 1:
                view.setBackgroundColor(Color.CYAN);
                break;
            case 2:
                view.setBackgroundColor(Color.GRAY);
                break;
            case 3:
                view.setBackgroundColor(Color.BLUE);
                break;
            case 4:
                view.setBackgroundColor(Color.YELLOW);
                break;

        }

        return view;
    }


    public void setType(int type) {
        this.type = type;
    }
}
