package com.idega.app.eplatform.headless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.adaptor.EclipseStarter;

public class Bootstrap {

  private volatile boolean running;
  private int adminPort = 9005; // default port

  public static void main(String[] args) {
    Bootstrap runner = new Bootstrap();
    try {
      runner.start();
    } catch (Exception e) {
      System.exit(2);
    } finally {
      try {
        runner.shutDown();
      } catch (Exception e) {
        System.exit(2);
      }
    }
    System.exit(0);
  }

  private void start() throws Exception {
    startEquinox();
    this.running = true;
    startAdmin();
  }

  public void shutDown() throws Exception {
    this.running = false;
    EclipseStarter.shutdown();
  }

  public boolean status() {
    return EclipseStarter.isRunning();
  }

  private void startEquinox() throws Exception {
    Map<String, String> props = new HashMap<String, String>();
    props.put("eclipse.ignoreApp", "true");
    props.put("osgi.noShutdown", "true");
    EclipseStarter.setInitialProperties(props);
    EclipseStarter.startup(new String[] {}, null);
  }

  private void startAdmin() throws Exception {
    ServerSocket ss = new ServerSocket(adminPort);
    while (running) {
      Socket sock = ss.accept();
      InputStream in = sock.getInputStream();
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(in));
      String command = reader.readLine();
      OutputStream out = sock.getOutputStream();
      if ("SHUTDOWN".equals(command)) {
        sendResponse(out, "shutting down");
        shutDown();
        sendResponse(out, "stopped");
      } else if ("STATUS".equals(command)) {
        String response = (status() ? "running" : "not running");
        sendResponse(out, response);
      }
      reader.close();
      in.close();
      out.close();
    }
  }

  private void sendResponse(OutputStream out, String response)
      throws IOException {
    PrintWriter writer = new PrintWriter(out);
    writer.println(response);
    writer.flush();
  }

}