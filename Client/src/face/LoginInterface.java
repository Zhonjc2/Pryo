package face;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import note.Note;
import service.Service;

import java.io.IOException;
import java.util.HashMap;

public class LoginInterface{

//    Service service=new Service("Test");
    
    //
    static Stage primaryStage;
    
    //LoginPane
    public static final double LOGIN_X_BEGIN_PIXEL=43;
    public static final double LOGIN_X_END_PIXEL=LOGIN_X_BEGIN_PIXEL+397;
    static Pane loginPane=new Pane();
    static Scene mainScene=new Scene(loginPane);
    static Text title=new Text("Pryo");
    static Text sign=new Text("Cloud note, powered by Java.");
    static Text operationTitle=new Text("登录");
    static Line line0=new Line(LOGIN_X_BEGIN_PIXEL,316,LOGIN_X_END_PIXEL,316);
    static Text userNameEnterTip=new Text("用户名");
    static PryoField userNameField=new PryoField();
    static Text passwordEnterTip=new Text("密码");
    static PryoPassField passwordField=new PryoPassField();
    static PryoButton loginButton=new PryoButton(mainScene,"登录",128,50,event -> login());
    static PryoButton registerButton=new PryoButton(mainScene,"注册新用户",239,50,event -> register());
    static HBox enterButtonGroup =new HBox(loginButton.trueButton,registerButton.trueButton);
    static Text loginFeedback=new Text();








    public static void start(Stage primaryStage){
        LoginInterface.primaryStage=primaryStage;
        preFont();
        preColor();
        preLoginPaneFace();

        primaryStage.setTitle("登录到 Pryo.");
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        dragWindow(primaryStage,loginPane);
    }

    //准备登录面板和其控件
    private static void preLoginPaneFace(){
        loginPane.setPrefSize(480,740);
        loginPane.setStyle("-fx-border-radius: 28px;-fx-background-color:#FFFAEE;-fx-background-radius: 30px;-fx-border-width: 5;-fx-border-color: #FF9A72");
        Main.setPos(title,LOGIN_X_BEGIN_PIXEL-8,93-18+77);
        Main.setPos(sign,LOGIN_X_BEGIN_PIXEL,187+21);
        Main.setPos(operationTitle,LOGIN_X_BEGIN_PIXEL,262+35);
        Main.setPos(userNameEnterTip,LOGIN_X_BEGIN_PIXEL,354+17);
        Main.setPos(userNameField.getField(),LOGIN_X_BEGIN_PIXEL,388);
        Main.setPos(passwordEnterTip,LOGIN_X_BEGIN_PIXEL,476+17);
        Main.setPos(passwordField.getField(),LOGIN_X_BEGIN_PIXEL,508);
        Main.setPos(enterButtonGroup,LOGIN_X_BEGIN_PIXEL,656);
        Main.setPos(loginFeedback,LOGIN_X_BEGIN_PIXEL,620);
        enterButtonGroup.setSpacing(31);
        loginPane.getChildren().addAll(title,sign,operationTitle,line0,userNameEnterTip,userNameField.getField(),passwordEnterTip,passwordField.getField(),loginFeedback,enterButtonGroup);
        correctEnter(userNameField.trueField);
        correctEnter(passwordField.trueField);
        loginButton.setDisable(true,Color.web("#FFE5DB"));
        registerButton.setDisable(true,Color.web("#FFE5DB"));
    }

    public static void login(){
        String userName=userNameField.getText();
        String password=passwordField.getText();
        Main.mainService.login(userName,password);
        if(Service.orderToNote == null){
            System.out.println(userName+"登录失败");
            loginFeedback.setText("(·_·; 用户名或密码错误......");
            shakeField(passwordField.getField());
        }else{
            System.out.println(userName+"登录成功");
            loginFeedback.setText("^_^ 登录成功！");
            MainInterface.start(primaryStage);
        }
    }

    public static void register(){
        String userName=userNameField.getText();
        String password=passwordField.getText();
        int re=Main.mainService.register(userName,password);
        if(re==0){
            System.out.println(userName+"注册成功");
            loginFeedback.setText("^_^ 注册成功！");
            MainInterface.start(primaryStage);
        }else if(re==-2){
            System.out.println(userName+"已存在，注册失败");
            loginFeedback.setText("(._.) 该用户已存在......");
            shakeField(userNameField.getField());
            userNameField.trueField.selectAll();
            passwordField.trueField.selectAll();
        }else if(re==-1){
            System.out.println(userName+"注册失败，服务器数据库错误");
            loginFeedback.setText("Σ（・□・；） 服务器可能有点问题......");
        }
    }

    private static void shakeField(StackPane field) {
        KeyValue v=new KeyValue(field.layoutXProperty(), LOGIN_X_BEGIN_PIXEL+50, new Interpolator() {
            protected double curve(double t) {
                return 0.1*Math.sin(2*Math.PI*t);//这里的t，0 代表动画开始，1 代表动画结束。
            }
        });
        KeyFrame f=new KeyFrame(Duration.millis(200), event -> field.setLayoutX(LOGIN_X_BEGIN_PIXEL),v);
        Timeline l=new Timeline(f);
        l.play();
        passwordField.trueField.selectAll();
    }

    private static void correctEnter(TextField field) {
        field.textProperty().addListener(event -> {
            String userName=userNameField.getText();
            String password=passwordField.getText();
            if(userName.length()>4 && password.length()>8){
                loginButton.setDisable(false,Color.web("#FFE5DB"));
                registerButton.setDisable(false,Color.web("#FFE5DB"));
            }else{
                loginButton.setDisable(true,Color.web("#FFE5DB"));
                registerButton.setDisable(true,Color.web("#FFE5DB"));
            }
        });
    }

    public static void preFont(){
        title.setFont(Font.loadFont(AppStyle.jf,85));
        sign.setFont(Font.loadFont(AppStyle.jf,23));
        operationTitle.setFont(Font.loadFont(AppStyle.jc_600,38));
        userNameEnterTip.setFont(Font.loadFont(AppStyle.jc_600,19));
        passwordEnterTip.setFont(Font.loadFont(AppStyle.jc_600,19));
        loginFeedback.setFont(Font.loadFont(AppStyle.jc_600,20));
    }

    public static void preColor(){
        title.setFill(AppStyle.pink);
        sign.setFill(AppStyle.pink);
        operationTitle.setFill(AppStyle.orange);
        line0.setStroke(AppStyle.orange);
        userNameEnterTip.setFill(AppStyle.orange);
        passwordEnterTip.setFill(AppStyle.orange);
        loginFeedback.setFill(AppStyle.pink);
    }

    public static double sPointerX=0, sPointerY=0;
    public static void dragWindow(Stage primaryStage,Pane mainPane){
        mainPane.setOnMousePressed(e -> {
            sPointerX = e.getSceneX();
            sPointerY = e.getSceneY();
        });
        mainPane.setOnMouseDragged(e -> {
            if(sPointerX>0 && sPointerY<86) {
                primaryStage.setX(e.getScreenX() - sPointerX);
                primaryStage.setY(e.getScreenY() - sPointerY);
            }
        });
    }

}