ApplicationContext 中的ioc容器的初始化流程： 
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

BeanDefinitionParserDelegate： 具体的有bean标签解析成BeanDefinition对象是有该对象完成的

## bean的创建流程
    AbstractBeanFactory.getBean
        #doGetBean： 去获取缓存中的bean或者创建bean
            DefaultSingletonBeanRegistry#getSingleton： 从三级缓存中获取bean实例 --- 牵扯到循环依赖的问题
                AbstractAutowireCapableBeanFactory.createBean:  创建bean实例、 属性填充、 初始化
                    AbstractAutowireCapableBeanFactory.doCreateBean:  真正干活的
                        AbstractAutowireCapableBeanFactory.createBeanInstance： 1. 创建bean对象
                        DefaultSingletonBeanRegistry.addSingletonFactory: 解决循环依赖， 也是对象存储到三级缓存中  -- 循环依赖的解决
                        AbstractAutowireCapableBeanFactory.populateBean ： 2. 属性的填充
                        AbstractAutowireCapableBeanFactory.initializeBean ： 3. bean的初始化 
                        
            #getObjectForBeanInstance --- 牵扯到的是BeanFactory和FactoryBean的面试问题
## BeanFactory和FactoryBean的区别
    BeanFactory是一个工厂
    FactoryBean是一个对象， 该对象有生成另一个对象的能力
    
## aop源码入口： 
DefaultBeanDefinitionDocumentReader.parseBeanDefinitions
    BeanDefinitionParserDelegate.getNamespaceURI： 获取命名空间URI http://www.springframework.org/schema/aop
        DefaultNamespaceHandlerResolver.resolve : 获取的是NamespaceHandler
            DefaultNamespaceHandlerResolver.getHandlerMappings ： 获取所有的命名空间和对应的NamespaceHandler对应关系
            AopNamespaceHandler.init ： 去注册冰倩名称和BeanDefinitionParse之间的对应关系
        NamespaceHandlerSupport.parse ； 使用对应的NamespaceHandler去加载BeanDefinition信息
            NamespaceHandlerSupport.findParserForElement： 获取指定自定义标签对应的BeanDefinitionParser
    ConfigBeanDefinitionParser.parse：专门用来解析aop:config标签的BeanDefinitionParser
        ConfigBeanDefinitionParser.parsePointcut ： 解析切入点对象AspectJExpressionPointcut
        ConfigBeanDefinitionParser.parseAdvisor ： 里面主要使用的是spring aop， 产生一个DefaultBeanFactoryPointcutAdvisor的BeanDefinition对象， 并注册
        ConfigBeanDefinitionParser.parseAspect ： 里面主要使用的是aspectJ整合spring实现的 aop, 产生的对象就多了
            ConfigBeanDefinitionParser.parseAdvice ： 产生了五个advice对应的BeanDefinition对象和一个advisor对应的BeanDefinition对象
                MethodLocatingFactoryBean对应的BeanDefinition（讲MyAdvice中的before等方法， 封装成Method对象）
                SimpleBeanFactoryAwareAspectInstanceFactory ： 对应的BeanDefinition(生产自定义的MyAdvice对象)
    
    
    
    
## InstantiationAwareBeanPostProcessor 后置处理器