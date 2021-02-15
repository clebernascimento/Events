package com.institutotransire.events.ui;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.institutotransire.events.R;
import com.institutotransire.events.controller.DateTime;
import com.institutotransire.events.controller.LoadDialog;
import com.institutotransire.events.databinding.FragmentDetailsEventsBinding;
import com.institutotransire.events.services.dataSource.EventsDataSource;
import com.institutotransire.events.services.model.Contains;
import com.institutotransire.events.services.model.DetailsEvents;
import com.institutotransire.events.util.Formatters;
import com.institutotransire.events.util.ImgUtil;

import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsEventsFragment extends Fragment implements View.OnClickListener {

    private FragmentDetailsEventsBinding detailsEventsBinding;
    private MainActivity main;
    private EventsDataSource mEventsDataSource;
    private LoadDialog loadDialogEvents;

    private NumberFormat nf = Formatters.numFormat();

    public DetailsEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        detailsEventsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_events, container, false);

        components();

        return detailsEventsBinding.getRoot();
    }

    /**
     * Metodo para inicializar os componentes
     */
    public void components() {
        main = ((MainActivity) getActivity());
        loadDialogEvents = new LoadDialog(getContext());
        detailsEventsBinding.btnBackHome.setOnClickListener(this);
        setAPI();
        getDetailsEvents();
        formChekin();
        onBackPressedFragment();
    }

    /**
     * Metodo para click
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_backHome:
                goToHome();
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
     * Metodo para mostrar o evento escolhido
     */
    @SuppressLint("SetTextI18n")
    public void getDetailsEvents() {
        assert getArguments() != null;
        ImgUtil.requestImgWeb(getContext(), detailsEventsBinding.imgEvents, getArguments().getString("img"));
        detailsEventsBinding.nameEvents.setText(getArguments().getString("title"));
        detailsEventsBinding.dateEvents.setText("Data: " + DateTime.getDate(getArguments().getLong("date")));
        detailsEventsBinding.priceEvents.setText("R$ " + nf.format(getArguments().getDouble("price")));
        detailsEventsBinding.descriptionEvents.setText(getArguments().getString("desc"));
    }

    public void setChekin() {
        String name = detailsEventsBinding.editName.getText().toString();
        String email = detailsEventsBinding.editEmail.getText().toString();

        DetailsEvents bodyEvesnts = new DetailsEvents(Contains.id, name, email);

        detailsEventsBinding.btnChekin.setOnClickListener(view -> {
            if (!validate(name, email)) return;
            ;
            LoadDialog.showStandardLoading(loadDialogEvents, "Aguade! Estamos fazendo seu check-in.");
            mEventsDataSource.checkin(bodyEvesnts, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    System.out.println("CHECK-IN REALIZADO");
                    System.out.println(response);
                    if (response.isSuccessful()) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, ChekinFragment.newInstance())
                                .commit();

                    } else {
                        System.out.println("ERRO AO FAZER CHECK-IN");
                        setErrorCheckin();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println("ERRO DE REQUISICAO AO FAZER CHECK-IN");
                    Log.e("Error", t.getMessage());
                    setErrorCheckin();
                }
            });
            loadDialogEvents.dismissIt();
        });
    }

    /**
     * Metodo para fragmento de erro de check-in
     */
    public void setErrorCheckin(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, ErroCheckinFragment.newInstance())
                .commit();
    }

    public boolean validate(String name, String email) {
        detailsEventsBinding.editEmail.requestFocus();
        if (email.isEmpty()) {
            errorChekin();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorChekin();
            return false;
        }
        detailsEventsBinding.editName.requestFocus();
        if (name.isEmpty()) {
            errorChekin();
            return false;
        } else if (!name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
            errorChekin();
            return false;
        }
        return true;
    }

    /**
     * Metodo para erro de Login
     */
    public void errorChekin() {
        Snackbar.make(detailsEventsBinding.editName, "Ops! Campo nome ou e-mail incorreto.", Snackbar.LENGTH_LONG)
                .setBackgroundTint(getResources().getColor(R.color.blue_header))
                .setTextColor(getResources().getColor(R.color.white))
                .setActionTextColor(getResources().getColor(R.color.white))
                .setAction("Fechar", v -> {

                })
                .show();
    }

    /**
     * Metodo para validar formulario do login
     */
    public void formChekin() {
        detailsEventsBinding.editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                detailsEventsBinding.labelName.setBoxStrokeColor(getResources().getColor(R.color.blue_header));
                detailsEventsBinding.editEmail.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blue_header)));
                if (detailsEventsBinding.editEmail.getText().toString().isEmpty()) {
                    detailsEventsBinding.btnChekin.setEnabled(false);
                } else {
                    detailsEventsBinding.btnChekin.setEnabled(true);
                    setChekin();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        detailsEventsBinding.editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                detailsEventsBinding.labelName.setBoxStrokeColor(getResources().getColor(R.color.blue_header));
                detailsEventsBinding.editEmail.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blue_header)));
                if (detailsEventsBinding.editEmail.getText().toString().isEmpty()) {
                    detailsEventsBinding.btnChekin.setEnabled(false);
                } else {
                    detailsEventsBinding.btnChekin.setEnabled(true);
                    setChekin();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Metodo para instaciar o fragmento
     */
    public static DetailsEventsFragment newInstance() {
        DetailsEventsFragment fragment = new DetailsEventsFragment();
        return fragment;
    }

    /*Metodo para o backSpace do celular*/
    private void onBackPressedFragment() {
        detailsEventsBinding.getRoot().setFocusableInTouchMode(true);
        detailsEventsBinding.getRoot().requestFocus();
        detailsEventsBinding.getRoot().setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                goToHome();
                return true;
            }
            return false;
        });
    }

    /*Metodo para voltar*/
    private void goToHome() {
        Fragment fragment = new MainFragment();
        main.openFragment(fragment);
    }
}