package com.htn.view.main;
import com.htn.api.view.View;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainView implements View {
    @Getter
    private final BorderPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Main");
    private VBox content;
    @Getter private final Tab parent;
    private Label timeText;
    private Thread clockThread;

    public MainView(Tab parent) {
        view = new BorderPane();
        this.parent = parent;
        init();
        view.setCenter(content);
        view.getStylesheets().add("application.css");
        content.setStyle("-fx-background-color: #FFF;");
    }
    @Override
    public void init() {
        // Create the VBox and set its properties
        content = new VBox();
        content.setAlignment(Pos.CENTER); // Add this line to center the content

        // Create the clock text
        timeText = new Label();
        timeText.getStyleClass().add("title");

        // Create the ImageView and load the image
        ImageView imageView = new ImageView();
        Image image = new Image(getClass().getResource("/image/htn-logo.png").toExternalForm());
        imageView.setImage(image);
        imageView.setFitHeight(200); // Set the width of the image
        imageView.setPreserveRatio(true);

        // Add the banner and clock text to the VBox
        content.getChildren().addAll(imageView, timeText);
        content.setSpacing(20);
        // Add the footer text to the VBox
        content.getChildren().addAll(
                new Text("13521075 - Muhammad Rifko Favian"),
                new Text("13521123 - William Nixon"),
                new Text("13521128 - Muhammad Abdul Aziz Ghazali"),
                new Text("13521153 - Made Debby Almadea Putri"),
                new Text("13521155 - Kandida Edgina Gunawan")
        );

        // Start the clock thread
        clockThread = new Thread(() -> {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            while (true) {
                String time = timeFormat.format(new Date());
                Platform.runLater(() -> timeText.setText(time));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        clockThread.setDaemon(true);
        clockThread.start();
    }

    // IF maybe want to stop thread if tab closed or whatnot
    public void stop() {
        clockThread.interrupt();
    }
}