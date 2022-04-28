package protocol;

import note.Note;

import java.io.Serializable;
import java.util.Vector;

public class Response implements Serializable {
    private int operator;
    private String userName;
    private String time;
    private Vector<Note> notesVector;
    private Note note;

    //0为登录成功并返回笔记列表notesVector，1为注册成功，2为返回笔记，3为保存成功，4为添加成功，6为笔记列表获取成功返回笔记列表，7为断开成功
    //-1为登录失败密码错误，-2为注册失败SQL错误，-3为注册失败用户已存在，-4为笔记返回异常，-5为保存异常，-6为添加异常，-8为笔记列表获取失败，-9为断开失败，-10参数错误
    public Response(int operator,String userName){
        this.operator=operator;
        this.userName =userName;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Vector<Note> getNotesVector() {
        return notesVector;
    }

    public void setNotesVector(Vector<Note> notesVector) {
        this.notesVector = notesVector;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
