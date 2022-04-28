package face;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class PryoButton {
    Scene mainScene;
    StackPane trueButton;
    Text name;
    Image icon;
    Rectangle fakeButton;
    EventHandler<MouseEvent> handler;
    EventHandler<MouseEvent> exitAnimateHandler;
    EventHandler<MouseEvent> enterAnimateHandler;
    Color buttonColor;
    public PryoButton(Scene scene, double w, double h, double arc, Color buttonColor, Color actionColor ,double textSize,String buttonText,Color textColor,EventHandler<MouseEvent> e){
        mainScene=scene;
        fakeButton =new Rectangle(w,h);
        name=new Text(buttonText);
        trueButton =new StackPane(fakeButton,name);
        name.setFont(Font.loadFont(AppStyle.jc_600,textSize));
        name.setFill(textColor);
        fakeButton.setArcHeight(arc);
        fakeButton.setArcWidth(arc);
        fakeButton.setFill(buttonColor);
        KeyValue oldValue=new KeyValue(fakeButton.fillProperty(),actionColor);
        KeyFrame oldFrame=new KeyFrame(Duration.millis(200),oldValue);
        Timeline line0=new Timeline(oldFrame);
        KeyValue newValue=new KeyValue(fakeButton.fillProperty(),buttonColor);
        KeyFrame newFrame=new KeyFrame(Duration.millis(200),newValue);
        Timeline line1=new Timeline(newFrame);
        enterAnimateHandler = event -> {
            scene.setCursor(Cursor.HAND);
            line1.stop();
            line0.play();
        };
        exitAnimateHandler=event -> {
            scene.setCursor(Cursor.DEFAULT);
            line0.stop();
            line1.play();
        };
        trueButton.setOnMouseEntered(enterAnimateHandler);
        trueButton.setOnMouseExited(exitAnimateHandler);
        this.handler=e;
        trueButton.setOnMouseClicked(e);
        this.buttonColor=buttonColor;
    }
    public PryoButton(Scene scene, double w, double h, double arc, Color buttonColor, Color actionColor, Image icon, double iconW, EventHandler<MouseEvent> e){
        mainScene=scene;
        fakeButton =new Rectangle(w,h);
        this.icon=icon;
        ImageView imageView=new ImageView(icon);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(iconW);
        trueButton =new StackPane(fakeButton,imageView);
        fakeButton.setArcHeight(arc);
        fakeButton.setArcWidth(arc);
        fakeButton.setFill(buttonColor);
        KeyValue oldValue=new KeyValue(fakeButton.fillProperty(),actionColor);
        KeyFrame oldFrame=new KeyFrame(Duration.millis(200),oldValue);
        Timeline line0=new Timeline(oldFrame);
        KeyValue newValue=new KeyValue(fakeButton.fillProperty(),buttonColor);
        KeyFrame newFrame=new KeyFrame(Duration.millis(200),newValue);
        Timeline line1=new Timeline(newFrame);
        enterAnimateHandler = event -> {
            scene.setCursor(Cursor.HAND);
            line1.stop();
            line0.play();
        };
        exitAnimateHandler=event -> {
            scene.setCursor(Cursor.DEFAULT);
            line0.stop();
            line1.play();
        };
        trueButton.setOnMouseEntered(enterAnimateHandler);
        trueButton.setOnMouseExited(exitAnimateHandler);
        this.handler=e;
        trueButton.setOnMouseClicked(e);
        this.buttonColor=buttonColor;
    }

    public PryoButton(Scene mainScene,String text,double w,double h,EventHandler<MouseEvent> handler){
        this(mainScene,w,h,16,AppStyle.light_orange,AppStyle.orange,20,text,AppStyle.backgroundColor,handler);
    }

    public void setPos(double x,double y){
        trueButton.setLayoutX(x);
        trueButton.setLayoutY(y);
    }
    public StackPane getTrueButton(){
        return trueButton;
    }
    public Text getName(){
        return name;
    }
    public void setDisable(boolean disable,Color hideColor){
        if(disable){
            mainScene.setCursor(Cursor.DEFAULT);
            trueButton.setOnMouseClicked(e->{});
            trueButton.setOnMouseEntered(e->{});
            trueButton.setOnMouseExited(e->{});
            new Timeline(new KeyFrame(Duration.millis(150),new KeyValue(fakeButton.fillProperty(),hideColor))).play();
        }
        else {
            trueButton.setOnMouseClicked(handler);
            trueButton.setOnMouseEntered(enterAnimateHandler);
            trueButton.setOnMouseExited(exitAnimateHandler);
            new Timeline(new KeyFrame(Duration.millis(150),new KeyValue(fakeButton.fillProperty(),buttonColor))).play();
        }
    }
}

