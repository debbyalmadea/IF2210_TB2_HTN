package com.htn.view;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public interface View {
    public Node getView();
    public void init();
    public StringProperty getTitle();
}
