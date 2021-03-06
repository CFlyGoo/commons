/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apehat.clone;

/**
 * @author hanpengfei
 * @since 1.0
 */
public abstract class AbstractClone implements Clone {
  
  @Override
  public final <T> T deepClone(T prototype, CloningService service) {
    if (prototype == null) {
      return null;
    }
    final Class<?> prototypeClass = prototype.getClass();
    if (!isCloneable(prototypeClass)) {
      throw new NotSupportedCloneException(prototypeClass);
    }
    try {
      return doDeepClone(prototype, service);
    } catch (Exception e) {
      throw new NotSupportedCloneException(prototypeClass, e);
    }
  }
  
  protected abstract <T> T doDeepClone(T prototype, CloningService service) throws Exception;
  
  protected abstract boolean isCloneable(Class<?> cls);
}
