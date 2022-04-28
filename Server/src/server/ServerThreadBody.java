package server;

import database.DataOperator;
import note.Note;
import protocol.Request;
import protocol.Response;
import protocol.ServerPacketProcessor;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Vector;

public class ServerThreadBody implements Runnable{
    Socket client;
    ServerPacketProcessor processor;
//    String currentUser;
    boolean noInterrupted=true;

    public ServerThreadBody(Socket client){
        this.client=client;
        try {
            processor=new ServerPacketProcessor(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(noInterrupted){
            Request request;
            try {
                request = processor.receiveRequest();
            }catch(Exception e){
                System.out.println("异常：流已断开");
                break;
            }
            Response response = parseRequest(request);
            processor.sendResponse(response);
        }
        DataOperator.interruptSocket(client);
    }

    public Response parseRequest(Request request){
        int operator=request.getOperator();
        String userName=request.getUser();
        Response response;
        switch (operator) {
            case 0: {
                String password=request.getPassword();
                int re=DataOperator.login(userName,password);
                if(re==0){
                    response=new Response(0,userName);
                    response.setNotesVector(DataOperator.getNoteList(userName));
                }else response=new Response(-1,userName);
                return response;
            }
            case 1:{
                String regPassword=request.getPassword();
                int re=DataOperator.register(userName,regPassword);
                if(re==0) response = new Response(1, userName);
                else if(re==-1) response = new Response(-2, userName);
                else response = new Response(-3, userName);
                return response;
            }
            case 2:{
                Note note=request.getNote();
//                String user =note.getUser();
                String time=note.getTime();
                Note reNote=DataOperator.getNote(userName,time);
                if(reNote!=null){
                    response=new Response(2,userName);
                    response.setNote(reNote);
                }else{
                    response=new Response(-4,userName);
                }
                return response;
            }
            case 3:{
                Note note=request.getNote();
                System.out.println("Server L74:"+note.getNoteContent());
                String noteContent=note.getNoteContent();
                System.out.println("L74保存笔记："+ noteContent);
                String noteTime=note.getTime();
                System.out.println("L75保存笔记时间："+noteTime);
                int re=DataOperator.saveNote(userName,noteTime,noteContent);
                System.out.println("L77保存Response:"+re);
                if(re==0)response=new Response(3,userName);
                else response=new Response(-5,userName);
                return response;
            }
            case 4:{
                Note noteKit=request.getNote();
                String noteTime=noteKit.getTime();
                String noteName=noteKit.getNoteName();
                int re=DataOperator.addNote(userName,noteName,noteTime);
                if(re==0)response=new Response(4,userName);
                else response=new Response(-6,userName);
                return response;
            }
            case 5:{
            }
            case 6:{
                Vector<Note> noteList=DataOperator.getNoteList(userName);
                if(noteList!=null){
                    response=new Response(6,userName);
                    response.setNotesVector(noteList);
                }else response=new Response(-8,userName);
                return response;
            }
            case 7:{
                int order=request.getOrder();
                System.out.println(userName+"获取"+order+"笔记");
                Note reNote=DataOperator.getNote(userName,order);
                if(reNote!=null){
                    response=new Response(2,userName);
                    response.setNote(reNote);
                }else{
                    response=new Response(-4,userName);
                }
                return response;
            }
            case 8:{
                int order=request.getOrder();
                int re=DataOperator.rmNote(userName,order);
                if(re==0)response=new Response(8,userName);
                else response=new Response(-5,userName);
                return response;
            }
            case 9: {
                noInterrupted = false;
                response = new Response(7, userName);
                return response;
            }
            default:return new Response(-10,userName);
        }
    }




}
