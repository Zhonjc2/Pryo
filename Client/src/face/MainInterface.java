package face;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.Service;


public class MainInterface {


    //Main
    static Pane mainPane=new Pane();
    static Scene mainScene=new Scene(mainPane);
    static PryoButton closeButton=new PryoButton(mainScene,25,25,25,AppStyle.pink,AppStyle.red, AppStyle.close,9,event -> quit());
    static Text logo=new Text("Pryo");
    static Text welcome=new Text("(｡･ω･｡)ﾉ\n欢迎，"+Service.loggedUser);
    static Line sep0=new Line(8,185,260,185);
    static Line sep1=new Line(260,8,260,799);
    static NoteListPane noteList=new NoteListPane();
    static String intro="你好，\n" +
            Service.loggedUser+"\n" +
            "欢迎来到 Pryo。\n" +
            "\n" +
            "\n" +
            "\n" +
            "←\n" +
            "可以在左边栏\n" +
            "选择你创建好的笔记\n" +
            "\n" +
            "编辑完笔记后\n" +
            "要记得按下 ✓ 来保存笔记\n" +
            "否则选择其他笔记后\n" +
            "当前笔记就会丢失编辑记录\n" +
            "\n" +
            "↙\n" +
            "点击 ＋ 来创建新笔记\n" +
            "\n" +
            "\n" +
            "↓\n" +
            "选中笔记后\n" +
            "按下 × 来删除笔记\n";
    static TextArea noteArea=new TextArea(intro);
    static Rectangle upShadow =new Rectangle(250,40,AppStyle.backgroundColor);
    static Rectangle downShadow=new Rectangle(250,40,AppStyle.backgroundColor);

    //AddNote
    static Rectangle blank =new Rectangle(972,806,Color.rgb(0,0,0,0));
    static Pane addPane=new Pane();
//    static Scene addScene=new Scene(addPane);
//    static Stage addStage=new Stage();
    static PryoButton addNoteButton=new PryoButton(mainScene,37,37,37,AppStyle.light_orange,AppStyle.orange, AppStyle.addNote,22,event -> preAddNote());
    static ImageView plusLogoImage=new ImageView(AppStyle.addNoteLogo);
    static StackPane plusLogo=new StackPane(new Circle(58,AppStyle.backgroundColor),plusLogoImage);
    static Text addTip=new Text("添加笔记");
    static PryoField noteNameField=new PryoField("请输入笔记名",377,53,10,26,4,AppStyle.backgroundColor,Color.TRANSPARENT,Font.loadFont(AppStyle.jc_500,25),AppStyle.backgroundColor);
    static PryoButton addCancel=new PryoButton(mainScene,128,50,16,AppStyle.backgroundColor,AppStyle.emphasize,20,"取消",AppStyle.pink,event -> addDown());
    static PryoButton addOk=new PryoButton(mainScene,128,50,16,AppStyle.backgroundColor,AppStyle.emphasize,20,"确定",AppStyle.pink,event -> {addNote();addDown();});
    static Text addFail=new Text("(._.) 添加笔记失败，稍后再试一下......");

    //SaveNote
    static PryoButton saveNoteButton=new PryoButton(mainScene,37,37,37,Color.web("#A3D1DF"),Color.web("#7FC3D8"), AppStyle.saveNote,22,event -> saveNote());

    //Tip
    static Text saveOkTip=new Text("保存成功~");
    static Text addOkTip=new Text("添加成功~");
    static StackPane tip=new StackPane(new Rectangle(111,31,Color.web("#FFF0CA",0)),new Text(""));


    //DeleteNote
    static PryoButton rmNoteButton=new PryoButton(mainScene,37,37,37,Color.web("#F6807D"),Color.web("#F25D5A"), AppStyle.close,15,event ->preRemoveNote());
    static PryoButton rmNotSureButton=new PryoButton(mainScene,98,31,14,Color.web("#FFEBED"),Color.web("#FFDFE3"),14,"手滑了...",Color.web("F57180"),event -> rmDown());
    static PryoButton rmSureButton=new PryoButton(mainScene,98,31,14,Color.web("#F57180"),Color.web("#F25D5A"),14,"删除",AppStyle.backgroundColor,event -> rmNote());
    static Text rmSure=new Text("删除当前笔记？");
    static Text rmOkTip=new Text("~_~; 笔记删除成功");
    static StackPane rmTip=new StackPane(new Rectangle(377,31,Color.web("#FFDFE3")),rmSure);
    static Pane rmQueryPane=new Pane(rmNotSureButton.getTrueButton(),rmSureButton.getTrueButton(),rmTip);


    static Rectangle tempBack=new Rectangle(972,807,Color.TRANSPARENT);

    public static void prePos(){
        mainPane.setPrefSize(973,807);
        Main.setPos(closeButton.getTrueButton(),19,19);
        Main.setPos(logo,56,46);
        Main.setPos(welcome,19,95);
        sep0.setStrokeWidth(5);
        sep1.setStrokeWidth(5);
        Main.setPos(noteList.listPane,8,189);
        Main.setPos(noteArea,268,28);
        noteArea.setPrefColumnCount(37);
        noteArea.setPrefRowCount(23);
        noteArea.setWrapText(true);
        Main.setPos(plusLogo,182,60);
        Main.setPos(addTip,166,238);
        Main.setPos(noteNameField.getField(),52,286);
        Main.setPos(addCancel.getTrueButton(),100,435);
        Main.setPos(addOk.getTrueButton(),252,435);
        addNoteButton.setPos(19,753);
        Main.setPos(addFail,110,378);
        saveNoteButton.setPos(70,753);
        Main.setPos(tip,129,776);
        Main.setPos(upShadow,8,170);
        Main.setPos(downShadow,8,720);
        Main.setPos(rmNoteButton.getTrueButton(),281,753);
        Main.setPos(rmTip,0,0);
        Main.setPos(rmQueryPane,344,800);
        Main.setPos(rmNotSureButton.getTrueButton(),396,0);
        Main.setPos(rmSureButton.getTrueButton(),511,0);
    }

    public static void preColor(){
        mainPane.setStyle("-fx-background-color: #FFFAEE;-fx-background-radius: 30;-fx-border-radius: 25;-fx-border-width: 5;-fx-border-color: #FF9A72");
        sep0.setStroke(AppStyle.emphasize);
        sep1.setStroke(AppStyle.emphasize);
        mainScene.setFill(new Color(0,0,0,0));
        logo.setFill(AppStyle.pink);
        welcome.setFill(AppStyle.orange);
        addTip.setFill(AppStyle.backgroundColor);
        addPane.setStyle("-fx-background-color: #F57180 ;-fx-border-radius: 25;-fx-background-radius: 25;");
        addNoteButton.fakeButton.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,Color.rgb(255,155,114,0.5),10,-1,0,0));
        saveNoteButton.fakeButton.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,Color.rgb(163,208,223,0.5),10,-1,0,0));
        rmNoteButton.fakeButton.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,Color.rgb(242,93,90,0.5),10,-1,0,0));
        addFail.setFill(AppStyle.backgroundColor);
        saveOkTip.setFill(Color.web("#7FC3D8",0));
        addOkTip.setFill(Color.web("#FF9A72",0));
        upShadow.setEffect(new GaussianBlur(25));
        downShadow.setEffect(new GaussianBlur(25));
        rmOkTip.setFill(AppStyle.backgroundColor);
        rmSure.setFill(AppStyle.red);
        ((Rectangle)rmTip.getChildren().get(0)).setArcHeight(14);
        ((Rectangle)rmTip.getChildren().get(0)).setArcWidth(14);
    }

    public static void preFont(){
        logo.setFont(Font.loadFont(AppStyle.jf,36));
        welcome.setFont(Font.loadFont(AppStyle.jc_600,26));
        noteArea.setFont(Font.loadFont(AppStyle.jc_500,20));
        addTip.setFont(Font.loadFont(AppStyle.jc_600,38));
        addFail.setFont(Font.loadFont(AppStyle.jc_600,20));
        saveOkTip.setFont(Font.loadFont(AppStyle.jc_600,14));
        addOkTip.setFont(Font.loadFont(AppStyle.jc_600,14));
        rmOkTip.setFont(Font.loadFont(AppStyle.jc_600,14));
        rmSure.setFont(Font.loadFont(AppStyle.jc_600,14));
    }

    public static void quit(){
        Main.mainService.quit();
        Main.mainService.interrupt();
        Platform.exit();
        System.exit(0);
    }

    public static void preAddInterface(){
        addOk.setDisable(true,Color.web("#FFE5DB"));
        addPane.setLayoutX(246);
        addPane.setLayoutY(806+131);
        addPane.setPrefSize(480,544);
        plusLogoImage.setPreserveRatio(true);
        plusLogoImage.setFitWidth(56);
//        addStage.setAlwaysOnTop(true);
//        addStage.setScene(addScene);
    }

    public static void addDown(){
        KeyValue v=new KeyValue(addPane.layoutYProperty(), 806 + 131, new Interpolator() {
            @Override
            protected double curve(double x) {
                return (1.0 - Math.pow((1.0 - x), 4));
            }
        });
        KeyFrame f=new KeyFrame(Duration.millis(500),e->mainPane.getChildren().remove(addPane),v);
        Timeline l=new Timeline(f);
        l.play();
        KeyValue vv=new KeyValue(blank.fillProperty(),Color.rgb(0,0,0,0));
        KeyFrame ff=new KeyFrame(Duration.millis(500),e->mainPane.getChildren().remove(blank),vv);
        Timeline ll=new Timeline(ff);
        ll.setDelay(Duration.millis(300));
        ll.play();
    }

    public static void preAddNote(){
//        addStage.show();
        mainPane.getChildren().addAll(blank,addPane);
        KeyValue v=new KeyValue(blank.fillProperty(), Color.rgb(0, 0, 0, 0.1));
        KeyFrame f=new KeyFrame(Duration.millis(500),v);
        Timeline l=new Timeline(f);
        l.play();
        KeyValue vv=new KeyValue(addPane.layoutYProperty(),131,new Interpolator() {
            @Override
            protected double curve(double x) {
                double factor=0.7;
                return Math.pow(2, -10 * x) * Math.sin((x - factor / 4) * (2 * Math.PI) / factor) + 1;
            }
        });
        KeyFrame ff=new KeyFrame(Duration.millis(1000),vv);
        Timeline ll=new Timeline(ff);
        ll.setDelay(Duration.millis(200));
        ll.play();
        noteNameField.trueField.textProperty().addListener(event -> {
            String noteName=noteNameField.getText();
            addOk.setDisable(noteName.length() <= 0 || noteName.length() > 10,Color.web("#FFE5DB"));
        });
    }

    static Timeline floatUp;
    static Timeline floatDown;
    public static void floatUpTip(StackPane tip,String textToColor) {
        if (mainPane.getChildren().contains(tip)) mainPane.getChildren().remove(tip);
        if(floatDown!=null)floatDown.stop();
        if(floatUp!=null)floatUp.stop();
        tip.setLayoutY(776);
        mainPane.getChildren().add(tip);
        Rectangle tipBack = (Rectangle) tip.getChildren().get(0);
        tipBack.setArcWidth(14);
        tipBack.setArcHeight(14);
        Text tipText = (Text) tip.getChildren().get(1);

        KeyValue v = new KeyValue(tipBack.fillProperty(), Color.web("#FFF0CA"));
        KeyValue vv = new KeyValue(tip.layoutYProperty(), 756, new Interpolator() {
            protected double curve(double x) {
                return (1.0 - (1.0 - x) * (1.0 - x));
            }
        });
        KeyValue vvv = new KeyValue(tipText.fillProperty(), Color.web(textToColor,1));

        KeyValue v0 = new KeyValue(tipBack.fillProperty(), Color.web("#FFF0CA", 0));
        KeyValue vv0 = new KeyValue(tip.layoutYProperty(), 776,new Interpolator() {
            protected double curve(double x) {
                return (1.0 - (1.0 - x) * (1.0 - x));
            }
        });
        KeyValue vvv0 = new KeyValue(tipText.fillProperty(), Color.web(textToColor,0));

        KeyFrame f0 = new KeyFrame(Duration.millis(500), event -> mainPane.getChildren().remove(tip), v0, vv0,vvv0);

        floatDown = new Timeline(f0);
        floatDown.setDelay(Duration.millis(1000));

        KeyFrame f = new KeyFrame(Duration.millis(500),event ->  floatDown.play(),v, vv, vvv);
        floatUp = new Timeline(f);
        floatUp.play();
    }

    public static void addNote(){
        String name=noteNameField.getText();
        noteNameField.trueField.clear();
//        addStage.close();
        boolean re=Main.mainService.createNote(name);
        if(re){
            System.out.println("创建笔记"+name+"成功");
            noteList.updateNote();
            tip.getChildren().set(1,addOkTip);
            floatUpTip(tip,"#FF9A72");
        }else{
            System.out.println("创建笔记"+name+"失败");
            addPane.getChildren().add(addFail);
        }
    }

    public static void saveNote(){
        String noteContent=noteArea.getText();
        boolean re=Main.mainService.saveNote(noteContent,NoteListPane.currentSelection);
        System.out.println("MainInterface L142"+NoteListPane.currentSelection);
        if(re){
            System.out.println("存储笔记"+Service.orderToNote.get(NoteListPane.currentSelection)+"成功");
            tip.getChildren().set(1,saveOkTip);
            floatUpTip(tip,"#7FC3D8");
        }
        else System.out.println("存储笔记"+Service.orderToNote.get(NoteListPane.currentSelection)+"失败");
    }

    public static void preRemoveNote(){
        tempBack.setOnMouseClicked(event -> {
            rmDown();
            return;
        });
        mainPane.getChildren().addAll(tempBack,rmQueryPane);
        rmNoteButton.setDisable(true,Color.web("#FFB8B7"));
        rmNotSureButton.setDisable(true,rmNotSureButton.buttonColor);
        rmSureButton.setDisable(true,rmSureButton.buttonColor);
        KeyValue v=new KeyValue(rmQueryPane.layoutYProperty(), 756, new Interpolator() {
            protected double curve(double x) {
                return (1.0 - (1.0 - x) * (1.0 - x));
            }
        });
        KeyFrame f=new KeyFrame(Duration.millis(300),event -> {
            rmNotSureButton.setDisable(false,rmNotSureButton.buttonColor);
            rmSureButton.setDisable(false,rmSureButton.buttonColor);
        },v);
        Timeline l=new Timeline(f);
        l.play();
    }

    public static void rmNote(){
        boolean re=Main.mainService.deleteNote(NoteListPane.currentSelection);
        if(re) {
            saveNoteButton.setDisable(true,Color.web("#DBEFF5"));
            NoteListPane.currentSelection = -1;
            noteArea.clear();
            noteArea.setEditable(false);
            noteList.updateNote();

            Rectangle tipRec=(Rectangle) rmTip.getChildren().get(0);

            KeyValue vvvvv=new KeyValue(rmQueryPane.layoutYProperty(), 800, new Interpolator() {
                @Override
                protected double curve(double x) {
                    return (1.0 - (1.0 - x) * (1.0 - x));
                }
            });
            KeyFrame fffff=new KeyFrame(Duration.millis(200),event -> {
                mainPane.getChildren().remove(rmQueryPane);
                rmSure.setFill(Color.web("#F57180"));
                rmSure.setText("删除当前笔记？");
                tipRec.setWidth(377);
                tipRec.setFill(Color.web("#FFDFE3"));
                if(mainPane.getChildren().contains(tempBack))mainPane.getChildren().remove(tempBack);
            },vvvvv);
            Timeline lllll=new Timeline(fffff);
            lllll.setDelay(Duration.millis(2000));

            KeyValue vv=new KeyValue(rmSure.fillProperty(),AppStyle.backgroundColor);
            KeyFrame ff=new KeyFrame(Duration.millis(200),event -> {

            },vv);
            Timeline ll=new Timeline(ff);

            KeyValue v=new KeyValue(rmSure.fillProperty(),Color.TRANSPARENT);
            KeyFrame f=new KeyFrame(Duration.millis(200),event -> {
                rmSure.setText("~_~ 笔记删除成功");
                ll.play();
            },v);
            Timeline l=new Timeline(f);
            l.play();

            KeyValue vvv=new KeyValue(tipRec.widthProperty(),609,new Interpolator() {
                @Override
                protected double curve(double x) {
                    return (1.0 - (1.0 - x) * (1.0 - x));
                }
            });
            KeyValue vvvv=new KeyValue(tipRec.fillProperty(),Color.web("#F57180"));
            KeyFrame fff=new KeyFrame(Duration.millis(200),event -> lllll.play(),vvv,vvvv);
            Timeline lll=new Timeline(fff);
            lll.play();
        }else{
            rmDown();
            Text rmErrorText=new Text("╮(￣▽￣\"\")╭ 删除失败");
            rmErrorText.setFill(Color.web("#FFD5E3"));
            rmErrorText.setFont(Font.loadFont(AppStyle.jc_600,14));
            Rectangle rmErrRec=new Rectangle(615,31,Color.web("#FFDFE3"));
            rmErrRec.setArcHeight(14);
            rmErrRec.setArcWidth(14);
            StackPane rmErrorStack=new StackPane(rmErrRec,rmErrorText);
            rmErrorStack.setLayoutX(340);
            floatUpTip(rmErrorStack,"#F57180");
        }
    }

    public static void rmDown(){
        if(mainPane.getChildren().contains(tempBack))mainPane.getChildren().remove(tempBack);
        rmNotSureButton.setDisable(true,rmNotSureButton.buttonColor);
        rmSureButton.setDisable(true,rmSureButton.buttonColor);
        KeyValue v=new KeyValue(rmQueryPane.layoutYProperty(), 800, new Interpolator() {
            protected double curve(double x) {
                return (1.0 - (1.0 - x) * (1.0 - x));
            }
        });
        KeyFrame f=new KeyFrame(Duration.millis(200),event -> {
            rmNoteButton.setDisable(false,null);
            mainPane.getChildren().remove(rmQueryPane);
        },v);
        Timeline l=new Timeline(f);
        l.play();
    }

    public static void start(Stage primaryStage){
        primaryStage.close();
        prePos();
        preColor();
        preFont();
        preAddInterface();
        rmNoteButton.setDisable(true,Color.web("#FFB8B7"));
        saveNoteButton.setDisable(true,Color.web("#DBEFF5"));
        noteList.updateNote();
        mainPane.getChildren().addAll(closeButton.trueButton,logo,welcome,noteList.listPane,noteArea,sep1,rmNoteButton.getTrueButton(),upShadow,downShadow,addNoteButton.getTrueButton(),saveNoteButton.getTrueButton(),sep0);
        addPane.getChildren().addAll(plusLogo,addTip,noteNameField.getField(),addCancel.getTrueButton(),addOk.getTrueButton());
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Welcome to Pryo!");
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        //如何隐藏文本区背景和滚动条：
        noteArea.setEditable(false);
        noteArea.setStyle("-fx-background-color: #FFFAEE;-fx-text-fill: #FF763F;-fx-highlight-fill: #FF763F;-fx-highlight-text-fill: #FFFAEE;-fx-control-inner-background:transparent;-fx-background-insets: 0");
        noteArea.lookup(".scroll-pane").setStyle("-fx-hbar-policy: never;-fx-vbar-policy: never;-fx-background-color: #FFFAEE");
        noteArea.lookup(".text-area .content").setStyle("-fx-background-color: #FFFAEE");
        noteArea.lookup(".text-area:focused .content").setStyle("-fx-background-color: #FFFAEE");
        dragWindow(primaryStage,mainPane);
        blank.setOnMouseClicked(event -> addDown());
        mainScene.setFill(Color.TRANSPARENT);
    }

    public static double sPointerX=0, sPointerY=0;
    public static void dragWindow(Stage primaryStage,Pane mainPane){
        mainPane.setOnMousePressed(e -> {
            sPointerX = e.getSceneX();
            sPointerY = e.getSceneY();
        });
        mainPane.setOnMouseDragged(e -> {
            if(sPointerX>59 && sPointerY<70) {
                primaryStage.setX(e.getScreenX() - sPointerX);
                primaryStage.setY(e.getScreenY() - sPointerY);
            }
        });
    }
}
