package com.example.fragmentex;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListContentFragment extends ListFragment {
    
    FragmentCoordinator coordinator;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] array = getResources().getStringArray(R.array.list_content);
        
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,array));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        coordinator = (FragmentCoordinator)activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        coordinator.onSetContentDetails(position);
    }
    
    
    
}
