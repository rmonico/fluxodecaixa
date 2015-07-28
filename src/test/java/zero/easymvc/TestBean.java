package zero.easymvc;

public class TestBean {

    public static boolean commandRan;
    public static boolean rendererRan;
    public static int instanceCount;

    public TestBean() {
        instanceCount++;
    }

    public static void reset() {
        instanceCount = 0;
        commandRan = false;
        rendererRan = false;
    }

}
