
package com.example.fragmentex;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class DynamicActivity extends Activity implements FragmentCoordinator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        
        if(savedInstanceState == null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            ListContentFragment listContentFragment = new ListContentFragment();
            DetailsContentFragment detailsContentFragment = new DetailsContentFragment();
            transaction.add(R.id.listContainer, listContentFragment,"listContent");
            transaction.add(R.id.detailsContainer, detailsContentFragment,"detailsContent");
            transaction.commit();
        }
        
    }

    @Override
    public void onSetContentDetails(int index) {
        FragmentManager fragmentManager = getFragmentManager();
        
        DetailsContentFragment detailsContentFragment = (DetailsContentFragment)fragmentManager.findFragmentByTag("detailsContent");
        
        detailsContentFragment.setContentDetails(index);
    }

}
