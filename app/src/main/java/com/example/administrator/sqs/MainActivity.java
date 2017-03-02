package com.example.administrator.sqs;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<Person> persons;
    PersonDao personDao2;
    BaseAdapter baseAdapter;
    Map<String,Boolean> radioStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioStates = new HashMap<String, Boolean>();
        personDao2 = new PersonDao(this);
        listView = (ListView)findViewById(R.id.listview);
        persons = personDao2.getAllPerson();
        initListView();
    }
    public void initListView(){
        baseAdapter = new BaseAdapter() {
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
                convertView = getLayoutInflater().inflate(R.layout.listview,null);
                TextView textViewname = (TextView)findViewById(R.id.text_name);
                TextView textViewsex = (TextView)findViewById(R.id.text_sex);
                TextView textViewcode = (TextView)findViewById(R.id.text_code);
                TextView textViewage = (TextView)findViewById(R.id.text_age);
                final Person person = (Person)getItem(position);
                textViewname.setText(person.getName());
                textViewage.setText(person.getAge()+"");
                textViewsex.setText(person.getSex());
                textViewcode.setText(person.getCode());
                RadioButton radioButton = (RadioButton)convertView.findViewById(R.id.radioButton);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        if (view instanceof RadioButton){
                            RadioButton radioButton = (RadioButton) view;
                            if (radioButton.isChecked()){
                                radioStates.put(String.valueOf(position),true);
                                for (String key:radioStates.keySet()){
                                    if (!key.equals(String.valueOf(position))){
                                        radioStates.put(key,false);
                                    }
                                }
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
                Boolean tempState = radioStates.get(String.valueOf(position));
                if (tempState!=null&&tempState){
                    radioButton.setChecked(false);
                    convertView.setBackgroundColor(Color.WHITE);
                }
                return convertView;
            }
        };
        listView.setAdapter(baseAdapter);
    }
    public void addPerson(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新增用户").create();
        View childView = getLayoutInflater().inflate(R.layout.alert,null);
        builder.setView(childView);
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int i){
                Field filed = null;
                try{
                    filed = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                    filed.setAccessible(true);
                    filed.set(dialogInterface,true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int i){
                try{
                   Field filed = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                    filed.setAccessible(true);
                    filed.set(dialogInterface,false);
                    AlertDialog ad;
                    if (dialogInterface instanceof AlertDialog){
                        ad = (AlertDialog)dialogInterface;
                        EditText editTextname = (EditText)ad.findViewById(R.id.edit_name);
                        EditText editTextcode = (EditText)ad.findViewById(R.id.edit_code);
                        EditText editTextage = (EditText)ad.findViewById(R.id.edit_age);
                        RadioGroup radioGroupSex = (RadioGroup)ad.findViewById(R.id.radioGroupSex);
                        if (TextUtils.isEmpty(editTextname.getText().toString())){
                            Toast.makeText(MainActivity.this,"姓名不能为空",Toast.LENGTH_SHORT);
                            return;
                        }
                        if (TextUtils.isEmpty(editTextcode.getText().toString())){
                            Toast.makeText(MainActivity.this,"编号不能为空",Toast.LENGTH_SHORT);
                            return;
                        }
                        if (TextUtils.isEmpty(editTextage.getText().toString())){
                            Toast.makeText(MainActivity.this,"年龄不能为空",Toast.LENGTH_SHORT);
                            return;
                        }
                        Person person= new Person();
                        person.setAge(Integer.parseInt(editTextage.getText().toString()));
                        person.setName(editTextname.getText().toString());
                        person.setCode(editTextname.getText().toString());
                        RadioButton radioButton = (RadioButton)radioGroupSex.findViewById(radioGroupSex.getCheckedRadioButtonId());
                        person.setSex(radioButton.getText().toString());
                        personDao2.addPerson(person);
                        persons = personDao2.getAllPerson();
                        radioStates = new HashMap<String, Boolean>();
                        baseAdapter.notifyDataSetChanged();
                        filed.set(dialogInterface,true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }
    public void deletePerson(View view){
        int position =-1;
        for (int i=0;i<listView.getChildCount();i++){
            View childView = listView.getChildAt(i);
            RadioButton radioButton = (RadioButton)childView.findViewById(R.id.radioButton);
            if (radioButton.isChecked()){
                position=i;
                break;
            }
        }
        if (position!=-1){
            Person person=persons.get(position);
            personDao2.deletePerson(person);
            persons=personDao2.getAllPerson();
            radioStates=new HashMap<String, Boolean>();
            baseAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this,"请选择要删除的行",Toast.LENGTH_SHORT);
        }
    }
}
