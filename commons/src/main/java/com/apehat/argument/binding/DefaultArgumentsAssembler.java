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

package com.apehat.argument.binding;

import com.apehat.Value;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @author hanpengfei
 * @since 1.0
 */
public class DefaultArgumentsAssembler<T> implements ArgumentsAssembler<T> {
  
  private final ParameterAliasDiscoverer parameterAliasDiscoverer;
  private final ArgumentAdapter argumentAdapter;
  
  public DefaultArgumentsAssembler(ParameterAliasDiscoverer discoverer, ArgumentAdapter adapter) {
    this.parameterAliasDiscoverer = Objects.requireNonNull(discoverer);
    this.argumentAdapter = Objects.requireNonNull(adapter);
  }
  
  @Override
  public Object[] assemble(Executable exec, T prototype) {
    final int count = exec.getParameterCount();
    final Object[] args = new Object[count];
    final Parameter[] parameters = exec.getParameters();
    for (int i = 0; i < count; i++) {
      final Parameter parameter = parameters[i];
      final String alias = parameterAliasDiscoverer.aliasOf(parameter);
      final Value<?> value = argumentAdapter.adapt(alias, prototype);
      Object result;
      if (value == null) {
        if (alias.lastIndexOf(".") == -1 && parameter.getType()
                .isAssignableFrom(prototype.getClass())) {
          result = prototype;
        } else {
          // TODO convert prototype to expected value
          throw new IllegalStateException("Cannot find " + alias + " from " + prototype);
        }
      } else {
        result = value.get();
      }
      args[i] = result;
    }
    return args;
  }
}
