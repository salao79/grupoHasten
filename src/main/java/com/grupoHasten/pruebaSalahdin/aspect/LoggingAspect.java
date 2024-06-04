package com.grupoHasten.pruebaSalahdin.aspect;

import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import com.grupoHasten.pruebaSalahdin.service.impl.NaveEspacialService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(NaveEspacialService.class);

    @Before("execution(* *..*Service.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof NaveEspacialDTO naveEspacial) {
                if (naveEspacial.getId() != null && naveEspacial.getId() == -1) {
                    logger.info("Hay una nave espacial con id -1");
                }
            }
        }
    }
}
