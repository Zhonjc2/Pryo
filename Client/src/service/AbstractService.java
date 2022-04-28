package service;

import note.Note;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public abstract class AbstractService {
    public static String loggedUser;
    boolean loggedIn=false;
    abstract Vector<Note> login(String userName, String password);
    abstract int register(String userName, String password);
    abstract void start() throws IOException;
    abstract Vector<Note> getNoteNameList();
    abstract void getNote(int order);//通过次序和笔记列表找到对应的time并找到note
    abstract boolean createNote(String name);
    abstract boolean deleteNote(int order);
    abstract boolean saveNote(String content,int order);
    abstract boolean quit();
}
