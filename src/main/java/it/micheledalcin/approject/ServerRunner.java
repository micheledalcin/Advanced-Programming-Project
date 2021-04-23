package it.micheledalcin.approject;

import java.io.IOException;

/*
        Server that performs mathematical operations on Strings received from a Client
        and respond either with an ERR or an OK response

        -USAGES EXAMPLES:

        MAX_GRID;x0:-1:0.1:1,x1:-10:1:20;((x0+(2.0^x1))/(21.1-x0));(x1*x0)
        COUNT_LIST;x0:1:0.001:100;x1
        STAT_MAX_TIME
        BYE

 */

public class ServerRunner {
    public static void main(String[] args) throws IOException {

        if (args.length != 0) {
            int port;
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Port number not parsable");
                return;
            }
            System.out.println("--- STARTING SERVER ---");
            LineProcessingServer server = new LineProcessingServer(port, "BYE", new CommandExecutor());
            server.run();
        }
        else {
            System.out.println("--- STARTING SERVER ---");
            LineProcessingServer server = new LineProcessingServer(1050, "BYE", new CommandExecutor());
            server.run();
        }
    }
}
