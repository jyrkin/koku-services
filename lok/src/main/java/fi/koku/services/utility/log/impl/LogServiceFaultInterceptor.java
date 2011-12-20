/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.log.impl;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuException;
import fi.koku.KoKuFaultException;
import fi.koku.services.utility.log.v1.ServiceFault;
import fi.koku.services.utility.log.v1.ServiceFaultDetailType;

/**
 * LogServiceFaultInterceptor.
 * 
 * Used in endpoint bean. Catches and logs all exceptions and converts them to
 * ServiceFault (as defined in the contract/wsdl).
 * 
 * @author laukksa
 */
public class LogServiceFaultInterceptor {

  private static Logger logger = LoggerFactory.getLogger(LogServiceFaultInterceptor.class);
  
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