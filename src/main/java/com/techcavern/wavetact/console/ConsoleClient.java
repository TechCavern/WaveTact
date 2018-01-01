package com.techcavern.wavetact.console;

import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.newsclub.net.unix.AFUNIXSocketException;

import java.io.*;
import java.util.Scanner;

public class ConsoleClient {
    public static void go() throws Exception {
        AFUNIXSocket sock = AFUNIXSocket.newInstance();
        try {
            sock.connect(new AFUNIXSocketAddress(new File("./console.unixsocket")));
        } catch (AFUNIXSocketException e) {
            System.out.println("Cannot connect to server. Have you started it?");
            System.out.flush();
            throw e;
        }
        System.out.println("");
        System.out.println("Connected to WaveTact!");
        System.out.println("");

        InputStream is = sock.getInputStream();
        OutputStream os = sock.getOutputStream();

        Thread inputThread = new Thread() {
            @Override
            public void run() {
                PrintStream ps = new PrintStream(os, true);
                Scanner scanner = new Scanner(System.in);
                while (!Thread.interrupted()) {
                    if (scanner.hasNextLine())
                        ps.println(scanner.nextLine());
                    else
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
        };
        inputThread.start();

        try {
            Scanner scanner = new Scanner(is);
            while (sock.isConnected()) {
                byte[] buf = new byte[512];
                int read = is.read(buf);
                if (read < 0)
                    break;
                else {
                    String input = new String(buf, 0, read);
                    System.out.print(input);
                }
                Thread.sleep(100);
            }

        } catch (IOException e) {

        }

        inputThread.interrupt();
        os.close();
        is.close();

        sock.close();

        System.out.println("Connection Terminated.");

        System.exit(0);
    }
}
