package com.synupassessment.remote;

import com.synupassessment.model.MenuList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APICALL {


    @GET("bins/19u0sf")
    Call<MenuList> getEmployeeList();

}
