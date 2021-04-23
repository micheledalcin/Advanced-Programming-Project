package it.micheledalcin.approject;

import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final LineProcessingServer server;

    public ClientHandler(Socket socket, LineProcessingServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (socket) {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            LineProcessingServer.printToServer("Connection from " + socket.getInetAddress());
            while (true) {
                String line = br.readLine();
                final double start = System.currentTimeMillis();
                if (line == null) {
                    LineProcessingServer.printToServer("Client " + socket.getInetAddress() + " suddenly closed connection");
                    break;
                }
                if (line.equals(server.getQuitCommand())) {
                    LineProcessingServer.printToServer("Client " + socket.getInetAddress() + " disconnected");
                    break;
                }
                String response = (String) server.commandProcessor.process(line);
                if (response.startsWith("ERR")) {
                    bw.write(response);
                    bw.flush();
                }
                else {
                    final double end = System.currentTimeMillis();
                    final double timeUsed = end - start;
                    String stringTimePassed = (new DecimalFormat("##0,000")).format(timeUsed);
                    String okResponse = "OK;" + stringTimePassed + ";" + response + System.lineSeparator();
                    LineProcessingServer.okResponseTimes.add(timeUsed/1000);
                    bw.write(okResponse);
                    bw.flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

}