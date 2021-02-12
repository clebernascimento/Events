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
import com.institutotransire.events.databinding.FragmentChekinBinding;

public class ChekinFragment extends Fragment implements  View.OnClickListener{

    private FragmentChekinBinding chekinBinding;
    private MainActivity main;

    private LoadDialog loadDialogEvents;


    public ChekinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        chekinBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chekin, container, false);

        components();

        return chekinBinding.getRoot();
    }

    /**
     * Metodo para inicializar os componentes
     */
    public void components() {
        main = ((MainActivity) getActivity());
        chekinBinding.btnCheckinSucess.setOnClickListener(this);
        loadDialogEvents = new LoadDialog(getContext());
        onBackPressedFragment();
    }

    /**
     * Metodo para click
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckinSucess:
                goToHome();
                break;

        }
    }


    /**
     * Metodo para instaciar o fragmento
     */
    public static ChekinFragment newInstance() {
        ChekinFragment fragment = new ChekinFragment();
        return fragment;
    }

    /*Metodo para o backSpace do celular*/
    private void onBackPressedFragment() {
        chekinBinding.getRoot().setFocusableInTouchMode(true);
        chekinBinding.getRoot().requestFocus();
        chekinBinding.getRoot().setOnKeyListener((view, i, keyEvent) -> {
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
