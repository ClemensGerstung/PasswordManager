package com.password.manager.gui.helper;

import android.app.Activity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.password.manager.R;
import com.password.manager.core.Logger;
import com.password.manager.core.PasswordListAdapter;

/**
 * Created by Clemens on 19.12.2014.
 */
public class OrderPasswordListHelper {
    public static void orderPasswordList(final Activity activity, View view, final PasswordListAdapter passwordListAdapter) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        MenuInflater menuInflater = activity.getMenuInflater();
        menuInflater.inflate(R.menu.password_list_button_line_order_menu, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                try {
                    switch (id) {
                        case R.id.name_ascending:
                            passwordListAdapter.order("ORDER_BY username ASCENDING");
                            break;
                        case R.id.name_descending:
                            passwordListAdapter.order("ORDER_BY username DESCENDING");
                            break;
                        case R.id.password_ascending:
                            passwordListAdapter.order("ORDER_BY password ASCENDING");
                            break;
                        case R.id.password_descending:
                            passwordListAdapter.order("ORDER_BY password DESCENDING");
                            break;
                        case R.id.program_ascending:
                            passwordListAdapter.order("ORDER_BY program ASCENDING");
                            break;
                        case R.id.program_descending:
                            passwordListAdapter.order("ORDER_BY program DESCENDING");
                            break;
                    }
                } catch (Exception e) {
                    Logger.show(e.getMessage(), activity);
                }

                return true;
            }
        });

    }
}
