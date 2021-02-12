package com.institutotransire.events.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class LoadDialog {

    private AlertDialog dialog;
    private LinearLayout.LayoutParams llParam;
    private int ySize = 300;
    private  int xSize = 500;
    private int textSize = 30;

    private int color = Color.CYAN;
    private boolean cancelable = true;
    private int textColor = Color.BLACK;
    private LinearLayout.LayoutParams WRAP_CONTENT = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    private boolean FORMAT_LAYOUT_VERTICAL = true;

    private Context context;

    public static void showStandardLoading(LoadDialog loadDialog, String message) {
        loadDialog
                .setCancelable(false)
                .setColor(Color.rgb(2, 101, 137))
                .setTextSize(16)
                .setSize(150, 150)
                .setTextColor(Color.WHITE)
                .startWithMessage(message);
    }

    public LoadDialog(Context context) {
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
    }

    public LoadDialog setColor(int color) {
        this.color = color;
        return this;
    }

    public LoadDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public LoadDialog setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public LoadDialog setTextSize(int size) {
        this.textSize = size;
        return this;
    }

    public LoadDialog setSize(int x, int y) {
        xSize = x;
        ySize = y;
        return this;
    }

    public LoadDialog setHorizontalLayout() {
        FORMAT_LAYOUT_VERTICAL = false;
        setSize(80, 80);
        return this;
    }

    public void justLoad() {

        dismissIt();
        RelativeLayout layoutBackground = new RelativeLayout(context);
        layoutBackground.setGravity(Gravity.CENTER);

        layoutBackground.setLayoutParams(WRAP_CONTENT);

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, 20, 0);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        llParam = new LinearLayout.LayoutParams(xSize, ySize);
        progressBar.setLayoutParams(llParam);
        layoutBackground.addView(progressBar);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(cancelable);
        builder.setView(layoutBackground);

        dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }
    }

    public void startWithMessage(String textToShow) {
        dismissIt();
        LinearLayout ll = new LinearLayout(context);
        ProgressBar progressBar = new ProgressBar(context);

        if (FORMAT_LAYOUT_VERTICAL) {
            ll.setOrientation(LinearLayout.VERTICAL);
            progressBar.setPadding(0, 0, 0, 50);
        } else {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            progressBar.setPadding(0, 0, 30, 0);
        }

        ll.setPadding(10, 30, 0, 30);
        ll.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams llParam = WRAP_CONTENT;
        llParam.gravity = Gravity.CENTER;
        ll.setLayoutParams(llParam);

        llParam = new LinearLayout.LayoutParams(xSize, ySize);
        progressBar.setIndeterminate(true);
        progressBar.setLayoutParams(llParam);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        llParam = WRAP_CONTENT;
        llParam.gravity = Gravity.CENTER;
        TextView tvText = new TextView(context);
        tvText.setText(textToShow);
        tvText.setTextColor(textColor);
        tvText.setTextSize(textSize);
        tvText.setLayoutParams(llParam);

        ll.addView(progressBar);
        ll.addView(tvText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(cancelable);
        builder.setView(ll);

        dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();

        if (FORMAT_LAYOUT_VERTICAL)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }

    }

    public void loadInAView(ScrollView view) {
        dismissIt();
        RelativeLayout layoutBackground = new RelativeLayout(context);
        layoutBackground.setGravity(Gravity.CENTER);

        layoutBackground.setLayoutParams(WRAP_CONTENT);

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, 20, 0);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        llParam = new LinearLayout.LayoutParams(xSize, ySize);
        progressBar.setLayoutParams(llParam);
        layoutBackground.addView(progressBar);

        dialog.show();

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }
    }

    public void dismissIt() {
        if (dialog.isShowing())
            dialog.dismiss();
    }
}
