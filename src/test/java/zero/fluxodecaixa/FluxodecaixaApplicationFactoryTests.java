package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import zero.environment.ApplicationPropertyKeys;
import zero.environment.Environment;

public class FluxodecaixaApplicationFactoryTests extends FluxodecaixaTest {

    @Test
    public void should_default_properties_be_set() {
        String appHomeFolder = Environment.get().getProperty(ApplicationPropertyKeys.APP_HOME_PROPERTY_KEY);

        assertEquals(FluxodecaixaApplicationFactory.EXECUTABLE_MAJOR_VERSION, appProperties.getProperty(ApplicationPropertyKeys.EXECUTABLE_MAJOR_VERSION_PROPERTY_KEY));
        assertEquals(FluxodecaixaApplicationFactory.EXECUTABLE_MINOR_VERSION, appProperties.getProperty(ApplicationPropertyKeys.EXECUTABLE_MINOR_VERSION_PROPERTY_KEY));
        assertEquals(FluxodecaixaApplicationFactory.EXECUTABLE_PROJECT_PHASE, appProperties.getProperty(ApplicationPropertyKeys.EXECUTABLE_PROJECT_PHASE_PROPERTY_KEY));
        assertEquals(String.format("jdbc:sqlite:%s/database.sqlite", appHomeFolder), Environment.get().getProperty(ApplicationPropertyKeys.JDBC_URL_KEY));
        assertEquals("org.sqlite.JDBC", Environment.get().getProperty(ApplicationPropertyKeys.JDBC_DRIVER_CLASS_KEY));
    }
}
