package fi.koku.services.entity.customer.impl;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuFaultException;
import fi.koku.KoKuException;
import fi.koku.services.entity.customer.v1.ServiceFault;
import fi.koku.services.entity.customer.v1.ServiceFaultDetailType;

/**
 * CustomerServiceFaultInterceptor.
 * 
 * @author laukksa
 *
 */
public class CustomerServiceFaultInterceptor {

  private static Logger logger = LoggerFactory.getLogger(CustomerServiceFaultInterceptor.class);
  
  @AroundInvoke
  public Object intercept(final InvocationContext invocationContext) throws Exception {
    try {
      return invocationContext.proceed();
    } catch (final RuntimeException e) {
      // Log the exception
      logger.error("Exception occured.", e);
      
      ServiceFaultDetailType type = new ServiceFaultDetailType();
      String message = null;
      // JBoss wraps runtime exceptions to EJBTransactionRolledbackException, we need to get the cause
      if (e.getCause() instanceof KoKuFaultException) {
        // KoKu specific exception
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