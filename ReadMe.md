
    ClassPathXmlApplicationContext
        构造方法
        AbstractApplicationContext.refresh : 刷新spring容器(删除原有容器， 创建新容器)
            #obtainFreshBeanFactory
            ### BeanFactory的初始化流程
                refreshBeanFactory： 方法为抽象方法， 有不同的实现类
                AbstractRefreshableApplicationContext.refreshBeanFactory： 创建BeanFactory(DefaultListableBeanFactory)
                    AbstractRefreshableApplicationContext.loadBeanDefinitions ： 通过resource定位xml资源， 加载到DefaultListableBeanFactory中
                        AbstractXmlApplicationContext.loadBeanDefinitions : 专门月度xml文件的阅读器， 用来加载xml的BeanDefinition
                        
                            AbstractBeanDefinitionReader.loadBeanDefinitions： 通用的resource阅读器， 通过加载并读取BeanDefinition信息
                            doLoadBeanDefinitions
                                XmlBeanDefinitionReader.registerBeanDefinitions： 专门阅读xml配置文件的阅读器， 用来加载BeanDefinition对象的
                                    DefaultBeanDefinitionDocumentReader.doRegisterBeanDefinitions： 
                                        DefaultBeanDefinitionDocumentReader.parseBeanDefinitions ： 默认使用spring-bean规范的xml， bean标签、 import标签 、 alias标签， 则使用默认解析规则
                                            DefaultBeanDefinitionDocumentReader.processBeanDefinition ： 解析bean标签
                                            
                                                BeanDefinitionParserDelegate.parseBeanDefinitionElement： 委托给该类， 进行BeanDefinition的具体解析
                                                    parseBeanDefinitionElement：解析bean标签， 获取BeanDefinition对象
                                                        createBeanDefinition： 创建BeanDefinition对象GenericBeanDefinition
                                                        parsePropertyElements： 解析properties标签
                                                            
总结： 
ClassPathXmlApplicationContext
AbstractApplicationContext
AbstractRefreshableApplicationContext
AbstractXmlApplicationContext

DefaultBeanDefinitionDocumentReader
XmlBeanDefinitionReader

BeanDefinitionParserDelegate： 