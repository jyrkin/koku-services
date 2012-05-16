/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (kohtikumppanuutta@ixonos.com).
 *
 */
package fi.koku.services.entity.kks.impl;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuException;
import fi.koku.KoKuFaultException;
import fi.koku.services.entity.kks.v1.ServiceFault;
import fi.koku.services.entity.kks.v1.ServiceFaultDetailType;

/**
 * KksServiceFaultInterceptor.
 * 
 * @author laukksa
 *
 */
public class KksServiceFaultInterceptor {

  private static Logger logger = LoggerFactory.getLogger(KksServiceFaultInterceptor.class);
  
  @AroundInvoke
  public Object intercept(final InvocationContext invocationContext) throws Exception {
    try {
      return invocationContext.proceed();
    } catch (final RuntimeException e) {
      // Log the exception
      logger.error("Exception occured.", e);
      
      ServiceFaultDetailType type = new ServiceFaultDetailType();
      String message = null;
      
      if (e instanceof KoKuFaultException) {
        KoKuFaultException kokuException = (KoKuFaultException) e;
        type.setCode(kokuException.getErrorCode());
        message = e.getMessage();      
      } else if (e.getCause() instanceof KoKuFaultException) {
        // JBoss wraps runtime exceptions to EJBTransactionRolledbackException, we need to get the cause
        KoKuFaultException kokuException = (KoKuFaultException) e.getCause();
        type.setCode(kokuException.getErrorCode());
        message = e.getCause().getMessage();
      } else {
        // Other runtime exception
        type.setCode(KoKuException.COMMON_ERROR_CODE);
        message = KoKuException.COMMON_ERROR_MESSAGE;
      }
      type.setMessage(message);
      // Throw service fault to the client
      throw new ServiceFault(message, type);
    }
  }
}
