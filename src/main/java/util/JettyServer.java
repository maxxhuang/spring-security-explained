package util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *     <bean id="jettyWebContext" class="org.eclipse.jetty.webapp.WebAppContext">
 <property name="contextPath" value="/demo" />
 <property name="descriptor" value="src/main/resources/web/web.xml" />
 <property name="resourceBase" value="web/content" />
 </bean>

 <bean id="jettyServer" class="org.eclipse.jetty.server.Server"
 init-method="start" destroy-method="stop">
 <constructor-arg value="8080" />
 <property name="handler" ref="jettyWebContext" />
 </bean>
 */
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
