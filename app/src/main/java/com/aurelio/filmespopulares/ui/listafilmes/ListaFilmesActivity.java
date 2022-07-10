package com.aurelio.filmespopulares.ui.listafilmes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aurelio.filmespopulares.BR;
import com.aurelio.filmespopulares.R;
import com.aurelio.filmespopulares.databinding.ActivityListarFilmesBinding;
import com.aurelio.filmespopulares.modal.Filme;
import com.aurelio.filmespopulares.ui.detalhesfilme.DetalhesFilmeActivity;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ListaFilmesActivity extends AppCompatActivity implements ListaFilmesAdapter.ItemFilmeClickListener {

    private ListaFilmesViewModel viewModel;
    private ListaFilmesAdapter filmesAdapter;
    private ActivityListarFilmesBinding activityListarFilmesBinding;

    Observer<List<Filme>> userListUpdateObserver = new Observer<List<Filme>>() {
        @Override
        public void onChanged(List<Filme> userArrayList) {
            if(userArrayList == null){
                mostraErro();
            } else {
                filmesAdapter.setFilmes(userArrayList);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityListarFilmesBinding = ActivityListarFilmesBinding.inflate(getLayoutInflater());
        setContentView(activityListarFilmesBinding.getRoot());

        configuraAdapter();
        viewModel = ViewModelProviders.of(this).get(ListaFilmesViewModel.class);
        viewModel.getFilmesLiveData().observe(this, userListUpdateObserver);
        configuraToolbar();
    }

    private void configuraToolbar() {
        Toolbar toolbar = activityListarFilmesBinding.toolbar;
        setSupportActionBar(toolbar);
    }

    private void configuraAdapter() {
        final RecyclerView recyclerFilmes = activityListarFilmesBinding.recyclerFilmes;
        filmesAdapter = new ListaFilmesAdapter(this);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerFilmes.setLayoutManager(gridLayoutManager);
        recyclerFilmes.setAdapter(filmesAdapter);
    }

    public void mostraErro() {
        Toast.makeText(this, "Erro ao obter lista de filmes.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemFilmeClicado(Filme filme) {
        Intent intent = new Intent(this, DetalhesFilmeActivity.class);
        intent.putExtra(DetalhesFilmeActivity.EXTRA_FILME, filme);
        startActivity(intent);
    }
}
