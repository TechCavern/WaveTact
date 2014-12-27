package com.techcavern.wavetact.console;

import com.techcavern.wavetact.console.utils.CommandVariables;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.objects.CommandLine;
import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * A crude implementation of a console server.
 */
public class ConsoleServer implements Runnable {

	public boolean keepConsoleRunning = true;
	public boolean keepConnectionRunning = true;


	@Override
	public void run() {
		File socketFile = new File("./console.unixsocket");
		socketFile.deleteOnExit();
		try {
			AFUNIXServerSocket server = AFUNIXServerSocket.newInstance();
			server.bind(new AFUNIXSocketAddress(socketFile));

			while ((!Thread.interrupted()) && keepConsoleRunning) {
				System.err.println("Waiting for connection on console socket...");
				Socket sock = server.accept();
				try {
					System.err.println("Connected: " + sock);

					InputStream is = sock.getInputStream();
					OutputStream os = sock.getOutputStream();

					keepConnectionRunning = true;
					while (keepConnectionRunning) {
						os.write("WaveTact> ".getBytes());
						os.flush();

						byte[] buf = new byte[512];
						int read = is.read(buf);
						String input = new String(buf, 0, read);
						//Strip the newline.
						if (input.endsWith("\n"))
							input = input.substring(0, input.length() - 1);

						CommandVariables commandVariables = new CommandVariables(is, os);
						parseCommandLineArguments(input.split(" "), commandVariables);
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

	public static void parseCommandLineArguments(String[] args, CommandVariables commandVariables) {
		boolean invalid = true;
		for (CommandLine c : Registry.CommandLineArguments) {
			for (String b : c.getArgument()) {
				for (String s : args) {

					if (s.equalsIgnoreCase(b)) {
						c.doAction(args, commandVariables);
						invalid = false;
					}
				}
			}
		}

		if (invalid)
			for (CommandLine c : Registry.CommandLines) {
				for (String b : c.getArgument()) {

					if (args[0].equalsIgnoreCase(b)) {
						c.doAction(args, commandVariables);
						invalid = false;

					}
				}
			}
		if (invalid)
			commandVariables.getPrintStream().println("Invalid command.");
	}

}
