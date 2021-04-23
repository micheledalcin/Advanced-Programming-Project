package it.micheledalcin.approject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LineProcessingServer {

    protected final int port;
    protected final String quitCommand;
    protected final Date date;
    protected final CommandProcessor commandProcessor;
    public static List<Double> okResponseTimes = new LinkedList<>();


    public LineProcessingServer(int port, String quitCommand, CommandProcessor commandProcessor) {
        this.port = port;
        this.quitCommand = quitCommand;
        this.commandProcessor = commandProcessor;
        this.date = new Date();
    }

    public int getPort() { return this.port; }

    public String getQuitCommand() { return this.quitCommand; }

    public void run() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            printToServer("SERVER STARTED AT PORT: " + this.getPort() + ". Use command \"" + this.getQuitCommand() + "\" to quit");
            while (true) {
                final Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clientHandler.start();
            }
        }
    }

    protected static void printToServer(String string) {
        Date date = new Date();
        System.out.println("["+date.toString()+"] "+string);
    }
}
