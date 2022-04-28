package face;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import note.Note;
import service.Service;


public class NoteListPane {
    ScrollPane listPane;
    Pane backPane;
    double currentY;
    static final double GAP=8;
    static int currentSelection =-1;
    public NoteListPane(){
        listPane =new ScrollPane();
        backPane=new Pane();
        backPane.setPrefSize(254,550);
        backPane.setStyle("-fx-background-color: #FFFAEE");
        backPane.setLayoutY(0);
        backPane.setLayoutX(0);
        listPane.setContent(backPane);
        listPane.setPrefSize(254,550);
        listPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        listPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        listPane.setFitToWidth(true);
        listPane.setStyle("-fx-background: #FFFAEE;-fx-border-color:#FFFAEE;-fx-background-color: #FFFAEE");
        currentY=20;
    }

    public void updateNote(){
        backPane.getChildren().clear();
        currentY=20;
        int i=0;
        backPane.setPrefHeight(Service.orderToNote.size()*50);
        for(Note w:Service.orderToNote) {
            Pane noteButton = new Pane();
            Rectangle rec=new Rectangle(228,40, Color.web("#FFF3D6"));
            Text name = new Text(w.getNoteName());
            noteButton.setPrefSize(228, 40);
            noteButton.setStyle("-fx-background-color: TRANSPARENT;-fx-border-radius: 6px;-fx-background-radius: 6px");
            rec.setArcHeight(12);
            rec.setArcWidth(12);
            name.setFill(AppStyle.orange);
            name.setLayoutX(14);
            name.setLayoutY(25);
            name.setFont(Font.loadFont(AppStyle.jc_500,16));
            noteButton.getChildren().addAll(rec,name);
            noteButton.setLayoutX(8);
            noteButton.setLayoutY(currentY);

            currentY=currentY+GAP+40;
            backPane.getChildren().add(noteButton);
//            if(currentY>490)backPane.setPrefHeight(backPane.getPrefHeight()+40);

            //选择之后的动画：
            double y=noteButton.getLayoutY();
            double h=rec.getHeight();
            KeyValue v=new KeyValue(rec.fillProperty(),AppStyle.orange);
            KeyValue vv=new KeyValue(name.fillProperty(),AppStyle.backgroundColor);
            KeyValue vvv=new KeyValue(rec.heightProperty(),h);
            KeyValue vvvv=new KeyValue(noteButton.layoutYProperty(),y);
            KeyFrame f=new KeyFrame(Duration.millis(200),v,vv,vvv,vvvv);
            Timeline l=new Timeline(f);

            int finalI = i;
            noteButton.setOnMouseClicked(event -> {
                if(!MainInterface.noteArea.isEditable())MainInterface.noteArea.setEditable(true);
                MainInterface.saveNoteButton.setDisable(false,Color.web("#DBEFF5"));
                MainInterface.rmNoteButton.setDisable(false,null);
                if(currentSelection !=-1 && currentSelection !=finalI){
                    Pane lastSelect=(Pane)backPane.getChildren().get(currentSelection);
                    //当新的被选中后，lastSelect要变回之前的按钮，并恢复之前的监听器：
                    new Timeline(new KeyFrame(Duration.millis(200),event1 -> buttonAnime((Rectangle) lastSelect.getChildren().get(0),lastSelect,(Text)lastSelect.getChildren().get(1)),new KeyValue(((Rectangle) lastSelect.getChildren().get(0)).fillProperty(),Color.web("#FFF3D6")),new KeyValue(((Text)lastSelect.getChildren().get(1)).fillProperty(),AppStyle.orange))).play();
                }
                currentSelection =finalI;
                l.play();
                noteButton.setOnMouseEntered(e->MainInterface.mainScene.setCursor(Cursor.HAND));
                noteButton.setOnMouseExited(e->MainInterface.mainScene.setCursor(Cursor.DEFAULT));
                MainInterface.noteArea.clear();
                Main.mainService.getNote(finalI);

                    System.out.println(Service.orderToNote);
                    System.out.println(Service.orderToNote);
                    System.out.println(Service.orderToNote.get(finalI));
                    System.out.println(Service.orderToNote.get(finalI).getNoteContent());

                String content=Service.orderToNote.get(finalI).getNoteContent();
                System.out.println(content);
                MainInterface.noteArea.setText(content);
            });

            //刷新之前如果已选择某个Note，则刷新后依旧是被选中状态。
            if(currentSelection==i){
                System.out.println("NLP L69:"+currentSelection);
                name.setFill(AppStyle.backgroundColor);
                rec.setFill(AppStyle.orange);
                noteButton.setOnMouseEntered(e->MainInterface.mainScene.setCursor(Cursor.HAND));
                noteButton.setOnMouseExited(e->MainInterface.mainScene.setCursor(Cursor.DEFAULT));
                i++;
                continue;
            }

            //EnteredExited动画
            buttonAnime(rec,noteButton,name);


            i++;
        }
    }

    public void buttonAnime(Rectangle rec,Pane noteButton,Text name){
        KeyValue v1=new KeyValue(rec.fillProperty(),AppStyle.emphasize);
        KeyFrame f1=new KeyFrame(Duration.millis(200),v1);
        Timeline l1=new Timeline(f1);
        KeyValue v2=new KeyValue(rec.fillProperty(),Color.web("#FFF3D6"));
        KeyFrame f2=new KeyFrame(Duration.millis(200),v2);
        Timeline l2=new Timeline(f2);

        double y=noteButton.getLayoutY();
        double h=rec.getHeight();
        KeyValue v11=new KeyValue(noteButton.layoutYProperty(),y-3);
        KeyValue v12=new KeyValue(rec.heightProperty(),47);
        KeyFrame f11=new KeyFrame(Duration.millis(200),v11,v12);
        Timeline l11=new Timeline(f11);

        KeyValue v21=new KeyValue(noteButton.layoutYProperty(),y);
        KeyValue v22=new KeyValue(rec.heightProperty(),h);
        KeyFrame f21=new KeyFrame(Duration.millis(200),v21,v22);
        Timeline l21=new Timeline(f21);

        noteButton.setOnMouseEntered(event -> {
            MainInterface.mainScene.setCursor(Cursor.HAND);
            l2.stop();
            l1.play();
            l21.stop();
            l11.play();
        });
        noteButton.setOnMouseExited(event -> {
            MainInterface.mainScene.setCursor(Cursor.DEFAULT);
            l1.stop();
            l2.play();
            l11.stop();
            l21.play();
        });
    }

}
