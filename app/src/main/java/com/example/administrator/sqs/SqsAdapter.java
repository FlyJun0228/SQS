package com.example.administrator.sqs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * User: Ivor
 * Date: 2017.03.03
 */

public class SqsAdapter extends BaseAdapter {

    private Context context;
    private List<Person> persons;
    private LayoutInflater mInflater;

    public SqsAdapter(Context context, List<Person> persons) {
        this.context = context;
        this.persons = persons;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(persons != null) {
            return persons.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static final class ViewHolder {
        public TextView mName;
        public TextView mMajor;
        public TextView mGender;
        public TextView mInterest;
        public TextView mCollect;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview, null);
            holder = new ViewHolder();
            holder.mName = (TextView) convertView.findViewById(R.id.text_name);
            holder.mMajor = (TextView) convertView.findViewById(R.id.text_major);
            holder.mGender = (TextView) convertView.findViewById(R.id.text_gender);
            holder.mInterest = (TextView) convertView.findViewById(R.id.text_interest);
            holder.mCollect = (TextView) convertView.findViewById(R.id.text_collect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mName.setText(persons.get(position).getName());
        holder.mMajor.setText(persons.get(position).getMajor());
        holder.mGender.setText(persons.get(position).getGender());
        holder.mInterest.setText(persons.get(position).getInterest());
        holder.mCollect.setText(persons.get(position).getCollect());

        return convertView;
    }

}
