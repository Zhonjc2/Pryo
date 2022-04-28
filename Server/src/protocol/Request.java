package protocol;

import note.Note;

import java.io.Serializable;

public class Request implements Serializable {
    private int operator;
    private String user;
    private String password;
    private Note note;
    private int order;
    //通过次序锁定笔记


    //规定操作数：0为提交登录，1为提交注册，2为通过u+t获取笔记，3为保存笔记 noteContent非空，4为新建笔记 noteName非空，6为获取用户笔记列表，7为通过order获取笔记，8为通过order删除笔记，9为断开连接
    public Request(int operator,String user){
        this.operator=operator;
        this.user=user;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
