package com.institutotransire.events.ui;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.institutotransire.events.R;
import com.institutotransire.events.adapter.EventsAdapter;
import com.institutotransire.events.controller.LoadDialog;
import com.institutotransire.events.databinding.FragmentMainBinding;
import com.institutotransire.events.services.dataBase.EventsDataSource;
import com.institutotransire.events.services.model.Contains;
import com.institutotransire.events.services.model.Events;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements View.OnClickListener {

    private FragmentMainBinding mainBinding;
    private MainActivity main;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Events> eventsArrayList = new ArrayList<>();

    private EventsDataSource mEventsDataSource;

    private LoadDialog loadDialogEvents;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        components();

        return mainBinding.getRoot();
    }

    /**
     * Metodo para inicializar os componentes
     */
    public void components() {
        main = ((MainActivity) getActivity());
        loadDialogEvents = new LoadDialog(getContext());
        mainBinding.btnTryAgain.setOnClickListener(this);
        setAPI();
        setmRecyclerView();
        onBackPressedFragment();
        getEventsApi();
    }

    /**
     * Metodo para Click
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTryAgain:
                getEventsApi();
                break;
        }
    }

    /**
     * Metodo para iniciar as API's
     */
    public void setAPI() {
        mEventsDataSource = EventsDataSource.getInstance();
    }

    /**
     * Metodo para setar o RecyclerView
     */
    public void setmRecyclerView() {
        mainBinding.recyclerEvents.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new EventsAdapter(getContext(), eventsArrayList, new EventsAdapter.OnClick() {
            @Override
            public void selectEvents(Events events, int position) {
                Log.d("Click on events", events.toString());
                Contains.id = events.getId();

                DetailsEventsFragment fragment = new DetailsEventsFragment();

                Events detailEvents = eventsArrayList.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("img", detailEvents.getImage());
                bundle.putString("title", detailEvents.getTitle());
                bundle.putLong("date", detailEvents.getDate());
                bundle.putDouble("price", detailEvents.getPrice());
                bundle.putString("desc", detailEvents.getDescription());

                fragment.setArguments(bundle);
                eventsArrayList.clear();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
        });

        mainBinding.recyclerEvents.setLayoutManager(mLayoutManager);
        mainBinding.recyclerEvents.setAdapter(mAdapter);
    }

    public void getEventsApi() {
        LoadDialog.showStandardLoading(loadDialogEvents, "Aguarde! Buscando seus eventos");
        mEventsDataSource.listEvents(new Callback<List<Events>>() {
            @Override
            public void onResponse(Call<List<Events>> call, Response<List<Events>> response) {
                System.out.println("LISTA DE EVENTOS");
                System.out.println(response);
                if (response.isSuccessful() && response.body() != null) {
                    eventsArrayList.clear();
                    eventsArrayList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                    mainBinding.layoutListEvents.setVisibility(View.VISIBLE);
                    mainBinding.layoutErroEvents.setVisibility(View.GONE);
                } else {
                    System.out.println("ERRO NA LISTAGEM DE SERVICOS");
                    System.out.println(response);
                    mainBinding.layoutListEvents.setVisibility(View.GONE);
                    mainBinding.layoutErroEvents.setVisibility(View.VISIBLE);
                }
                loadDialogEvents.dismissIt();
            }

            @Override
            public void onFailure(Call<List<Events>> call, Throwable t) {
                System.out.println("ERRO NA REQUISICAO DOS EVENTOS");
                Log.d("ErrorBack-End", t.getMessage());
                mainBinding.layoutListEvents.setVisibility(View.GONE);
                mainBinding.layoutErroEvents.setVisibility(View.VISIBLE);
                loadDialogEvents.dismissIt();
            }
        });
    }

    /**
     * Metodo para instanciar o fragmento
     *
     * @return
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    /*Metodo para o backSpace do celular*/
    private void onBackPressedFragment() {
        mainBinding.getRoot().setFocusableInTouchMode(true);
        mainBinding.getRoot().requestFocus();
        mainBinding.getRoot().setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                goToHome();
                return true;
            }
            return false;
        });
    }

    /*Metodo para voltar*/
    private void goToHome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sair do aplicativo");
        builder.setMessage("Deseja sair do aplicativo?");
        builder.setCancelable(false);
        builder.setPositiveButton("Sim", (dialog, which) -> {
            getActivity().finishAffinity();
        }).setNegativeButton("Não", null);
        builder.show();
    }

}