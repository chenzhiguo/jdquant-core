/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jd.quant.core.common.utils;

import java.util.UUID;

/**
 * UUID生成工具类
 *
 * @author Zhiguo.Chen
 */
public class UuidUtil {

    /**
     * 获得UUID的方法
     *
     * @return
     */
    public static String createUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 获得系统时间戳
     *
     * @return
     */
    public static long createIDForLong(){
        return System.currentTimeMillis();
    }

}