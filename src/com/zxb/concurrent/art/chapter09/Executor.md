**Executor框架的成员**
Executor框架的主要成员：ThreadPoolExecutor、ScheduledThreadPoolExecutor、Future接口、Runnable接口、Callable接口和Executors。
1、ThreadPoolExecutor
    ThreadPoolExecutor通常是由工厂类Executors来创建。Executors可以创建3种类型的ThreadPoolExecutor：FixedThreadPool、SingleThreadExecutor、CacheThreadPool。
    1) FixedThreadPool：创建使用固定线程数的线程池,FixedThreadPool适用于为了满足资源管理的需求，而需要限制当前线程数量的应用场景，它适用于负载比较重的服务器。
    2) SingleThreadExecutor：创建使用单个线程的线程池，SingleThreadExecutor适用于需要保证顺序地执行各个任务；并且在任意时间点，不会有多个线程是活动的应用场景。
    3) CacheThreadPool：创建一个会根据需要创建新线程的线程池，CacheThreadPool是大小无界的线程池，适用于执行很多的短期异步任务的小程序，或者是负载较轻的服务器。
    
2、ScheduledThreadPoolExecutor
    ScheduledThreadPoolExecutor通常是使用工厂类Executors来创建。Executors可以创建2种类型的ScheduledThreadPoolExecutor：ScheduledThreadPoolExecutor、SingleThreadScheduledExecutor
    1) ScheduledThreadPoolExecutor：包含若干线程的线程池，适用于需要多个后台线程执行周期任务，同时为了满足资源管理的需求而需要限制后台线程的数量的应用场景。
    2) SingleThreadScheduledExecutor：只包含一个线程的线程池，适用于需要单个后台线程执行周期任务，同时需要保证顺序地执行各个任务的应用场景。
    
3、Future接口
    Future接口和实现Future接口的FutureTask类用来表示异步计算的结果。
    
4、Runnable接口和Callable接口
    Runnable接口和Callable接口的实现类都可以被ThreadPoolExecutor或ScheduleThreadPoolExecutor执行。它们之间的区别是Runnable接口不会返回结果，而Callable接口可以返回处理结果。
    除了可以自己创建实现Callable接口的对象外，还可以使用工厂类Executors来把一个Runnable接口包装成一个Callable接口，Executors.callable(Runnable task)方法即可。
    
**ThreadPoolExecutor详解**
    Executor框架最核心的类是ThreadPoolExecutor，它是线程池的实现类。主要由下列4个组件构成。
    corePool：核心线程池的大小。
    maximumPool：最大线程池的大小。
    BlockingQueue：用来暂时存放任务的工作队列。
    RejectedExecutionHandler：当ThreadPoolExecutor已经关闭或ThreadPoolExecutor已经饱和时（达到了最大线程池大小且工作队列已满），execute()方法将要调用的handler。
    通过Executor框架的工具类Executors，可以创建3种类型的ThreadPoolExecutor。
    FixedThreadPool
    SingleThreadExecutor
    CachedThreadPool
    
