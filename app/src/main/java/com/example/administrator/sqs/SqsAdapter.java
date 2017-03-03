package com.example.administrator.sqs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Ivor
 * Date: 2017.03.03
 */

public class SqsAdapter extends BaseAdapter {

    private Context context;
    private List<Person> persons;
    private Map<String, Boolean> radioStates;
    private LayoutInflater mInflater;

    public SqsAdapter(Context context, HashMap<String, Boolean> radioStates, List<Person> persons) {
        this.context = context;
        this.persons = persons;
        this.radioStates = radioStates;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.listview, null);
        TextView textViewname = (TextView) convertView.findViewById(R.id.text_name);
        TextView textViewsex = (TextView) convertView.findViewById(R.id.text_sex);
        TextView textViewcode = (TextView) convertView.findViewById(R.id.text_code);
        TextView textViewage = (TextView) convertView.findViewById(R.id.text_age);
        final Person person = (Person) getItem(position);
        textViewname.setText(person.getName());
        textViewage.setText(person.getAge() + "");
        textViewsex.setText(person.getSex());
        textViewcode.setText(person.getCode());
        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radioButton);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) view;
                    if (radioButton.isChecked()) {
                        radioStates.put(String.valueOf(position), true);
                        for (String key : radioStates.keySet()) {
                            if (!key.equals(String.valueOf(position))) {
                                radioStates.put(key, false);
                            }
                        }
                        notifyDataSetChanged();
                    }
                }
            }
        });
        Boolean tempState = radioStates.get(String.valueOf(position));
        if (tempState != null && tempState) {
            radioButton.setChecked(false);
            convertView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

}
