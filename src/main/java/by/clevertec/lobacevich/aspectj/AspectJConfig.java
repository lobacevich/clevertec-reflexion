package by.clevertec.lobacevich.aspectj;

import by.clevertec.lobacevich.cache.Cache;
import by.clevertec.lobacevich.cache.impl.LFUCache;
import by.clevertec.lobacevich.cache.impl.LRUCache;
import by.clevertec.lobacevich.db.Connect;
import by.clevertec.lobacevich.entity.User;
import by.clevertec.lobacevich.exception.YamlReaderException;
import by.clevertec.lobacevich.util.YamlReader;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.sql.Connection;
import java.util.Optional;

@Aspect
public class AspectJConfig {

    private Cache cache = setCache();

    private Cache setCache() {
        if (YamlReader.getData().get("Cache.algorithm").equals("LRU")) {
            return new LRUCache();
        } else if (YamlReader.getData().get("Cache.algorithm").equals("LFU")) {
            return new LFUCache();
        } else {
            throw new YamlReaderException("Can't get cache algorithm");
        }
    }

    @Around("execution(* by.clevertec.lobacevich.dao.UserDaoImpl.findUserById(..))")
    public Object findById(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        User cacheUser = cache.getById((Long) args[0]);
        if (cacheUser != null) {
            return Optional.of(cacheUser);
        } else {
            return joinPoint.proceed();
        }

    }

    @Around("execution(* by.clevertec.lobacevich.dao.UserDaoImpl.createUser(..))")
    public Object createUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        cache.put((User) result);
        return result;
    }

    @Around("execution(* by.clevertec.lobacevich.dao.UserDaoImpl.updateUser(..))")
    public Object updateUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        cache.put((User) args[0]);
        return joinPoint.proceed();
    }

    @Around("execution(* by.clevertec.lobacevich.dao.UserDaoImpl.deleteUser(..))")
    public Object deleteUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed();
        cache.deleteById((Long) args[0]);
        return result;
    }
}
