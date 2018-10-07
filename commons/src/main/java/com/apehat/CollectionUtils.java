/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.apehat;

import java.util.HashSet;
import java.util.Set;

public final class CollectionUtils {
  
  private CollectionUtils() {
  }
  
  public static <E> Set<E> crossSet(Set<E> s1, Set<E> s2) {
    Set<E> traverser = (s1.size() > s2.size()) ? s2 : s1;
    Set<E> observer = (s1.size() > s2.size()) ? s1 : s2;
    final Set<E> crossSet = new HashSet<>();
    for (E reachable : traverser) {
      if (observer.contains(reachable)) {
        crossSet.add(reachable);
      }
    }
    return crossSet;
  }
}
