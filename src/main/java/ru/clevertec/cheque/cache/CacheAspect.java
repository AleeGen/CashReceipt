package ru.clevertec.cheque.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import ru.clevertec.cheque.cache.algorithm.Cache;
import ru.clevertec.cheque.entity.Human;

import java.lang.reflect.*;
import java.util.*;

@Aspect
@Component
public class CacheAspect {

    @Autowired
    private FactoryCache factoryCache;
    private final Map<Class<?>, Cache<Object, Object>> map = new HashMap<>();

    @Pointcut("@annotation(ru.clevertec.cheque.cache.Cache)")
    public void cachePointcut() {
    }

    @Around(value = "cachePointcut()")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> declaringType = joinPoint.getSignature().getDeclaringType();
        String nameMethod = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Class<?>[] paramTypes = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
        Method method = ReflectionUtils.findMethod(declaringType, nameMethod, paramTypes);
        Type genericInterface = declaringType.getGenericInterfaces()[0];
        Class<?> clazzArg = (Class<?>) ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String nameId = Objects.requireNonNull(method).getAnnotation(ru.clevertec.cheque.cache.Cache.class).id();
        Method methodGetId = getMethodGetId(clazzArg, nameId);
        if (!map.containsKey(clazzArg)) {
            map.put(clazzArg, factoryCache.getCache());
        }
        Cache<Object, Object> cache = map.get(clazzArg);
        Class<?> returnType = Objects.requireNonNull(method).getReturnType();
        Object result;
        if (args.length == 0) {
            List<?> objects = (List<?>) joinPoint.proceed();
            int skip = Math.max(objects.size() - cache.getCapacity(), 0);
            for (int i = skip; i < objects.size(); i++) {
                Object o = objects.get(i);
                cache.put(methodGetId.invoke(o), o);
            }
            result = objects;
        } else if (returnType != void.class) {
            Integer id = (Integer) args[0];
            Object o = cache.get(id);
            if (o == null) {
                o = joinPoint.proceed();
                if (o != null) {
                    cache.put(methodGetId.invoke(o), o);
                }
            }
            result = o;
        } else if (args[0].getClass() == clazzArg) {
            Object o = joinPoint.proceed();
            Human human = (Human) args[0];
            cache.put(human.getId(), human);
            result = o;
        } else {
            Object o = joinPoint.proceed();
            cache.delete(args[0]);
            result = o;
        }
        return result;
    }

    private Method getMethodGetId(Class<?> clazz, String nameId) throws NoSuchFieldException {
        Field id = clazz.getDeclaredField(nameId);
        String nameMethod = "get" + nameId;
        return Arrays.stream(clazz.getMethods())
                .filter(m -> m.getReturnType() == id.getType()
                        && m.getParameterTypes().length == 0
                        && m.getName().equalsIgnoreCase(nameMethod))
                .findFirst().get();
    }

}