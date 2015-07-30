package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;

public class AbstractEasyMVCTest {

    protected EasyMVC controller;

    @Before
    public void before() {
        controller = new EasyMVC();
    }

    public static void assertBeanList(List<Object> beans, int size) {
        assertNotNull(beans);
        assertEquals(size, beans.size());
    }

    @SuppressWarnings("unchecked")
    public static <T> T assertAndGetBean(List<Object> beans, int index, Class<T> beanClass) {
        Object bean = beans.get(index);

        assertTrue(beanClass.isAssignableFrom(bean.getClass()));

        return (T) bean;
    }

}