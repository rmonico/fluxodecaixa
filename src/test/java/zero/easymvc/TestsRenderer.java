package zero.easymvc;

public class TestsRenderer {

    @Renderer
    public void render(TestBean bean) {
        bean.setOk(true);
    }
}
