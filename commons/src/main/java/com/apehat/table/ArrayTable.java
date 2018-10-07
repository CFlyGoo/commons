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

package com.apehat.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayTable<E> {
  
  private final Object[][] elementData;
  
  public ArrayTable(int rowAndColumn) {
    this(rowAndColumn, rowAndColumn);
  }
  
  public ArrayTable(int row, int column) {
    this.elementData = new Object[row][column];
  }
  
  public ArrayTable(E[][] array) {
    this.elementData = new Object[array.length][];
    for (int i = 0; i < array.length; i++) {
      this.elementData[i] = Arrays.copyOf(array[i], array[i].length);
    }
  }
  
  public void fill(E item) {
    for (int i = 0, row = this.row(); i < row; i++) {
      Arrays.fill(this.elementData[i], item);
    }
  }
  
  @SuppressWarnings("unchecked")
  public List<E> itemsOfRow(int row) {
    return Arrays.asList((E[]) this.elementData[row]);
  }
  
  @SuppressWarnings("unchecked")
  public List<E> itemsOfColumn(int column) {
    List<E> list = new ArrayList<>(this.row());
    for (int i = 0, row = this.row(); i < row; i++) {
      list.add((E) this.elementData[i][column]);
    }
    return list;
  }
  
  public void set(int row, int column, E item) {
    elementData[row][column] = item;
  }
  
  @SuppressWarnings("SuspiciousSystemArraycopy")
  public <T> T[][] toArray(T[][] a) {
    for (int i = 0; i < this.row(); i++) {
      System.arraycopy(elementData[i], 0, a[i], 0, row());
    }
    return a;
  }
  
  @SuppressWarnings("unchecked")
  public E get(int row, int column) {
    return (E) elementData[row][column];
  }
  
  public int row() {
    return elementData.length;
  }
  
  public int column() {
    return elementData[0].length;
  }
}
