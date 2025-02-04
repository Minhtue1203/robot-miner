package com.example.robominer.view;

import com.example.robominer.controller.GridController;
import com.example.robominer.model.*;
import com.example.robominer.util.Helper;

import com.example.robominer.util.MineralType;
import com.example.robominer.util.StatusRobotType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.robominer.util.Helper.isAllFindingStatus;

public class GridView extends Application {
    private GridController controller;
    private GridPane gridPane;
    private static final int SECTEUR_SIZE = 50;
    private Scene scene;
    private Label robotTurnLabel;
    private TextArea logs;

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

        Button button = new Button("Start Auto Place Robot");
        button.setOnAction(event -> startAutoPlaceRobot());
        logs = new TextArea();
        logs.setEditable(false);

        VBox rightPane = new VBox(10); // 10 is the spacing between elements
        rightPane.getChildren().addAll(button, logs);

        borderPane.setTop(robotTurnLabel);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(legendBox);
        borderPane.setRight(rightPane);
        updateLogs();
        scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        updateView();
    }

    public Scene getScene() {
        return scene;
    }

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private void startAutoPlaceRobot() {
        controller.autoPlaceRobotsForMine();
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            Platform.runLater(this::simulateAutoRobotMove);
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void simulateAutoRobotMove() {
        Robot currentRobot = controller.getCurrentRobot();
        List<int[]> path = currentRobot.getRobotPaths();
        int currentIndex = currentRobot.getCurrentIndexPath();
        boolean isAllMineExhausted = false;
        if (currentIndex < path.size()) {
            int[] newPos = path.get(currentIndex);
            int[] oldPos = currentRobot.getCurrentPosition();
            controller.moveRobot(oldPos, newPos[0], newPos[1]);
            currentIndex++;
            currentRobot.setCurrentIndexPath(currentIndex);
        } else {
            System.out.printf("%s", currentRobot.getStatus());

            if(currentRobot.isFinding()) {
                // currentRobot.setStatus(StatusRobotType.MINING);
                controller.loopHavestResources(currentRobot);
            } else if (currentRobot.isDepositing()) {
                controller.loopDepositResources(currentRobot);
                isAllMineExhausted = controller.isMineExhausted();
            }
        }

        updateRobotTurnLabel();
        controller.nextRobot();
        controller.updateGridConsole();
        updateLogs();
        updateView();

        if(isAllMineExhausted) {
            if(isAllFindingStatus(controller.getRobots())) { // all robot deposited mine
                robotTurnLabel.setText("Le jeu est terminé");
                robotTurnLabel.setTextFill(Color.BLACK);
                executor.shutdown();
            }
        }
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

                if (!secteur.isVisited()) {
                    color = Color.BLACK;
                } else if (secteur instanceof Water) {
                    color = Color.BLUE;
                } else if (secteur instanceof Warehouse) {
                    Warehouse warehouse = (Warehouse) secteur;
                    color = warehouse.getMineralType() == MineralType.NICKEL ? Color.GREEN : warehouse.getColor(); // Changer la couleur de l'entrepôt de nickel
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
    }



    private void initRobotTurnLabel () {
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
        Label nickelwarehouseLabel = createLegendLabel("Warehouse du nickel", Color.GREEN);

        legendBox.getChildren().addAll(waterLabel, nickelMineLabel, goldMineLabel, goldwarehouseLabel, nickelwarehouseLabel);
        return legendBox;
    }

    private Label createLegendLabel(String text, Color color) {
        Label label = new Label(text);
        label.setTextFill(color);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return label;
    }

    public void updateLogs() {
        List<SecteurInfo> addedSecteurs = controller.getAddedSecteurs();
        for (SecteurInfo secteurInfo : addedSecteurs) {
            String messages = secteurInfo.toString();
            logs.appendText(messages + "\n");
        }
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
