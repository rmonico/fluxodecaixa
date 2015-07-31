package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class EasyMVCAssert {

    public static void assertBeanList(List<Object> beans, int size) {
        assertNotNull(beans);
        assertEquals(size, beans.size());
    }

    @SuppressWarnings("unchecked")
    public static <T> T assertAndGetBean(List<Object> beans, int index, Class<T> beanClass) {
        Object bean = beans.get(index);
    
        assertNotNull(bean);
    
        assertTrue(beanClass.isAssignableFrom(bean.getClass()));
    
        return (T) bean;
    }

}
