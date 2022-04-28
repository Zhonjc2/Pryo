package note;

import java.io.Serializable;

public class Note implements Serializable {
//    private static final long serialVersionUID=400L;
    String noteContent;//非请求笔记内容时响应对象的该成员为空
    String noteName;
    String time;

    public Note(String noteName){
        this.noteName=noteName;
    }

    public Note(String noteName,String time){
        this.noteName=noteName;
        this.time=time;
    }

    //通过Response对象获取userName。

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
