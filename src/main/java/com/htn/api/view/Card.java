package com.htn.api.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;

public interface Card {
    public String getTitle();
    public void setTitle(String title);
    public String getSubtitle();
    public void setSubtitle(String subtitle);
    public Pane getBody();
    public void setBody(Pane body);
    public Pane getFooter();
    public void setFooter(Pane footer);
}
