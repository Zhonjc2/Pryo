package face;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.Service;

import static javafx.scene.paint.Color.TRANSPARENT;

public class Main extends Application {
    AppStyle initStyle=new AppStyle();

    //NetworkError
    ImageView cry=new ImageView(this.getClass().getResource("/cry.png").toString());
    static Pane errorPane=new Pane();
    static Scene errorScene =new Scene(errorPane);
    static Text errorTitle =new Text("Oops...\n服务器连接超时");
    static Text errorTip=new Text("请检查网络，并稍后再试");
    static PryoButton errorOK=new PryoButton(errorScene,"确定",128,50,event -> {
        Platform.exit();
        System.exit(-1);
    });

    //Service
    static Service mainService;
//    protected static HashMap<Integer,Note> noteHashMap=Service.orderToNote; 注意静态变量传递的不是地址，是变量值，orderToNote的改变不会影响到noteHashMap。

    public void start(Stage primaryStage){
        try {
            mainService=new Service();
            LoginInterface.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            cry.setPreserveRatio(true);
            cry.setFitWidth(161);
            errorTitle.setFill(AppStyle.red);
            errorTitle.setTextAlignment(TextAlignment.CENTER);
            errorTitle.setFont(Font.loadFont(AppStyle.jc_600,38));
            errorTip.setFill(AppStyle.orange);
            errorTip.setFont(Font.loadFont(AppStyle.jc_600,22));
            setPos(cry,159,50);
            setPos(errorTitle,107,280);
            setPos(errorTip,120,431);
            setPos(errorOK.getTrueButton(),176,471);
            errorPane.setPrefSize(480,570);
            errorPane.getChildren().addAll(cry,errorTitle,errorTip,errorOK.getTrueButton());
            errorPane.setStyle("-fx-background-color: #FFFAEE;-fx-border-radius: 10;-fx-background-radius: 11;-fx-border-width: 5px;-fx-border-color: #F9B194");
            Stage errorStage=new Stage();
            errorScene.setFill(TRANSPARENT);
            errorStage.initStyle(StageStyle.TRANSPARENT);
            errorStage.setScene(errorScene);
            errorStage.show();
            dragWindow(errorStage,errorPane);
        }
    }

    private void preErrorPaneFace(){

    }

    public static void setPos(Node node, double x, double y){
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    public double sPointerX=0, sPointerY=0;
    public void dragWindow(Stage primaryStage,Pane mainPane){
        mainPane.setOnMousePressed(e -> {
            sPointerX = e.getSceneX();
            sPointerY = e.getSceneY();
        });
        mainPane.setOnMouseDragged(e -> {
            if(sPointerX>0 && sPointerY<52) {
                primaryStage.setX(e.getScreenX() - sPointerX);
                primaryStage.setY(e.getScreenY() - sPointerY);
            }
        });
    }

}
