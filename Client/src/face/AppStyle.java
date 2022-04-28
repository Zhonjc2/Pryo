package face;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class AppStyle {
    //AppStyle
    public static String jf ;
    public static String jc_500;
    public static String jc_600 ;
    public static final Color backgroundColor = Color.web("#FFFAEE");
    public static final Color orange = Color.web("#FF9A72");
    public static final Color red = Color.web("#F25D5A");
    public static final Color pink = Color.web("#F57180");
    public static final Color light_orange = Color.web("#F9B194");
    public static final Color emphasize = Color.web("#FFF0CA");


    //ImageResource
    public static Image error;
    public static Image close;
    public static Image addNote;
    public static Image addNoteLogo;
    public static Image saveNote;
    public static Image rmNote;


    public AppStyle(){
        jc_600=this.getClass().getResource("/jcr/jc_600.ttf").toString();
        jc_500=this.getClass().getResource("/jcr/jc_500.ttf").toString();
        jf=this.getClass().getResource("/jf-openhuninn-1.1.ttf").toString();
        error=new Image(this.getClass().getResource("/x.png").toString());
        close=error;
        addNote=new Image(this.getClass().getResource("/addNote.png").toString());
        addNoteLogo=new Image(this.getClass().getResource("/addNoteLogo.png").toString());
        saveNote=new Image(this.getClass().getResource("/saveNote.png").toString());
        rmNote=new Image(this.getClass().getResource("/rmNote.png").toString());
    }
}
