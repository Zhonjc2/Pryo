package database;

import note.Note;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Vector;

public class DataOperator {
    static Connection conn;
    static String notePath="/Users/zhonjc/Documents/notes";
//    static String notePath="/root/PryoNote";
    static int query=0;

    public static void loadDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","YourPassword");
            DataOperator.conn=conn;
            new Thread(()->{
                while(true) {
                    try {
                        PreparedStatement statement = conn.prepareStatement("SELECT * FROM PryoData.PryoUser");
                        ResultSet re = statement.executeQuery();
                        query++;
                        System.out.println("PRYO_SERVER_BETA_003_NOTICE:防止数据库断开，定期访问数据库，第" + query + "次请求");
                        Thread.sleep(3600000);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int login(String userName,String password){
        if(conn==null)loadDatabase();
        try {
            PreparedStatement statement=conn.prepareStatement("SELECT * FROM PryoData.PryoUser WHERE password=? AND userName=?");
            statement.setString(1,password);
            statement.setString(2,userName);
            ResultSet set=statement.executeQuery();
            if(set.next())return 0;
            else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int register(String userName,String password){
        if(conn==null)loadDatabase();
        try {
            PreparedStatement selectStatement=conn.prepareStatement("SELECT * FROM PryoData.PryoUser WHERE userName=?");
            selectStatement.setString(1,userName);
            if(!selectStatement.executeQuery().next()) {
                PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO PryoData.PryoUser VALUES (?,?)");
                insertStatement.setString(1, userName);
                insertStatement.setString(2, password);
                insertStatement.executeUpdate();
                return 0;
            }else return -2;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int addNote(String userName,String noteName,String time){
        if(conn==null)loadDatabase();
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO PryoData.PryoNotes VALUES (?,?,?,?)");
            statement.setString(1,userName);
            statement.setString(2,time);
            statement.setString(3,notePath+"/"+userName+time);
            statement.setString(4,noteName);
            statement.executeUpdate();
            File noteFile=new File(notePath+"/"+userName+time);
            noteFile.createNewFile();
            return 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Vector<Note> getNoteList(String userName){
        if(conn==null)loadDatabase();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM PryoData.PryoNotes WHERE user=?");
            statement.setString(1,userName);
            ResultSet set=statement.executeQuery();
            Vector<Note> notes=new Vector<Note>();
            while(set.next()){
                Note tempNote=new Note(set.getString(4),set.getString(2));
                notes.add(tempNote);
            }
            return notes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int saveNote(String userName,String time,String noteContent){
        if(conn==null)loadDatabase();
        try {
            PreparedStatement statement= conn.prepareStatement("SELECT path FROM PryoData.PryoNotes WHERE user=? AND createTime=?");
            statement.setString(1,userName);
            statement.setString(2,time);
            ResultSet set=statement.executeQuery();
            if(set.next()){
                reWriteNote(set.getString(1),noteContent);
                return 0;
            }
            else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Note getNote(String userName,String time){
        if(conn==null)loadDatabase();
        Note note;
        try {
            PreparedStatement statement=conn.prepareStatement("SELECT * FROM PryoData.PryoNotes WHERE user=? AND createTime=?");
            statement.setString(1,userName);
            statement.setString(2,time);
            ResultSet set=statement.executeQuery();
            if(set.next()){
                String noteContent=String.copyValueOf(readNoteFile(set.getString(3)));
                note=new Note(set.getString(4));
                note.setNoteContent(noteContent);
                return note;
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Note getNote(String userName,int order){
        Vector<Note> noteList=getNoteList(userName);
        if(noteList!=null)return getNote(userName,noteList.elementAt(order).getTime());
        else return null;
    }

    public static int rmNote(String userName,String time){
        if(conn==null)loadDatabase();
        System.out.println("DO L158:删除笔记:"+userName+":"+time);
        try {
            PreparedStatement statement0 = conn.prepareStatement("SELECT * FROM PryoData.PryoNotes WHERE user=? AND createTime=?");
            statement0.setString(1,userName);
            statement0.setString(2,time);
            ResultSet re=statement0.executeQuery();
            if(re.next()){
                PreparedStatement statement1 = conn.prepareStatement("DELETE FROM PryoData.PryoNotes WHERE user=? AND createTime=?");
                statement1.setString(1,userName);
                statement1.setString(2,time);
                statement1.executeUpdate();
                deleteNoteFile(re.getString(3));
                return 0;
            }else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int rmNote(String userName,int order){
        Vector<Note> noteList=getNoteList(userName);
        if(noteList!=null)return rmNote(userName,noteList.elementAt(order).getTime());
        else return -1;
    }

    public static char[] readNoteFile(String path){
        File note=new File(path);
        Vector<Character> chars=new Vector<Character>();
        try {
            FileReader reader=new FileReader(note);
            int c;
            while((c=reader.read())!=-1){
                chars.add((char)c);
            }
            char[] noteContent=new char[chars.size()];
            int i=0;
            for (char w:chars){
                noteContent[i]=w;
                i++;
            }
            reader.close();
            return noteContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void reWriteNote(String path,String noteContent){
        File noteFile=new File(path);
        try(FileWriter writer =new FileWriter(noteFile);BufferedWriter buf=new BufferedWriter(writer)) {
            boolean noteFileCreateRe=noteFile.createNewFile();
            buf.write("");
            buf.flush();
            buf.write(noteContent);
            buf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean interruptSocket(Socket socket){
        try {
            socket.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteNoteFile(String path){
        File noteFile=new File(path);
        return noteFile.delete();
    }


}
