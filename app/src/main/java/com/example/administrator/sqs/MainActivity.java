package com.example.administrator.sqs;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

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
                        EditText editTextName = (EditText) ad.findViewById(R.id.edit_name);
                        EditText editTextMajor = (EditText) ad.findViewById(R.id.edit_major);
                        EditText editTextGender = (EditText) ad.findViewById(R.id.edit_gender);
                        EditText editTextInterest = (EditText) ad.findViewById(R.id.edit_interest);
                        EditText editTextCollect = (EditText) ad.findViewById(R.id.edit_collect);
                        Person person = new Person();
                        person.setName(editTextName.getText().toString());
                        person.setMajor(editTextMajor.getText().toString());
                        person.setGender(editTextGender.getText().toString());
                        person.setInterest(editTextInterest.getText().toString());
                        person.setCollect(editTextCollect.getText().toString());
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
