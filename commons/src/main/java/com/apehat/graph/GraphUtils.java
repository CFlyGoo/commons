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

package com.apehat.graph;

import com.apehat.CollectionUtils;
import com.apehat.layer.DigraphLayer;
import com.apehat.layer.Layer;

import java.util.HashSet;
import java.util.Set;

public final class GraphUtils {
  
  private GraphUtils() {
  }
  
  public static <E> Layer<E> topologicalSort(Digraph<E> graph) {
    Layer<E> firstLayer = null;
    final Set<E> pendingItems = graph.vertices();
    final Set<E> completed = new HashSet<>();
    
    while (!pendingItems.isEmpty()) {
      Set<E> currentLayerItems = new HashSet<>();
      for (E item : pendingItems) {
        final Set<E> reachableSet = graph.getReachableVertices(item);
        reachableSet.removeAll(completed);
        if (reachableSet.equals(CollectionUtils.crossSet(
                graph.getFirstVertices(item), graph.getReachableVertices(item)))) {
          currentLayerItems.add(item);
        }
      }
      pendingItems.removeAll(currentLayerItems);
      completed.addAll(currentLayerItems);
      
      if (firstLayer == null) {
        firstLayer = new DigraphLayer<>(currentLayerItems);
      } else {
        firstLayer.addAsFloor(currentLayerItems);
      }
    }
    return firstLayer;
  }
}
