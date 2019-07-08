package com.synupassessment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.synupassessment.CustomAdapter.CustomAdapter;
import com.synupassessment.model.MenuList;
import com.synupassessment.remote.APICALL;
import com.synupassessment.remote.RetroFitClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ListView listView;
    private CustomAdapter customAdapter;
    private MenuList menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        APICALL apicall = RetroFitClass.getAPIinstance();
        Call<MenuList> employeeListCall = apicall.getEmployeeList();
        employeeListCall.enqueue(new Callback<MenuList>() {
            @Override
            public void onResponse(Call<MenuList> call, Response<MenuList> response) {

                menuList = response.body();
                customAdapter = new CustomAdapter(MainActivity.this, menuList);
                listView.setAdapter(customAdapter);

            }

            @Override
            public void onFailure(Call<MenuList> call, Throwable t) {
                Log.e("okhttp", t.getMessage());
            }
        });

    }


}
