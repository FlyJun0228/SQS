package com.example.administrator.sqs;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class MainActivity extends Activity implements View.OnClickListener {

    private Button mAddBtn;
    private Button mUpdateBtn;
    private Button mDelBtn;
    private ListView listView;
    private List<Person> persons;
    private PersonDao personDao2;
    private SqsAdapter sqsAdapter;
    private HashMap<String, Boolean> radioStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        mAddBtn = (Button) findViewById(R.id.add);
        mUpdateBtn = (Button) findViewById(R.id.update);
        mDelBtn = (Button) findViewById(R.id.delete);
        listView = (ListView) findViewById(R.id.listview);
    }

    private void initListener() {
        mAddBtn.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);
        mDelBtn.setOnClickListener(this);
        personDao2 = new PersonDao(this);
        persons = personDao2.getAllPerson();
        sqsAdapter = new SqsAdapter(this, radioStates, persons);
        listView.setAdapter(sqsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                addPerson();
                break;
            case R.id.update:
                // TODO
                break;
            case R.id.delete:
                deletePerson();
                break;
            default:
                break;
        }
    }

    private void addPerson() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新增用户").create();
        View childView = getLayoutInflater().inflate(R.layout.alert, null);
        builder.setView(childView);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Field filed = null;
                try {
                    filed = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                    filed.setAccessible(true);
                    filed.set(dialogInterface, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Field filed = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                    filed.setAccessible(true);
                    filed.set(dialogInterface, false);
                    AlertDialog ad;
                    if (dialogInterface instanceof AlertDialog) {
                        ad = (AlertDialog) dialogInterface;
                        EditText editTextname = (EditText) ad.findViewById(R.id.edit_name);
                        EditText editTextcode = (EditText) ad.findViewById(R.id.edit_code);
                        EditText editTextage = (EditText) ad.findViewById(R.id.edit_age);
                        RadioGroup radioGroupSex = (RadioGroup) ad.findViewById(R.id.radioGroupSex);
                        if (TextUtils.isEmpty(editTextname.getText().toString())) {
                            Toast.makeText(MainActivity.this, "姓名不能为空", Toast.LENGTH_SHORT);
                            return;
                        }
                        if (TextUtils.isEmpty(editTextcode.getText().toString())) {
                            Toast.makeText(MainActivity.this, "编号不能为空", Toast.LENGTH_SHORT);
                            return;
                        }
                        if (TextUtils.isEmpty(editTextage.getText().toString())) {
                            Toast.makeText(MainActivity.this, "年龄不能为空", Toast.LENGTH_SHORT);
                            return;
                        }
                        Person person = new Person();
                        person.setAge(Integer.parseInt(editTextage.getText().toString()));
                        person.setName(editTextname.getText().toString());
                        person.setCode(editTextname.getText().toString());
                        RadioButton radioButton = (RadioButton) radioGroupSex.findViewById(radioGroupSex.getCheckedRadioButtonId());
                        person.setSex(radioButton.getText().toString());
                        personDao2.addPerson(person);
                        persons = personDao2.getAllPerson();
                        radioStates = new HashMap<String, Boolean>();
                        sqsAdapter.notifyDataSetChanged();
                        filed.set(dialogInterface, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }

    private void deletePerson() {
        int position = -1;
        for (int i = 0; i < listView.getChildCount(); i++) {
            View childView = listView.getChildAt(i);
            RadioButton radioButton = (RadioButton) childView.findViewById(R.id.radioButton);
            if (radioButton.isChecked()) {
                position = i;
                break;
            }
        }
        if (position != -1) {
            Person person = persons.get(position);
            personDao2.deletePerson(person);
            persons = personDao2.getAllPerson();
            radioStates = new HashMap<String, Boolean>();
            sqsAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "请选择要删除的行", Toast.LENGTH_SHORT);
        }
    }

}
