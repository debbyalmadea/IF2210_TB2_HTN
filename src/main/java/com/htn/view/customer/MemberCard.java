package com.htn.view.customer;

import com.htn.controller.CustomerController;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.view.CardBuilder;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

class MemberCard {
    @Getter
    private final Member member;
    @Getter private final CustomerView parent;
    @Getter private final Pane card;
    public MemberCard(@NotNull Member member, CustomerView parent) {
        this.member = member;
        this.parent = parent;
        card = CardBuilder.builder()
                .title(member.getName())
                .subtitle(member.getPoint() + " point")
                .body(this.body())
                .styleSheet(member instanceof VIPMember ? "vip-card.css" : "card.css")
                .footer(this.footer())
                .build().getCard();
    }
    private @NotNull VBox body() {
        VBox bodyContainer = new VBox();
        bodyContainer.getChildren().addAll(
                new Label(member.getId().toString()),
                new Label(member.getPhoneNumber())
        );

        return bodyContainer;
    }
    private @NotNull HBox footer() {
        if (member.isActivated()) return activatedFooter();

        return deactivatedFooter();
    }
    private @NotNull HBox activatedFooter() {
        HBox buttonContainer = new HBox();
        Button activeButton = new Button("Deactivate");
        activeButton.getStyleClass().setAll("btn", "btn-red", "btn-small");
        activeButton.setPrefWidth(105);
        activeButton.setOnAction(e -> {
            CustomerController.deactivate(member);
        });

        Button editButton = new Button("Edit");
        editButton.getStyleClass().setAll("btn", "btn-blue", "btn-small");
        editButton.setPrefWidth(105);
        editButton.setOnAction(e -> {
            parent.edit(this.member);
        });

        buttonContainer.setSpacing(10);
        buttonContainer.getChildren().addAll(activeButton, editButton);

        return buttonContainer;
    }
    private @NotNull HBox deactivatedFooter() {
        HBox buttonContainer = new HBox();
        Button activateButton = new Button("Activate");
        activateButton.getStyleClass().setAll("btn", "btn-green", "btn-small");
        activateButton.setPrefWidth(210);
        activateButton.setOnAction(e -> {
            CustomerController.activate(member);
        });
        buttonContainer.getChildren().add(activateButton);
        return buttonContainer;
    }
}
