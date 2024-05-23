package com.example.robominer;

//public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}

import com.example.robominer.controller.GridController;
import com.example.robominer.model.Grid;
import com.example.robominer.view.GridView;

public class HelloApplication {
    public static void main(String[] args) {
        Grid grille = new Grid();
        GridView view = new GridView();
        GridController controller = new GridController(grille, view);

        controller.addRandomWater(3); // Ajouter 3 plans d'eau
        controller.addRandomMine(3); // Ajouter 3 mines avec des numéros uniques
        controller.addRandomWarehouse(2); // Ajouter 2 entrepôts avec des numéros uniques
        controller.addRandomRobot(2);

        controller.updateView();
    }
}