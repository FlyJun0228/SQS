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
import android.widget.AdapterView;
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

public class MainActivity extends Activity implements View.OnClickListener, ListView.OnItemClickListener {

    private Button mAddBtn;
    private ListView listView;
    private List<Person> persons;
    private PersonDao personDao;
    private SqsAdapter sqsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        mAddBtn = (Button) findViewById(R.id.add);
        listView = (ListView) findViewById(R.id.listview);
    }

    private void initListener() {
        mAddBtn.setOnClickListener(this);
        personDao = new PersonDao(this);
        persons = personDao.getAllPerson();
        sqsAdapter = new SqsAdapter(this, persons);
        listView.setAdapter(sqsAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                addPerson();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO 点击每一条Item后弹出AlertDialog，可选择修改或者删除该条记录
        // Test
        Toast.makeText(getApplicationContext(), "小帅哥点击了Item！", Toast.LENGTH_SHORT).show();
    }

    private void addPerson() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新增用户").create();
        View childView = getLayoutInflater().inflate(R.layout.alert, null);
        builder.setView(childView);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (dialogInterface instanceof AlertDialog) {
                        AlertDialog ad = (AlertDialog) dialogInterface;
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
                        personDao.addPerson(person);
                        List<Person> newPersons;
                        // list新的引向
                        newPersons = personDao.getAllPerson();
                        persons.clear();
                        persons.addAll(newPersons);
                        sqsAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }

}
