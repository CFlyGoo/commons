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

import com.apehat.clone.SerializationCloneTest.SerializableClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertNull;

/**
 * @author hanpengfei
 * @since 1.0
 */
@SuppressWarnings("Duplicates")
public class ReflectionCloneTest {
  
  private Clone clone = new ReflectionClone();
  private CloningService service = new DefaultCloningService();
  
  @Test(expectedExceptions = NotSupportedCloneException.class)
  public void testDeepCloneWithNullService() {
    clone.deepClone(new Object(), null);
  }
  
  @Test
  public void testDeepCloneWithNull() {
    assertNull(clone.deepClone(null, service));
  }
  
  @Test
  public void testDeepCloneCollection() {
    Set<Object> set = new HashSet<>();
    set.add(1);
    set.add(new Object());
    set.add(true);
    Set<Object> cloneValue = clone.deepClone(set, service);
    assertEquals(set, cloneValue);
    assertNotSame(set, cloneValue);
  }
  
  @Test
  public void testDeepCloneMultidimensionalCollection() {
    Set<Set<Integer>> prototype = new HashSet<>();
    
    Set<Integer> first = new HashSet<>();
    first.add(1);
    first.add(2);
    first.add(3);
    Set<Integer> second = new HashSet<>();
    second.add(4);
    second.add(5);
    second.add(6);
    
    prototype.add(first);
    prototype.add(second);
    
    Set<Set<Integer>> cloneValue = clone.deepClone(prototype, service);
    
    assertEqualsDeep(prototype, cloneValue, "Prototype and deepClone is not equals");
    assertNotSame(prototype, cloneValue);
    
    first.add(7);
    second.add(8);
    assertNotEqualsDeep(prototype, cloneValue);
  }
  
  @Test
  public void testDeepClonePlainObject() {
    SerializableClass cls = new SerializableClass();
    SerializableClass cloneValue = clone.deepClone(cls, service);
    assertNotNull(cloneValue);
    assertNotSame(cls, cloneValue);
    assertEquals(cloneValue, cls);
  }
}
