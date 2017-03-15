package com.example.administrator.sqs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mInput;
    private Button mRecommend;
    private Button mViewAll;
    private ListView listView;
    private PersonDao personDao;
    private SqsAdapter sqsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initListener();
    }

    private void initView() {
        mInput = (EditText) findViewById(R.id.et_search);
        mRecommend = (Button) findViewById(R.id.btn_recommend);
        mViewAll = (Button) findViewById(R.id.btn_viewall);
        listView = (ListView) findViewById(R.id.listview);
    }

    private void initListener() {
        mRecommend.setOnClickListener(this);
        mViewAll.setOnClickListener(this);
        personDao = new PersonDao(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recommend:
                if(mInput.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请输入查询内容！", Toast.LENGTH_SHORT).show();
                } else {
                    List<Person> newPersons = new ArrayList<Person>();
                    for(int i = 0; i < personDao.getAllPerson().size(); i++) {
                        if(mInput.getText().toString().equals(personDao.getAllPerson().get(i).getName()) || mInput.getText().toString().equals(personDao.getAllPerson().get(i).getInterest())) {
                            newPersons.add(personDao.getAllPerson().get(i));
                        }
                    }
                    Log.e("11", newPersons.size() + "");
                    sqsAdapter = new SqsAdapter(this, newPersons);
                    listView.setAdapter(sqsAdapter);
                }
                break;
            case R.id.btn_viewall:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
