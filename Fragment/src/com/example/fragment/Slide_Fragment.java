package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Slide_Fragment extends Fragment {

	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.new_activity, container,false);

		Button button = (Button)view.findViewById(R.id.button1);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText edtName = (EditText)view.findViewById(R.id.edtName);
				EditText edtAge = (EditText)view.findViewById(R.id.edtAge);
				EditText edtComment = (EditText)view.findViewById(R.id.edtComment);
				
				TextView lbName = (TextView)view.findViewById(R.id.lbName);
				TextView lbAge = (TextView)view.findViewById(R.id.lbAge);
				TextView lbComment = (TextView)view.findViewById(R.id.lbComment);
				
				lbName.setText(edtName.getText().toString());
				lbAge.setText(edtAge.getText().toString());
				lbComment.setText(edtComment.getText().toString());
			}
		});
		
		return view;
	}

	
	
}
