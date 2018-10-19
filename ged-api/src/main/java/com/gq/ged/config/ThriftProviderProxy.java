package com.gq.ged.config;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.common.Errors;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.resp.ResponseEntityUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wyq_tomorrow on 2018/1/17.
 */
public class ThriftProviderProxy {
  public Object proxy(Object bean, Class<?> iFaceInterface) {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    return Proxy.newProxyInstance(classLoader, new Class[] {iFaceInterface},
        new InvocationHandler() {

          @SuppressWarnings("unchecked")
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            try {
              result = method.invoke(bean, args);
            } catch (InvocationTargetException ex) {
              ex.printStackTrace();
              BusinessException targetEx = (BusinessException) ex.getTargetException();
              Errors errors = Errors.resolveObject(targetEx.getCode());
              return JSONObject
                  .toJSONString(ResponseEntityUtil.fail(errors, targetEx.getMessage()));
            }
            return result;
          }

        });
  }
}
