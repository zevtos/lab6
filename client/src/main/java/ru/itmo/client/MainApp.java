package ru.itmo.client;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.itmo.client.controller.*;
import ru.itmo.client.utility.runtime.Runner;
import ru.itmo.client.utility.runtime.ServerConnection;
import ru.itmo.general.models.Ticket;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Runner runner;
    private ResourceBundle bundle;
    private DataVisualizationController dataVisualizationController;

    private String currentScreen;
    private static final String LOGIN_SCREEN = "/view/LoginScreen.fxml";
    private static final String REGISTER_SCREEN = "/view/RegisterScreen.fxml";
    private static final String MAIN_SCREEN = "/view/MainScreen.fxml";
    private static final String DATA_VISUALIZATION_SCREEN = "/view/DataVisualization.fxml";

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Ticket Management System");

        this.primaryStage.setFullScreen(true);

        Locale.setDefault(new Locale("ru"));
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        this.primaryStage.setX(bounds.getMinX());
        this.primaryStage.setY(bounds.getMinY());
        this.primaryStage.setWidth(bounds.getWidth());
        this.primaryStage.setHeight(bounds.getHeight());

        ServerConnection connection = new ServerConnection("localhost", 4093); // Укажите хост и порт вашего сервера
        runner = new Runner(connection);
        this.bundle = bundle;
        initRootLayout(bundle);
        showLoginScreen(bundle);
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public void initRootLayout(ResourceBundle bundle) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            loader.setResources(bundle);
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this, bundle);
            controller.setRunner(runner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDataVisualization() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(DATA_VISUALIZATION_SCREEN));
            loader.setResources(bundle);
            AnchorPane dataVisualization = loader.load();

            dataVisualization.getStyleClass().add("data-visualization-pane");

            dataVisualizationController = loader.getController();
            dataVisualizationController.setMainApp(this);
            dataVisualizationController.setRunner(runner);

            rootLayout.setRight(dataVisualization);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginScreen(ResourceBundle bundle) {
        currentScreen = LOGIN_SCREEN;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(currentScreen));
            loader.setResources(bundle);
            BorderPane loginScreen = loader.load();

            rootLayout.setCenter(loginScreen);

            LoginController controller = loader.getController();
            controller.setRunner(runner);
            controller.setMainApp(this);
            controller.setBundle(bundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRegisterScreen(ResourceBundle bundle) {
        currentScreen = REGISTER_SCREEN;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(currentScreen));
            loader.setResources(bundle);
            BorderPane registerScreen = loader.load();

            rootLayout.setCenter(registerScreen);

            RegisterController controller = loader.getController();
            controller.setMainApp(this);
            controller.setBundle(bundle);
            controller.setRunner(runner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainScreen(ResourceBundle bundle) {
        currentScreen = MAIN_SCREEN;
        try {
            FXMLLoader loader = new FXMLLoader();
            URL resourceUrl = MainApp.class.getResource(currentScreen);
            if (resourceUrl == null) {
                throw new IOException("Resource not found: " + currentScreen);
            }
            loader.setLocation(resourceUrl);
            loader.setResources(bundle);
            BorderPane mainScreen = loader.load();

            rootLayout.setCenter(mainScreen);

            MainController controller = loader.getController();
            controller.setMainApp(this);
            controller.setRunner(runner);
            controller.setBundle(bundle);
            controller.setPrimaryStage(primaryStage);
            controller.fetchTickets();
            controller.setUserInfo();
            List<Ticket> tickets = controller.getTicketData();
            showDataVisualization();
            DataVisualizationController dataVisualizationController = getDataVisualizationController();
            if (dataVisualizationController != null) {
                dataVisualizationController.setMainController(controller);
                dataVisualizationController.initializeRoutes(tickets);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showTicketEditDialog(Ticket ticket) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/TicketEditDialog.fxml"));
            loader.setResources(bundle);
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(bundle.getString("edit.title"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TicketEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTicket(ticket);
            controller.setBundle(bundle);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static boolean showConfirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public DataVisualizationController getDataVisualizationController() {
        return dataVisualizationController;
    }

    public void changeLocale(Locale locale) {
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        reloadScenes();
    }

    private void fadeTransition(Scene scene, double fromValue, double toValue, int durationMillis, Runnable onFinished) {
        FadeTransition fade = new FadeTransition(Duration.millis(durationMillis), scene.getRoot());
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setOnFinished(event -> {
            if (onFinished != null) {
                onFinished.run();
            }
        });
        fade.play();
    }

    private void reloadScenes() {
        boolean isFullScreen = primaryStage.isFullScreen();
        double x = primaryStage.getX();
        double y = primaryStage.getY();
        double width = primaryStage.getWidth();
        double height = primaryStage.getHeight();

        // Анимация исчезновения
        fadeTransition(primaryStage.getScene(), 1.0, 0.0, 500, () -> {
            Platform.runLater(() -> {
                initRootLayout(bundle);
                initCurrentLayout();

                primaryStage.setX(x);
                primaryStage.setY(y);
                primaryStage.setWidth(width);
                primaryStage.setHeight(height);
                primaryStage.setFullScreen(isFullScreen);

                // Принудительное обновление стилей и перерисовка
                primaryStage.getScene().getRoot().applyCss();
                primaryStage.getScene().getRoot().layout();
                primaryStage.hide();  // Скрыть окно
                primaryStage.show();  // Показать окно

                // Анимация появления
                fadeTransition(primaryStage.getScene(), 0.0, 1.0, 500, null);
            });
        });
    }


    private void initCurrentLayout() {
        switch (currentScreen) {
            case LOGIN_SCREEN:
                showLoginScreen(bundle);
                break;
            case REGISTER_SCREEN:
                showRegisterScreen(bundle);
                break;
            case MAIN_SCREEN:
                showMainScreen(bundle);
                break;
        }
    }
}
