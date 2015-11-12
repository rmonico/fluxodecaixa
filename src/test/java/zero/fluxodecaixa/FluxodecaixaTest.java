package zero.fluxodecaixa;

import zero.easymvc.ormlite.factory.AbstractApplicationFactory;
import zero.utils.test.AbstractEasyMVCOrmliteTest;

public class FluxodecaixaTest extends AbstractEasyMVCOrmliteTest {

    public FluxodecaixaTest() {
        this((String[]) null);
    }

    public FluxodecaixaTest(String... defaultDBUnitDatasetFileNames) {
        super("%%HOME%%/.config/fluxodecaixa_test", defaultDBUnitDatasetFileNames);
    }

    @Override
    protected AbstractApplicationFactory createApplicationFactory() {
        return new FluxodecaixaTestApplicationFactory();
    }

    @Override
    protected AbstractApplicationFactory createApplicationFactory(int databaseVersion) {
        return new FluxodecaixaTestApplicationFactory(databaseVersion);
    }

}
