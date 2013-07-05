
package com.example.fragmentex;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsContentFragment extends Fragment {
    
    TextView lbMess;
    String[] array;
    int saveIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.details_content_fragment, container,false);
        array = getResources().getStringArray(R.array.list_details);
        lbMess = (TextView)v.findViewById(R.id.lbDetails);
        
        int currentIndex = savedInstanceState == null ? 0 : savedInstanceState.getInt("indexContent",0);
        setContentDetails(currentIndex);
        
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("indexContent", saveIndex);
    }

    public void setContentDetails(int index) {
        saveIndex = index;
        lbMess.setText(array[saveIndex]);
    }
}
