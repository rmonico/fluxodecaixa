package zero.easymvc;

public class TestsRenderer {

    @Renderer
    public void render(TestBean bean) {
        TestBean.rendererRan = true;
    }
}
