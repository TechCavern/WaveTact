package info.techcavern.wavetact.console;

import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.Registry;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConsoleServer implements Runnable {

    public boolean keepConsoleRunning = true;
    public boolean keepConnectionRunning = true;

    public static void parseCommandLineArguments(String[] args, CommandIO commandIO) {
        try {
            String command = args[0].toLowerCase();
            args = ArrayUtils.remove(args, 0);
            ConsoleCommand cmd = Registry.consoleCommands.get(command);
            if (cmd != null) {
                cmd.onCommand(command, args, commandIO);
            } else {
                commandIO.getPrintStream().print("Invalid Command");
            }
        } catch (Exception e) {
            commandIO.getPrintStream().print("Failed to execute command");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        File socketFile = new File("./console.unixsocket");
        socketFile.deleteOnExit();
        try {
            AFUNIXServerSocket server = AFUNIXServerSocket.newInstance();
            server.bind(new AFUNIXSocketAddress(socketFile));

            while ((!Thread.interrupted()) && keepConsoleRunning) {
                System.err.println("Waiting for connection on consoleCommands socket...");
                Socket sock = server.accept();
                try {
                    System.err.println("Connected: " + sock);

                    InputStream is = sock.getInputStream();
                    OutputStream os = sock.getOutputStream();

                    keepConnectionRunning = true;
                    while (keepConnectionRunning) {
                        os.write("\nWaveTact> ".getBytes());
                        os.flush();

                        byte[] buf = new byte[512];
                        int read = is.read(buf);
                        if (read < 0) {
                            keepConnectionRunning = false;
                            break;
                        } else {
                            String input = new String(buf, 0, read);
                            if (input.endsWith("\n"))
                                input = input.substring(0, input.length() - 1);
                            CommandIO commandIO = new CommandIO(is, os);
                            parseCommandLineArguments(input.split(" "), commandIO);
                        }
                    }
                    os.close();
                    is.close();

                    sock.close();
                } catch (IOException e) {
                    System.err.println("Received IOException: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("Console server terminated. Shutting down...");
        Registry.WaveTact.stop();
        socketFile.delete();
        System.exit(0);
    }

}
