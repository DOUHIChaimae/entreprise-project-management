package ma.enset.projectmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProjectManagementJavaFxRunner  extends Application {
    double X=0 ,Y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("presentation/views/loginView.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            X = event.getSceneX();
            Y=event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getSceneX()-X);
            primaryStage.setY(event.getSceneY()-Y);
        });
        Scene scene = new Scene(root,600,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Project Management");
        primaryStage.getIcons().add(new Image("ma/enset/projectmanagement/presentation/ressources/icon.png"));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}