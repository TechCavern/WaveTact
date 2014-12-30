package com.techcavern.wavetact.utils.consoleUtils;

import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.objects.CommandIO;
import com.techcavern.wavetact.utils.objects.ConsoleCommand;
import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * A crude implementation of a consoleCommands server.
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
				System.err.println("Waiting for connection on consoleCommands socket...");
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
						if (read < 0) {
							keepConnectionRunning = false;
							break;
						}
						else
						{
							String input = new String(buf, 0, read);
							//Strip the newline.
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
	public static void parseCommandLineArguments(String[] args, CommandIO commandIO) {
		boolean invalid = true;
			for (ConsoleCommand c : Registry.ConsoleCommands) {
				for (String b : c.getCommandID()) {
					if (args[0].equalsIgnoreCase(b)) {
						c.onCommand(args, commandIO);
						invalid = false;
					}
				}
			}
		if (invalid)
			commandIO.getPrintStream().println("Invalid command.");
	}

}
