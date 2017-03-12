package util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {
    public static void run() {
        WebAppContext webContext = new WebAppContext();
        webContext.setContextPath("/demo");
        webContext.setDescriptor("src/main/resources/web/web.xml");
        webContext.setResourceBase("web/content");

        Server server = new Server(8080);
        server.setHandler(webContext);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { server.stop(); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}
