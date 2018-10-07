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

package com.apehat.graph;

import com.apehat.table.ArrayTable;
import com.apehat.util.MatrixUtils;

import java.util.*;

/**
 * @author hanpengfei
 * @since 1.0
 */
public class ArrayDigraph<E> implements Digraph<E> {
  
  private final List<E> vertices;
  private final Indicator<? super E> indicator;
  private transient ArrayTable<Integer> adjacencyTable;
  private transient ArrayTable<Integer> reachableTable;
  
  public ArrayDigraph(Set<E> vertices, Indicator<? super E> indicator) {
    if (vertices == null || vertices.size() == 0) {
      throw new IllegalArgumentException("Must specified vertices");
    }
    this.indicator = Objects.requireNonNull(indicator, "Indicator must not be null");
    this.vertices = new ArrayList<>(vertices);
  }
  
  @Override
  public Set<E> getAdjacentFirstVertices(E node) {
    return this.nonZeroElements(this.getAdjacencyTable().itemsOfColumn(this.vertices.indexOf(node)));
  }
  
  @Override
  public Set<E> getAdjacentReachableVertices(E vertex) {
    return this.nonZeroElements(this.getAdjacencyTable().itemsOfRow(this.vertices.indexOf(vertex)));
  }
  
  @Override
  public Set<E> getReachableVertices(E item) {
    return this.nonZeroElements(this.getReachableTable().itemsOfRow(this.vertices.indexOf(item)));
  }
  
  @Override
  public Set<E> getFirstVertices(E item) {
    return this.nonZeroElements(this.getReachableTable().itemsOfColumn(this.vertices.indexOf(item)));
  }
  
  private Set<E> nonZeroElements(List<Integer> list) {
    final Set<E> items = new HashSet<>();
    for (int i = 0, len = list.size(); i < len; i++) {
      if (list.get(i) > 0) {
        items.add(this.vertices.get(i));
      }
    }
    return items;
  }
  
  @Override
  public boolean isDirected(E from, E to) {
    return from.equals(to) || indicator.isDirection(from, to);
  }
  
  @Override
  public Set<E> vertices() {
    return new HashSet<>(vertices);
  }
  
  private ArrayTable<Integer> getReachableTable() {
    if (this.reachableTable == null) {
      final ArrayTable<Integer> table = getAdjacencyTable();
      final Integer[][] array = table.toArray(new Integer[table.row()][table.column()]);
      this.reachableTable = new ArrayTable<>(MatrixUtils.mul(array, array));
    }
    return reachableTable;
  }
  
  private ArrayTable<Integer> getAdjacencyTable() {
    if (this.adjacencyTable == null) {
      final ArrayTable<Integer> table = new ArrayTable<>(this.vertices.size());
      table.fill(0);
      for (E from : vertices) {
        for (E to : vertices) {
          if (isDirected(from, to)) {
            table.set(vertices.indexOf(from), vertices.indexOf(to), 1);
          }
        }
      }
      this.adjacencyTable = table;
    }
    return adjacencyTable;
  }
}
