package com.htn.view.main;
import com.htn.view.View;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainView implements View {

    @Getter
    private final ScrollPane view;
    @Getter
    private final StringProperty title = new SimpleStringProperty("Main");
    private VBox content;
    @Getter private final Tab parent;
    private Text timeText;
    private Thread clockThread;

    public MainView(Tab parent) {
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        this.parent = parent;
        init();
        view.setContent(content);
    }
    @Override
    public void init() {
        // Create the VBox and set its properties
        // Create the VBox and set its properties
        content = new VBox();
        content.setStyle("-fx-background-color: #333333; -fx-padding: 20px;");
        content.setAlignment(Pos.CENTER); // Add this line to center the content

        // Create the banner
        Text bannerText = new Text("Hontouni");
        bannerText.setFill(javafx.scene.paint.Color.WHITE);
        bannerText.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        bannerText.setTextAlignment(TextAlignment.CENTER);

        // Create the clock text
        timeText = new Text();
        timeText.setFill(javafx.scene.paint.Color.WHITE);
        timeText.setFont(Font.font("Arial", FontWeight.BOLD, 72));

        // Create the ImageView and load the image

        ImageView imageView = new ImageView();
        Image image = new Image(getClass().getResource("/asset.jpg").toExternalForm());
        imageView.setImage(image);
        imageView.setFitWidth(300); // Set the width of the image
        imageView.setPreserveRatio(true);

        // Add the banner and clock text to the VBox
        content.getChildren().addAll(bannerText, timeText, imageView);
        content.setSpacing(20);
        Text footerText = new Text("Created by John Doe (0123456) and Jane Smith (6543210)");
        footerText.setFill(javafx.scene.paint.Color.WHITE);
        footerText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Add the footer text to the VBox
        content.getChildren().add(footerText);

        // Set the alignment of the footer text to center
        VBox.setMargin(footerText, new Insets(20, 0, 0, 0));
        VBox.setMargin(imageView, new Insets(0, 0, 20, 0));

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