package com.gq.ged.advice;

import java.lang.annotation.*;

/**
 * Created by wyq_tomorrow on 2018/1/12.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SerializedField {
  /**
   * 数据是否需要加密
   * 
   * @return
   */
  boolean encode() default true;
}
