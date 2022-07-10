package com.aurelio.filmespopulares.ui.listafilmes;

import com.aurelio.filmespopulares.BR;
import com.aurelio.filmespopulares.mapper.FilmeMapper;
import com.aurelio.filmespopulares.modal.Filme;
import com.aurelio.filmespopulares.network.ApiService;
import com.aurelio.filmespopulares.network.response.FilmesResult;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFilmesViewModel extends ViewModel {

    public ListaFilmesViewModel() {
        filmesLiveData = new MutableLiveData<>();
        init();
    }

    MutableLiveData<List<Filme>> filmesLiveData;
    List<Filme> filmesArrayList;

    public MutableLiveData<List<Filme>> getFilmesLiveData() {
        return filmesLiveData;
    }

    public void init(){
        obtemFilmes();
    }

    public void obtemFilmes() {
        ApiService.getInstance()
                .obterFilmesPopulares("2df3158d4c725c7dd0862d66223cab82")
                .enqueue(new Callback<FilmesResult>() {
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                        if (response.isSuccessful()) {
                            filmesArrayList = FilmeMapper
                                    .deResponseParaDominio(response.body().getResultadoFilmes());
                            filmesLiveData.setValue(filmesArrayList);
                        } else {
                            filmesLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) {
                        filmesLiveData.setValue(null);
                    }
                });
    }

}
