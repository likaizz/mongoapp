package com.example.mongoapp.interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


//@Aspect
//@Component
public class ControllerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerInterceptor.class);

//	@Value("${spring.profiles}")

    /**
     * 定义拦截规则：拦截com.xjj.web.controller包下面的所有类中，有@RequestMapping注解的方法。
     */
//	@Pointcut("com.example.mongoapp.controller")
//	public void controllerMethodPointcut(){}

    /**
     * 拦截器具体实现
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    /*@Around(value = "execution(* com.example.mongoapp.controller.*(..))") //指定拦截器规则；也可以直接把“execution(* com.xjj.........)”写进这里
	public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
		long beginTime = System.currentTimeMillis();
//		Signature signature=pjp.getSignature();
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod(); //获取被拦截的方法
		String methodName = method.getName(); //获取被拦截的方法名
		pjp.proceed();


		Set<Object> allParams = new LinkedHashSet<>(); //保存所有请求参数，用于输出到日志中
		LOGGER.info("请求开始，方法：{}", methodName);
		Object result = null;
		Object[] args = pjp.getArgs();
		for(Object arg : args){
			//logger.debug("arg: {}", arg);
			if (arg instanceof Map<?, ?>) {
				//提取方法中的MAP参数，用于记录进日志中
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) arg;
				allParams.add(map);
			}*//*else if(arg instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest) arg;
				if(isLoginRequired(method)){
					if(!isLogin(request)){
						result = new JsonResult(ResultCode.NOT_LOGIN, "该操作需要登录！去登录吗？\n\n（不知道登录账号？请联系老许。）", null);
					}
				}*//*
				
				//获取query string 或 posted form data参数
				*//*Map<String, String[]> paramMap = request.getParameterMap();
				if(paramMap!=null && paramMap.size()>0){
					allParams.add(paramMap);
				}
			}else if(arg instanceof HttpServletResponse){
				//do nothing...
			}else{
				//allParams.add(arg);
			}
		}
		
		try {
			if(result == null){
				// 一切正常的情况下，继续执行被拦截的方法
				result = pjp.proceed();
			}
		} catch (Throwable e) {
			logger.info("exception: ", e);
			result = new JsonResult(ResultCode.EXCEPTION, "发生异常："+e.getMessage());
		}
		
		if(result instanceof JsonResult){
			long costMs = System.currentTimeMillis() - beginTime;
			logger.info("{}请求结束，耗时：{}ms", methodName, costMs);
		}*//*
		
		return result;
	}*/

    /**
     * 判断一个方法是否需要登录
     *
     * @param method
     * @return
     */
/*	private boolean isLoginRequired(Method method){
		if(!env.equals("prod")){ //只有生产环境才需要登录
			return false;
		}
		
		boolean result = true;
		if(method.isAnnotationPresent(Permission.class)){
			result = method.getAnnotation(Permission.class).loginReqired();
		}
		
		return result;
	}*/

    //判断是否已经登录
	/*private boolean isLogin(HttpServletRequest request) {
		return true;
		*//*String token = XWebUtils.getCookieByName(request, WebConstants.CookieName.AdminToken);
		if("1".equals(redisOperator.get(RedisConstants.Prefix.ADMIN_TOKEN+token))){
			return true;
		}else {
			return false;
		}*//*
	}*/
    @Around("execution(* com.example.mongoapp.controller.*(..))")
    public Object test(ProceedingJoinPoint jp)
            throws Throwable {
        long t1 = System.currentTimeMillis();
        Object val = jp.proceed();//目标业务方法
        long t2 = System.currentTimeMillis();
        long t = t2 - t1;

        //JoinPoint 对象可以获取目标业务方法的
        //详细信息: 方法签名, 调用参数等
        Signature m = jp.getSignature();
        //Signature: 签名, 这里是方法签名
        System.out.println(m + "用时:" + t);
        return val;
    }


    @Before("bean(us)")
    public void test(){
        System.out.println("aop.@Before");
        this.getClass();
    }

    @After("bean(us)")
    public void test2(){
        System.out.println("aop.@After");
    }

    @AfterReturning("bean(us)")
    public void test3(){
        System.out.println("aop.@AfterReturning");
    }

    @AfterThrowing("bean(us)")
    public  void test4(){
        System.out.println("aop.@AfterThrowing");
    }


    //环绕通知 方法
    //必须有返回值
    //必须有参数 ProceedingJoinPoint
    //必须抛出异常
    //返回业务方法的返回值
    @Around("bean(us)")
    public Object around(ProceedingJoinPoint o)throws Throwable{
        System.out.println("ProceedindJoinPoint:"+o.getClass());


        System.out.println("around.业务方法执行前");
        Object x=o.proceed();
//		o.
        System.out.println("around.业务方法执行后");
        System.out.println("业务方法执行后的结果为:"+x);
        //修改业务方法的处理结果
        throw new RuntimeException("就是不让你登录");
//		return x;
    }


}