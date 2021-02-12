package com.institutotransire.events.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.institutotransire.events.R;
import com.institutotransire.events.controller.LoadDialog;
import com.institutotransire.events.databinding.FragmentErroCheckinBinding;

public class ErroCheckinFragment extends Fragment implements View.OnClickListener{

    private FragmentErroCheckinBinding erroCheckinBinding;
    private MainActivity main;

    private LoadDialog loadDialogEvents;

    public ErroCheckinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        erroCheckinBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_erro_checkin, container, false);

        components();

        return erroCheckinBinding.getRoot();
    }

    /**
     * Metodo para inicializar os componentes
     */
    public void components() {
        main = ((MainActivity) getActivity());
        loadDialogEvents = new LoadDialog(getContext());
        erroCheckinBinding.btnCheckinTryAgain.setOnClickListener(this);
        onBackPressedFragment();
    }

    /**
     * Metodo para click
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckinTryAgain:
                goToHome();
                break;

        }
    }

    /**
     * Metodo para instaciar o fragmento
     */
    public static ErroCheckinFragment newInstance() {
        ErroCheckinFragment fragment = new ErroCheckinFragment();
        return fragment;
    }

    /*Metodo para o backSpace do celular*/
    private void onBackPressedFragment() {
        erroCheckinBinding.getRoot().setFocusableInTouchMode(true);
        erroCheckinBinding.getRoot().requestFocus();
        erroCheckinBinding.getRoot().setOnKeyListener((view, i, keyEvent) -> {
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
