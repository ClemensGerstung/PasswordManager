package com.password.manager.gui.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

/**
 * Created by Clemens on 01.06.2015.
 */
public class AlertDialogHelper {
    private AlertDialog dialog;

    private static AlertDialogHelper alertDialogHelper;

    static {
        alertDialogHelper = new AlertDialogHelper();
    }

    /*
    * creates an AlertDialog without any Button
    * */
    public static AlertDialogHelper create(Context context, int titleId, View view) {
        alertDialogHelper.dialog = new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setView(view)
                .create();
        return alertDialogHelper;
    }

    /*
    * creates an AlertDialog with only one Button.
    * */
    public static AlertDialogHelper create(Context context, int titleId, View view, int buttonTextId){
        alertDialogHelper.dialog = new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setView(view)
                .setPositiveButton(buttonTextId, null)
                .create();
        return alertDialogHelper;
    }

    /*
    * creates an AlertDialog with two Button.
    * */
    public static AlertDialogHelper create(Context context, int titleId, View view, int posTextId, int negTextId){
        alertDialogHelper.dialog = new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setView(view)
                .setPositiveButton(posTextId, null)
                .setNegativeButton(negTextId, null)
                .create();
        return alertDialogHelper;
    }

    /*
    * creates an AlertDialog with three Button.
    * */
    public static AlertDialogHelper create(Context context, int titleId, View view, int posTextId, int negTextId, int neuTextId){
        alertDialogHelper.dialog = new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setView(view)
                .setPositiveButton(posTextId, null)
                .setNegativeButton(negTextId, null)
                .setNeutralButton(neuTextId, null)
                .create();
        return alertDialogHelper;
    }

    /*
    * sets the onclickevent of the PositiveButton
    * all other events are null
    * */
    public AlertDialogHelper setSingleButton(final OnClickListener listener) {
        final AlertDialogHelper th = this;
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(th);
                    }
                });
            }
        });
        return this;
    }

    public AlertDialogHelper setPositiveAndNegativeButton(final OnClickListener posListener,
                                                          final OnClickListener negListener) {
        final AlertDialogHelper th = this;
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        posListener.onClick(th);
                    }
                });
                Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negListener.onClick(th);
                    }
                });
            }
        });

        return this;
    }

    public AlertDialogHelper setAllButtons(final OnClickListener posListener,
                                           final OnClickListener negListener,
                                           final OnClickListener neuListener) {
        final AlertDialogHelper th = this;
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        posListener.onClick(th);
                    }
                });
                Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negListener.onClick(th);
                    }
                });
                Button neutral = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                neutral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        neuListener.onClick(th);
                    }
                });
            }
        });

        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public static void close() {
        if(alertDialogHelper.dialog.isShowing()) {
            alertDialogHelper.dialog.dismiss();
        }
    }

    public static interface OnClickListener{
        public abstract void onClick(AlertDialogHelper dialog);
    }
}
