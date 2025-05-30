package com.mobile.tarefadiariaapplication.services;

import com.mobile.tarefadiariaapplication.models.Frase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FraseService {
    @GET("api/random")
    Call<List<Frase>> getFrases();
}
