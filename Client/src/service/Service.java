package service;

import javafx.application.Platform;
import note.Note;
import protocol.ClientPacketProcessor;
import protocol.Request;
import protocol.Response;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

public class Service extends AbstractService{
    //已登录成功的用户名在该类中
    Socket socket;
    ClientPacketProcessor processor;
    boolean[] noteLoaded=new boolean[100];//标识该笔记是否已经被缓存过。
    public static Vector<Note> orderToNote; //建立一个order对note映射。

    public Service() throws IOException{
        start();
    }

    @Override
    public Vector<Note> login(String userName, String password) {
        orderToNote=null;
        Request request=new Request(0,userName);
        request.setPassword(password);
        processor.sendRequest(request);
        Response response=processor.receiveResponse();
        int re=response.getOperator();
        String certifiedUserName=response.getUserName();//验证返回的响应是否是回应当前用户的请求。
        if(re==0 && certifiedUserName.equals(userName)){
            System.out.println(userName+"登录成功，返回笔记列表");
            loggedIn=true;
            loggedUser=userName;
            orderToNote=response.getNotesVector();
            return orderToNote;
        }else{
            System.out.println(userName+"登录失败");
            return null;
        }
    }

    @Override
    public int register(String userName, String password) {
        orderToNote=null;
        Request request=new Request(1,userName);
        request.setPassword(password);
        processor.sendRequest(request);
        Response response=processor.receiveResponse();
        int re=response.getOperator();
        String certifiedUserName=response.getUserName();//验证返回的响应是否是回应当前用户的请求。
        if(re==1 && certifiedUserName.equals(userName)){
            System.out.println(userName+"注册成功");
            loggedIn=true;
            loggedUser=userName;
            orderToNote=new Vector<Note>();
            return 0;
        }else if(re==-2){
            System.out.println(userName+"注册失败，服务器内部错误，数据库错误");
            return -1;
        }else{
            System.out.println(userName+"注册失败，用户已存在");
            return -2;
        }
    }

    @Override
    public Vector<Note> getNoteNameList() {
        if(!loggedIn)return null;
        Request request=new Request(6,loggedUser);
        processor.sendRequest(request);
        Response response=processor.receiveResponse();
        int re=response.getOperator();
        String certifiedUserName=response.getUserName();//验证返回的响应是否是回应当前用户的请求。
        if(re==6 && certifiedUserName.equals(loggedUser)){
            System.out.println("成功获取到"+loggedUser+"的笔记名列表");
            return response.getNotesVector();
        }else {
            System.out.println("无法获取到"+loggedUser+"的笔记名列表");
            return null;
        }
    }

    @Override
    //获得笔记时可能会出现快速操作导致的Response堵塞
    //每次获取笔记都要新建一个线程，否则会造成阻塞。可能会有对个请求同时发生。
    public void getNote(int order) {//order从0开始
        if(!loggedIn)return ;
        if(noteLoaded[order])return ;
        Request request=new Request(7,loggedUser);
        request.setOrder(order);
        processor.sendRequest(request);
        Response response=processor.receiveResponse();
        int re=response.getOperator();
        String certifiedUserName=response.getUserName();//验证返回的响应是否是回应当前用户的请求。
        if(re==2 && certifiedUserName.equals(loggedUser)){
            System.out.println("成功获取到"+order+orderToNote.get(order).getNoteName()+"笔记");
            orderToNote.get(order).setNoteContent(response.getNote().getNoteContent());
            noteLoaded[order]=true;
        }else{
            System.out.println("无法获取到"+order+"笔记");
        }
    }

    @Override
    public boolean createNote(String name) {
        if(!loggedIn)return false;
        Request request=new Request(4,loggedUser);
        Note newNote=new Note(name,getCurrentTime());
        request.setNote(newNote);
        processor.sendRequest(request);
        Response response=processor.receiveResponse();
        int re=response.getOperator();
        if(re==4){
            System.out.println("成功添加笔记"+name);
            orderToNote.add(orderToNote.size(),newNote);
            return true;
        }else{
            System.out.println("无法添加笔记"+name);
            return false;
        }
    }

    @Override
    public boolean deleteNote(int order) {
        for(int i=order;i<orderToNote.size();i++){
            noteLoaded[i]=noteLoaded[i+1];
        }
        if(!loggedIn)return false;
        Request request=new Request(8,loggedUser);
        request.setOrder(order);
        orderToNote.remove(order);
        processor.sendRequest(request);
        int re=processor.receiveResponse().getOperator();
        if(re==8){
            System.out.println("笔记"+order+"删除成功");
            return true;
        }else{
            System.out.println("笔记"+order+"删除失败");
            return false;
        }
    }

    @Override
    public boolean saveNote(String content,int order) {
        if(!loggedIn)return false;
        Request request=new Request(3,loggedUser);
        Note note=new Note(orderToNote.get(order).getNoteName(),orderToNote.get(order).getTime());
//        Note note=orderToNote.get(order); 为什么这里只可以进行第一次发送，第二次发送会导致
        //猜测：有可能是因为这里所发送的Note对象，在服务器那边被识别为已经存在的Note对象
        orderToNote.set(order,note);
        note.setNoteContent(content);
        request.setNote(note);
        System.out.println("Service L143 笔记存储内容："+request.getNote().getNoteContent());
        processor.sendRequest(request);
        Response response=processor.receiveResponse();
        int re=response.getOperator();
        if(re==3){
            System.out.println("笔记"+order+"保存成功");
            return true;
        }else{
            System.out.println("Response:"+re);
            System.out.println("笔记"+order+"保存失败");
            return false;
        }
    }

    @Override
    public boolean quit() {
        if(!loggedIn)return false;
        System.out.println(loggedUser+"下线成功");
        orderToNote.clear();
        loggedIn=false;
        loggedUser=null;
        return true;
    }

    public boolean interrupt(){
        Request request=new Request(9,null);
        processor.sendRequest(request);
        Response response=processor.receiveResponse();
        int re=response.getOperator();
        if(re==7){
            System.out.println(socket.getLocalPort()+"断开成功");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else{
            System.out.println(socket.getLocalPort()+"断开失败");
            return false;
        }
    }



    @Override
    public void start() throws IOException{


//        try {
            socket=new Socket("127.0.0.1",9001);//YOUR SERVER IP
            socket.setSoTimeout(5000);
            processor=new ClientPacketProcessor(socket);
            for(int i=0;i<noteLoaded.length;i++)noteLoaded[i]=false;
//        } catch (IOException e) {
//            System.out.println("服务器连接失败");
//            e.printStackTrace();
//            Platform.exit();
//            System.exit(-1);
            //服务器连接失败，抛出异常，界面提示无法连接服务器稍后再试并且退出
//        }



    }

    public String getCurrentTime(){
        String currentTime;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(Calendar.getInstance().getTime());
    }

    public HashMap<Integer,Note> vectorConvertToMap(Vector<Note> notes){
        HashMap<Integer,Note> noteMap=new HashMap<>();
        for (Note w:notes){
            noteMap.put(notes.indexOf(w),w);
        }
        return noteMap;
    }


}
