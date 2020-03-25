package com.zhysunny.spring.beans;

import com.zhysunny.spring.beans.lookup_method.AbstractLookupMethod;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

/**
 * ElementBeanTest Test.
 * @author 章云
 * @date 2020/3/25 14:20
 */
public class ElementBeanTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test ElementBeanTest Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test ElementBeanTest Class End...");
    }

    @Test
    public void testMeta() throws Exception {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("beans/ElementBeanTest.xml");
        TestBean bean = ac.getBean("testMeta", TestBean.class);
        System.out.println(bean);
        Object attribute = ac.getBeanFactory().getBeanDefinition("testMeta").getAttribute("meta_key");
        assertEquals(attribute, "meta_value");
        System.out.println(attribute);
    }

    @Test
    public void testScopeSingleton() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans/ElementBeanTest.xml");
        TestBean bean1 = ac.getBean("testScopeSingleton", TestBean.class);
        TestBean bean2 = ac.getBean("testScopeSingleton", TestBean.class);
        assertEquals(bean1, bean2);
    }

    @Test
    public void testScopePrototype() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans/ElementBeanTest.xml");
        TestBean bean1 = ac.getBean("testScopePrototype", TestBean.class);
        TestBean bean2 = ac.getBean("testScopePrototype", TestBean.class);
        assertNotEquals(bean1, bean2);
    }

    @Test
    public void testLookupMethod() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans/ElementBeanTest.xml");
        AbstractLookupMethod testLookupMethod = ac.getBean("testLookupMethod", AbstractLookupMethod.class);
        testLookupMethod.show();
    }

    @Test
    public void testReplacedMethod() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans/ElementBeanTest.xml");
        User user = ac.getBean("testReplacedMethod", User.class);
        user.show();
    }

}
