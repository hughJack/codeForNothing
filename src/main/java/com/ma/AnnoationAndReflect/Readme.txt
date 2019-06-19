

	在 Java 程序中许多对象在运行是都会出现两种类型：编译时类型和运行时类型。

	编译时的类型由声明对象时实用的类型来决定，
	运行时的类型由实际赋值给对象的类型决定 。
    如：
      Person p = new Student();
      其中编译时类型为 Person，运行时类型为 Student。

1.在进行spring开发的时候看见---进行构造器注入的时候，若提供了有参的构造方法
  则无参数的构造方法必须人为提供，否则系统不会自动提供
2.调用无参构造方法实例化对象要比调用有参构造的更加简单、方便，
  所以在日后的所有开发之中，凡是有简单Java类出现的地方，都一定要提供无参构造。


	类加载器：类加载器是负责加载类的对象。
    	将class文件（硬盘）加载到内存生成Class对象。
    	所有的类加载器都是  java.lang.ClassLoader 的子类
        	BootstrapClassloader  引导类 加载器
        	ExtClassLoader        扩展类 加载器
        	AppClassloader        应用类 加载器
    	使用	xxx.class.getClassLoader() 获得加载自己的类加载器
	类加载器加载机制：
    全盘负责 + 委托机制
    class A {
        private B;
    }
    	全盘负责：A类如果要使用B类（不存在），A类加载器C必须负责加载B类。
    	委托机制：A类加载器如果要加载资源 B，必须询问父类加载是否加载。
                 如果加载，将直接使用。
                 如果没有机制，自己再加载。
    	采用全盘负责 + 委托机制保证一个 class文件只会被加载一次，形成一个Class对象。
    例如类java.lang.Object，它由启动类加载器加载。
    双亲委派模型保证任何类加载器收到的对java.lang.Object的加载请求，
    最终都是委派给处于模型最顶端的启动类加载器进行加载，因此Object类在程序的各种类加载器环境中都是同一个类。

    相反，如果没有使用双亲委派模型，由各个类加载器自行去加载的话，如果用户自己编写了一个称为java.lang.Object的类，
    并用自定义的类加载器加载，
    那系统中将会出现多个不同的Object类，Java类型体系中最基础的行为也就无法保证，应用程序也将会变得一片混乱。

	动态代理：程序运行时，使用JDK提供工具类（Proxy），动态创建一个类，此类一般用于代理。
    代理：你 -- 代理（增强） -- 厂商
    代理类：目标类：被代理的
    动态代理使用前提：必须有接口
    Object proxyObj = Proxy.newProxyInstance(参数1,参数2,参数3);
    参数1：ClassLoader，负责将动态创建类，加载到内存。当前类xxx.class.getClassLoader();
    参数2：Class[] interfaces ,代理类需要实现的所有接口（确定方法）,被代理类实例.getClass().getInterfaces();
    参数3：InvocationHandler, 请求处理类，代理类不具有任何功能，代理类的每一个方法执行时，调用处理类invoke方法。
            invoke(Object proxy ,Method ,Object[] args)
                参数1：代理实例
                参数2：当前执行的方法
                参数3：方法实际参数。
假如我们自己写了一个java.lang.String的类，我们是否可以替换调JDK本身的类？
    答案是否定的。我们不能实现。为什么呢？
    我看很多网上解释是说双亲委托机制解决这个问题，其实不是非常的准确。
    因为双亲委托机制是可以打破的，你完全可以自己写一个classLoader来加载自己写的java.lang.String类，但是你会发现也不会加载成功，
    具体就是因为针对java.*开头的类，jvm的实现中已经保证了必须由bootstrp来加载。

    由于jdk对"java."开头的包增加了权限保护，用户无法使用示例中的ClassLoader#defineClass()方法；
    而所有类都是java.lang.Object类的子类，sout输出时也要使用java.lang.System类等，
    所以我们又必须加载java.lang包下的类。因此，我们仍然将这些包委托给jdk加载。
    同时，这也解释了，为什么不能将常用的java.lang包下的类作为同名类测试对象。
正确姿势
    一个符合规范的类加载器，应当仅覆写ClassLoader#findClass()，以支持自定义的类加载方式。
    不建议覆写ClassLoader#loadClass()    （以使用默认的类加载逻辑，即双亲委派模型）；
    如果需要覆写，则不应该破坏双亲委派模型：
