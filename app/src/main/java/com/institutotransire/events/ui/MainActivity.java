package com.institutotransire.events.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.institutotransire.events.R;
import com.institutotransire.events.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        adjustFontScale(getResources().getConfiguration());
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        components();
    }

    /**
     * Metodo para inicializar os componentes
     */
    public void components() {
        fragments();
    }

    /**
     * Metodo para iniciar Fragmento Home
     */
    public void fragments() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, MainFragment.newInstance(), "main");
        ft.commit();
    }

    /**
     * Metodo para iniciar o Fragmento
     */
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "dashboard");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Metodo para Ajustar o size do dispositivo
     */
    public void adjustFontScale(Configuration configuration) {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }
}