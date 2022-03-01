package com.sshproject;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Session session = null;
        ChannelExec channel = null;
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Introduzca la dirección ip a la que se quiere conectar");
            String ip = sc.next();
            System.out.println("Introduzca el puerto de la máquina a la que se quiere conectar");
            int puerto = sc.nextInt();
            System.out.println("Introduzca su nombre de usuario");
            String username = sc.next();
            System.out.println("Introduzca su contraseña");
            String password = sc.next();
            System.out.println("¿Cómo se llama el fichero que quieres ver?");
            String fichero = sc.next();

            session = new JSch().getSession(username, ip, puerto);

            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand("cat /var/log/" + fichero);

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream((responseStream));
            channel.connect();

            while (channel.isConnected()){
                Thread.sleep(100);
            }

            String responseString = new String(responseStream.toByteArray());
            System.out.println(responseString);


        } catch (JSchException | InterruptedException e) {
            e.printStackTrace();
        } finally{
            if (session != null){
                session.disconnect();
            }
            if(channel != null){
                channel.disconnect();
            }
        }

    }
}
