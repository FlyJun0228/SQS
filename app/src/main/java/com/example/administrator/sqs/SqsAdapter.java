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

    public static final class ViewHolder {
        public TextView mName;
        public TextView mSex;
        public TextView mAge;
        public TextView mCode;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview, null);
            holder = new ViewHolder();
            holder.mName = (TextView) convertView.findViewById(R.id.text_name);
            holder.mSex = (TextView) convertView.findViewById(R.id.text_sex);
            holder.mAge = (TextView) convertView.findViewById(R.id.text_code);
            holder.mCode = (TextView) convertView.findViewById(R.id.text_age);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mName.setText("姓名：" + persons.get(position).getName());
        holder.mSex.setText("年龄：" + persons.get(position).getAge());
        holder.mAge.setText("性别：" + persons.get(position).getSex());
        holder.mCode.setText("编号：" + persons.get(position).getCode());

        return convertView;
    }

}
