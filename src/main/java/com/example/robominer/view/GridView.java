package com.example.robominer.view;

import com.example.robominer.controller.GridController;
import com.example.robominer.model.*;
import com.example.robominer.util.MineralType;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class GridView extends Application {
    private GridController controller;
    private GridPane gridPane;
    private static final int SECTEUR_SIZE = 50;
    private Scene scene;
    private Label robotTurnLabel;

    public void setController(GridController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulation de robot miner");
        BorderPane borderPane = new BorderPane();
        gridPane = new GridPane();
        initRobotTurnLabel();
        updateRobotTurnLabel();
        HBox legendBox = createLegend();
        legendBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        borderPane.setTop(robotTurnLabel);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(legendBox);

        scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        updateView();
    }

    public Scene getScene() {
        return scene;
    }

    public void updateView() {
        gridPane.getChildren().clear();
        Secteur[][] secteurs = controller.getGrid().getSecteurs();

        for (int row = 0; row < secteurs.length; row++) {
            for (int col = 0; col < secteurs[row].length; col++) {
                Secteur secteur = secteurs[row][col];
                Color color;

                StackPane stackPane = new StackPane();
                Rectangle rect = new Rectangle(SECTEUR_SIZE, SECTEUR_SIZE);
                rect.setStroke(Color.BLACK);
                if (secteur instanceof Water) {
                    color = Color.BLUE;
                } else if (secteur instanceof Warehouse) {
                    Warehouse warehouse = (Warehouse) secteur;
                    color = warehouse.getColor();
                } else if (secteur instanceof Mine) {
                    Mine mine = (Mine) secteur;
                    color = mine.getColor();
                } else if (secteur instanceof Robot) {
                    Robot robot = (Robot) secteur;
                    color = robot.getColor();
                } else {
                    color = Color.WHITE;
                }
                rect.setFill(color);
                stackPane.getChildren().add(rect);
                gridPane.add(stackPane, col, row);
            }
        }
        updateRobotTurnLabel();
    }

    private void initRobotTurnLabel() {
        robotTurnLabel = new Label();
        robotTurnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        robotTurnLabel.setTextAlignment(TextAlignment.CENTER);
        robotTurnLabel.setAlignment(Pos.CENTER);
        robotTurnLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void updateRobotTurnLabel() {
        Robot robot = controller.getCurrentRobot();
        int currentRobotNumber = robot.getNumber();
        Color currentRobotColor = robot.getColor();
        MineralType type = robot.getMineralType();
        robotTurnLabel.setText("C'est le tour du Robot " + currentRobotNumber + " - " + type);
        robotTurnLabel.setTextFill(currentRobotColor);
    }

    private HBox createLegend() {
        HBox legendBox = new HBox(14);
        legendBox.setAlignment(Pos.CENTER);

        Label waterLabel = createLegendLabel("Water", Color.BLUE);
        Label nickelMineLabel = createLegendLabel("Mine du nickel", Color.GREY);
        Label goldMineLabel = createLegendLabel("Mine de l'or", Color.YELLOW);
        Label goldwarehouseLabel = createLegendLabel("Warehouse de l'or", Color.ORANGE);
        Label nickelwarehouseLabel = createLegendLabel("Warehouse du nickel", Color.BLACK);

        legendBox.getChildren().addAll(waterLabel, nickelMineLabel, goldMineLabel, goldwarehouseLabel, nickelwarehouseLabel);
        return legendBox;
    }

    private Label createLegendLabel(String text, Color color) {
        Label label = new Label(text);
        label.setTextFill(color);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return label;
    }

    public void printGrid(Grid grille) {
        for (int i = 0; i < grille.getRows(); i++) {
            for (int row = 0; row < 2; row++) {
                for (int j = 0; j < grille.getCols(); j++) {
                    char[][] matrice = grille.getSecteur(i, j).getMatrice();
                    System.out.print(matrice[row][0] + " " + matrice[row][1] + " ");
                }
                System.out.println();
            }
        }
    }
}
