package com.htn.view;

import com.htn.api.view.View;
import com.htn.view.customer.CustomerView;
import javafx.scene.control.Tab;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ViewFactory {
    @Getter private final static HashMap<String, Class<? extends View>> views = new HashMap<>();
    public static @Nullable View get(@NotNull String request, Tab parent) {
        Class<? extends View> cview = views.get(request);
        if (cview == null) return null;
        try {
            Constructor<? extends View> constructor = cview.getDeclaredConstructor(Tab.class);
            System.out.println("hm " + constructor);
            View view = (View) constructor.newInstance(parent);
            parent.textProperty().bindBidirectional(view.getTitle());
            return view;
        } catch (NoSuchMethodException e) {
            try {
                View view = (View) cview.newInstance();
                parent.textProperty().bindBidirectional(view.getTitle());
                return view;
            } catch (InstantiationException | IllegalAccessException ex) {
                return null;
            }
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
