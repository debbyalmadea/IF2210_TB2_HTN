package com.htn.view;

import com.htn.api.view.View;
import com.htn.application.PluginManager;
import com.htn.view.customer.CustomerView;
import com.htn.view.main.MainView;
import javafx.scene.control.Tab;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class ViewFactory {
    @Getter private static HashMap<String, Class<? extends View>> views = new HashMap<>();
    public static @Nullable View get(@NotNull String request, Tab parent) {
        Class<? extends View> cview = views.get(request);
        if (cview == null) return new CustomerView(parent);
        try {
            Constructor constructor = cview.getDeclaredConstructor(Tab.class);
            View view = (View) constructor.newInstance(parent);
            parent.textProperty().bindBidirectional(view.getTitle());
            return view;
        } catch (NoSuchMethodException e) {
            View view = null;
            try {
                view = (View) cview.newInstance();
                parent.textProperty().bindBidirectional(view.getTitle());
                return view;
            } catch (InstantiationException ex) {
                return null;
            } catch (IllegalAccessException ex) {
                return null;
            }
        }
        catch (InstantiationException | IllegalAccessException e) {
            return new CustomerView(parent);
        } catch (InvocationTargetException e) {
            return new CustomerView(parent);
        }
//        if (request.equalsIgnoreCase("customer")) {
//            View view = new CustomerView(parent);
//            // TODO! SEARCH BETTER WAY TO BIND THE TAB TEXT
//            parent.textProperty().bindBidirectional(view.getTitle());
//            return view;
//        } else if (request.equalsIgnoreCase("main")) {
//            View view = new MainView(parent);
//            parent.textProperty().bindBidirectional(view.getTitle());
//            return view;
//        } else if (request.equalsIgnoreCase("bill")) {
//            View view = new BillView(parent);
//            parent.textProperty().bindBidirectional(view.getTitle());
//            return view;
//        }else if(request.equalsIgnoreCase("product")){
//            View view = new ProductView(parent);
//            parent.textProperty().bindBidirectional(view.getTitle());
//            return view;
//        } else if (request.equalsIgnoreCase("shopping")) {
//            View view = new BillProductView(parent);
//            parent.textProperty().bindBidirectional(view.getTitle());
//            return view;
//        }

//        List<Object> plugins = PluginManager.getInstance().getPlugin(View.class);
//        for (Object plugin : plugins) {
//                    View view = (View) plugin;
//                    if (request.equalsIgnoreCase(view.getTitle().get())) {
//                        parent.textProperty().bindBidirectional(view.getTitle());
//                        return view;
//                    }
//        };


//        if(request.equalsIgnoreCase("settings...")){
//            View view = new SettingsView(parent);
//            parent.textProperty().bindBidirectional(view.getTitle());
//            return view;
//        }

//        return null;
    }
}
