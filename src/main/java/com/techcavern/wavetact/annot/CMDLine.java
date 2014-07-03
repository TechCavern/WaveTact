package com.techcavern.wavetact.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jztech101 on 7/3/14.
 */@Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.CONSTRUCTOR)
   public @interface CMDLine{}
