package com.example.readcontact;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<UserContacts> {

    private final List<UserContacts> list;
    private final Activity context;
    private CheckBox checkAll;
    boolean checkAll_flag = false;
    boolean checkItem_flag = false;

    public MyAdapter(Activity context, List<UserContacts> list, CheckBox checkAll) {
        super(context, R.layout.list_item, list);
        this.context = context;
        this.list = list;
        this.checkAll = checkAll;
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton checkbox, boolean arg1) {
                if(!checkItem_flag){
                    checkAll_flag = true;
                    notifyDataSetChanged(); 
                }
            }
        });
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }

    private boolean areAllSelected() {

         boolean areAllSelected = false;

          for (int i = 0; i < list.size(); i++) {
              if(list.get(i).isSelected()){
                  areAllSelected = true;
              }
              else{
                  areAllSelected = false;
                  return areAllSelected;
              }
          }
          return areAllSelected;
        }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.lbName);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.cbContact);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            UserContacts element = (UserContacts) viewHolder.checkbox.getTag();
                            element.setSelected(buttonView.isChecked());

                            if(!checkAll_flag){
                                checkItem_flag = true;
                                if(buttonView.isChecked()){
                                    checkAll.setChecked(areAllSelected());
                                }
                                if(!buttonView.isChecked()){
                                    checkAll.setChecked(areAllSelected());                              
                                }
                                checkItem_flag = false;
                            }
                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        holder.checkbox.setChecked(list.get(position).isSelected());    

        if(checkAll_flag){
            if(checkAll.isChecked()){
                holder.checkbox.setChecked(true);
            }
            else if(!checkAll.isChecked()){
                holder.checkbox.setChecked(false);
            }
            if(position == (list.size() -1)){
                checkAll_flag = false;
            }
        }
        return view;
    }
}
