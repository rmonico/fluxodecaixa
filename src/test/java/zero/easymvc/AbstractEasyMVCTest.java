package zero.easymvc;

import org.junit.Before;

public class AbstractEasyMVCTest {

    protected EasyMVC controller;

    @Before
    public void before() {
        controller = new EasyMVC();
    }

}