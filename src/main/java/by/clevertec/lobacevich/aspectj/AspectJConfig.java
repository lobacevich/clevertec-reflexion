package by.clevertec.lobacevich.aspectj;

import by.clevertec.lobacevich.cache.Cache;
import by.clevertec.lobacevich.cache.impl.LFUCache;
import by.clevertec.lobacevich.cache.impl.LRUCache;
import by.clevertec.lobacevich.data.UserDto;
import by.clevertec.lobacevich.entity.User;
import by.clevertec.lobacevich.exception.YamlReaderException;
import by.clevertec.lobacevich.mapper.UserMapper;
import by.clevertec.lobacevich.mapper.UserMapperImpl;
import by.clevertec.lobacevich.util.YamlReader;
import by.clevertec.lobacevich.validator.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Optional;

@Aspect
public class AspectJConfig {

    private Cache cache = setCache();
    private Validator validator = Validator.getINSTANCE();

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
    public Object daoCreateUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        cache.put((User) result);
        return result;
    }

    @Around("execution(* by.clevertec.lobacevich.dao.UserDaoImpl.updateUser(..))")
    public Object daoUpdateUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        cache.put((User) args[0]);
        return joinPoint.proceed();
    }

    @Around("execution(* by.clevertec.lobacevich.dao.UserDaoImpl.deleteUser(..))")
    public Object daoDeleteUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed();
        cache.deleteById((Long) args[0]);
        return result;
    }

    @Around("execution(* by.clevertec.lobacevich.service.impl.UserServiceImpl.createUser(..))")
    public Object serviceCreateUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        validator.validateToCreate((UserDto) args[0]);
        return joinPoint.proceed();
    }

    @Around("execution(* by.clevertec.lobacevich.service.impl.UserServiceImpl.updateUser(..))")
    public Object serviceUpdateUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        validator.validateToUpdate((UserDto) args[0]);
        return joinPoint.proceed();
    }
}
