package com.framecloud.common.aspect;


import com.framecloud.common.util.UserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.logging.Logger;

@Aspect
@Order(3)
@Component
public class PermissionsAspect {

    private static final Logger logger = Logger.getLogger("PermissionsAspect");

//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;

    @Pointcut("@annotation(Permissions)")
    public void permissions() {
        // pointcut mark
    }

    @Around("permissions()")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        Method realMethod = pjp.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
        logger.info("Around:" + realMethod);
        if(realMethod.isAnnotationPresent(Permissions.class)){
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            Integer userId = UserUtil.getUserId(request);
            System.out.println(userId);
            /*Permissions permission = realMethod.getAnnotation(Permissions.class);
            String[] permissions = permission.value();
            boolean isPermission = false;
            User user = jwtTokenUtil.getLoginUser();
            ArrayList<String> authorities = (ArrayList<String>) redisService.get("Security:Authorities:" + user.getId(), ArrayList.class);
            for (int i=0;i<permissions.length;i++){
                for (int j=0;j<authorities.size();j++){
                    if (permissions[i].equals(authorities.get(j))){
                        isPermission = true;
                        break;
                    }
                }
            }
            //if (!isPermission){
//            Integer userId = UserUtil.getUserId(request);
            if (false){
                throw new PermissionException("Unauthorized");
            }*/
        }

        return pjp.proceed();
    }

}
