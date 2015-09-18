package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import zero.environment.ApplicationPropertyKeys;

public class FluxodecaixaApplicationFactoryTests extends FluxodecaixaTest {

    @Test
    public void should_default_properties_be_set() {
        assertEquals(FluxodecaixaTestApplicationFactory.DEFAULT_TEST_JDBC_URL, appProperties.getProperty(ApplicationPropertyKeys.JDBC_URL_KEY));
        assertEquals(FluxodecaixaTestApplicationFactory.DEFAULT_TEST_JDBC_DRIVER_CLASS, appProperties.getProperty(ApplicationPropertyKeys.JDBC_DRIVER_CLASS_KEY));

        assertEquals(FluxodecaixaApplicationFactory.EXECUTABLE_MAJOR_VERSION, appProperties.getProperty(ApplicationPropertyKeys.EXECUTABLE_MAJOR_VERSION_PROPERTY_KEY));
        assertEquals(FluxodecaixaApplicationFactory.EXECUTABLE_MINOR_VERSION, appProperties.getProperty(ApplicationPropertyKeys.EXECUTABLE_MINOR_VERSION_PROPERTY_KEY));
        assertEquals(FluxodecaixaApplicationFactory.EXECUTABLE_PROJECT_PHASE, appProperties.getProperty(ApplicationPropertyKeys.EXECUTABLE_PROJECT_PHASE_PROPERTY_KEY));
    }
}
