
package com.example.fragmentex;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class StaticsActivity extends Activity implements FragmentCoordinator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statics);
    }

    @Override
    public void onSetContentDetails(int index) {
        FragmentManager fragmentManager = getFragmentManager();

        DetailsContentFragment detailsContentFragment = (DetailsContentFragment) fragmentManager
                .findFragmentById(R.id.detailsContent);

        detailsContentFragment.setContentDetails(index);
    }

}
