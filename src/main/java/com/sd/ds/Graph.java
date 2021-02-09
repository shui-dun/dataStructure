package com.sd.ds;

import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.*;

public class Graph<V extends Comparable<? super V>> {
    public class Edge {
        private Vertex begin;
        private Vertex end;
        private double value;

        public Edge(Vertex begin, Vertex end, double value) {
            this.begin = begin;
            this.end = end;
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        public Vertex getBegin() {
            return begin;
        }

        public Vertex getEnd() {
            return end;
        }
    }

    public class Vertex {
        private V value;
        private Map<Vertex, Edge> in = new HashMap<>();
        private Map<Vertex, Edge> out = new HashMap<>();

        public Vertex(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        public Map<Vertex, Edge> getIn() {
            return in;
        }

        public Map<Vertex, Edge> getOut() {
            return out;
        }
    }

    private List<Vertex> vertices = new LinkedList<>();

    public List<Vertex> getVertices() {
        return vertices;
    }

    public Vertex insertVertex(V value) {
        Vertex vertex = new Vertex(value);
        vertices.add(vertex);
        return vertex;
    }

    public Edge insertEdge(double value, Vertex begin, Vertex end) {
        if (begin.out.containsKey(end)) {
            return null;
        }
        Edge edge = new Edge(begin, end, value);
        begin.out.put(end, edge);
        end.in.put(begin, edge);
        return edge;
    }

    public Edge insertDoubleEdge(double value, Vertex v1, Vertex v2) {
        if (v1.out.containsKey(v2)) {
            return null;
        }
        Edge edge = new Edge(v1, v2, value);
        v1.out.put(v2, edge);
        v2.in.put(v1, edge);
        v2.out.put(v1, edge);
        v1.in.put(v2, edge);
        return edge;
    }

    public List<Vertex> topSort() {
        LinkedList<Vertex> result = new LinkedList<>();
        Queue<Vertex> q = new LinkedList<>();
        Map<Vertex, Integer> remainIn = new HashMap<>();
        for (Vertex vertex : vertices) {
            remainIn.put(vertex, vertex.in.size());
            if (vertex.in.isEmpty()) {
                q.offer(vertex);
                result.addLast(vertex);
            }
        }
        while (!q.isEmpty()) {
            Vertex vertex = q.poll();
            for (Vertex v : vertex.out.keySet()) {
                int oldIn = remainIn.get(v);
                if (oldIn != 0) {
                    int newIn = oldIn - 1;
                    remainIn.put(v, newIn);
                    if (newIn == 0) {
                        q.offer(v);
                        result.addLast(v);
                    }
                }
            }
        }
        if (result.size() != vertices.size()) {
            throw new RuntimeException("cycle found");
        }
        return result;
    }

    public List<Vertex> reverseTopSort() {
        LinkedList<Vertex> result = new LinkedList<>();
        Queue<Vertex> q = new LinkedList<>();
        Map<Vertex, Integer> remainOut = new HashMap<>();
        for (Vertex vertex : vertices) {
            remainOut.put(vertex, vertex.out.size());
            if (vertex.out.isEmpty()) {
                q.offer(vertex);
                result.addLast(vertex);
            }
        }
        while (!q.isEmpty()) {
            Vertex vertex = q.poll();
            for (Vertex v : vertex.in.keySet()) {
                int oldOut = remainOut.get(v);
                if (oldOut != 0) {
                    int newOut = oldOut - 1;
                    remainOut.put(v, newOut);
                    if (newOut == 0) {
                        q.offer(v);
                        result.addLast(v);
                    }
                }
            }
        }
        if (result.size() != vertices.size()) {
            throw new RuntimeException("cycle found");
        }
        return result;
    }

    public Map<Vertex, Integer> unweightedMinPathAll(Vertex vertex) {
        Map<Vertex, Integer> result = new HashMap<>();
        Queue<Vertex> q = new LinkedList<>();
        q.offer(vertex);
        result.put(vertex, 0);
        while (!q.isEmpty()) {
            Vertex curV = q.poll();
            for (Vertex v : curV.out.keySet()) {
                if (!result.containsKey(v)) {
                    q.offer(v);
                    result.put(v, result.get(curV) + 1);
                }
            }
        }
        return result;
    }

    public Map<Vertex, Double> dijkstra(Vertex vertex) {
        Map<Vertex, Double> result = new HashMap<>();
        Set<Vertex> known = new HashSet<>();
        Queue<Pair<Double, Vertex>> queue = new PriorityQueue<>(Comparator.comparingDouble(Pair::getKey));
        result.put(vertex, 0.0);
        queue.offer(new Pair<>(0.0, vertex));
        while (!queue.isEmpty()) {
            Pair<Double, Vertex> minPair = queue.poll();
            Vertex minV = minPair.getValue();
            if (known.contains(minV)) {
                continue;
            }
            Double minLen = minPair.getKey();
            known.add(minV);
            for (Map.Entry<Vertex, Edge> entry : minV.out.entrySet()) {
                Vertex adjacent = entry.getKey();
                if (known.contains(adjacent)) {
                    continue;
                }
                Double curLen = result.get(adjacent);
                double newLen = minLen + entry.getValue().value;
                if (curLen == null || curLen > newLen) {
                    result.put(adjacent, newLen);
                    queue.offer(new Pair<>(newLen, adjacent));
                }
            }
        }
        return result;
    }

    public Map<Pair<Vertex, Vertex>, Double> dijkstra() {
        Map<Pair<Vertex, Vertex>, Double> map = new HashMap<>();
        for (Vertex v : vertices) {
            for (Map.Entry<Vertex, Double> entry : dijkstra(v).entrySet()) {
                map.put(new Pair<>(v, entry.getKey()), entry.getValue());
            }
        }
        return map;
    }

    public Map<Vertex, Map<Vertex, Double>> floyd() {
        Map<Vertex, Map<Vertex, Double>> map = new HashMap<>();
        for (Vertex x : vertices) {
            Map<Vertex, Double> mapX = new HashMap<>();
            map.put(x, mapX);
            for (Vertex y : vertices) {
                if (x == y) {
                    mapX.put(y, 0.0);
                } else {
                    Edge edge = edge(x, y);
                    if (edge == null) {
                        mapX.put(y, Double.POSITIVE_INFINITY);
                    } else {
                        mapX.put(y, edge.value);
                    }
                }
            }
        }
        for (Vertex k : vertices) {
            for (Vertex i : vertices) {
                for (Vertex j : vertices) {
                    double disIK = map.get(i).get(k), disKJ = map.get(k).get(j);
                    if (disIK != Double.POSITIVE_INFINITY && disKJ != Double.POSITIVE_INFINITY) {
                        double newDis = disIK + disKJ;
                        if (newDis < map.get(i).get(j)) {
                            map.get(i).put(j, newDis);
                        }
                    }
                }
            }
        }
        return map;
    }

    public Map<Vertex, Double> earliestHappenTime() {
        Map<Vertex, Double> result = new HashMap<>();
        List<Vertex> list = topSort();
        for (Vertex cur : list) {
            if (cur.in.isEmpty()) {
                result.put(cur, 0.0);
            } else {
                double max = Double.NEGATIVE_INFINITY;
                for (Edge fromE : cur.in.values()) {
                    Vertex fromV = fromE.begin;
                    double newVal = result.get(fromV) + fromE.value;
                    max = Math.max(max, newVal);
                }
                result.put(cur, max);
            }
        }
        return result;
    }

    public Map<Vertex, Double> latestHappenTime() {
        Map<Vertex, Double> result = new HashMap<>();
        List<Vertex> list = reverseTopSort();
        double offset = Double.POSITIVE_INFINITY;
        for (Vertex cur : list) {
            if (cur.out.isEmpty()) {
                result.put(cur, 0.0);
            } else {
                double min = Double.POSITIVE_INFINITY;
                for (Edge toE : cur.out.values()) {
                    Vertex toV = toE.end;
                    double newVal = result.get(toV) - toE.value;
                    min = Math.min(min, newVal);
                }
                result.put(cur, min);
                offset = Math.min(offset, min);
            }
        }
        for (Map.Entry<Vertex, Double> entry : result.entrySet()) {
            entry.setValue(entry.getValue() - offset);
        }
        return result;
    }

    public Map<Vertex, Double> slackTime() {
        Map<Vertex, Double> earliest = earliestHappenTime();
        Map<Vertex, Double> latest = latestHappenTime();
        for (Map.Entry<Vertex, Double> entry : latest.entrySet()) {
            entry.setValue(entry.getValue() - earliest.get(entry.getKey()));
        }
        return latest;
    }

    public List<Vertex> unweightedMinPath(Vertex s, Vertex t) {
        Map<Vertex, Vertex> preMap = new HashMap<>();
        Queue<Vertex> q = new LinkedList<>();
        preMap.put(s, null);
        q.offer(s);
        loop:
        while (!q.isEmpty()) {
            Vertex curV = q.poll();
            for (Vertex v : curV.out.keySet()) {
                if (!preMap.containsKey(v)) {
                    q.offer(v);
                    preMap.put(v, curV);
                    if (v.equals(t)) {
                        break loop;
                    }
                }
            }
        }
        if (!preMap.containsKey(t)) {
            return null;
        } else {
            LinkedList<Vertex> ret = new LinkedList<>();
            for (Vertex cur = t; cur != null; cur = preMap.get(cur)) {
                ret.addFirst(cur);
            }
            return ret;
        }
    }

    public double ekMaxFlow(Vertex s, Vertex t) {
        double sum = 0;
        while (true) {
            List<Vertex> path = unweightedMinPath(s, t);
            if (path == null) {
                break;
            }
            double maxFlow = Double.POSITIVE_INFINITY;
            Iterator<Vertex> iter = path.iterator();
            Vertex pre = iter.next();
            while (iter.hasNext()) {
                Vertex cur = iter.next();
                maxFlow = Math.min(maxFlow, edge(pre, cur).value);
                pre = cur;
            }
            sum += maxFlow;
            iter = path.iterator();
            pre = iter.next();
            while (iter.hasNext()) {
                Vertex cur = iter.next();
                Edge to = edge(pre, cur);
                to.value -= maxFlow;
                if (to.value == 0) {
                    removeEdge(pre, cur);
                }
                Edge from = edge(cur, pre);
                if (from == null) {
                    insertEdge(maxFlow, cur, pre);
                } else {
                    from.value += maxFlow;
                }
                pre = cur;
            }
        }
        return sum;
    }

    public void removeEdge(Vertex from, Vertex to) {
        from.out.remove(to);
        to.in.remove(from);
    }

    public void removeVertex(Vertex v) {
        for (Vertex neighbor : v.out.keySet()) {
            neighbor.in.remove(v);
        }
        v.out.clear();
        for (Vertex neighbor : v.in.keySet()) {
            neighbor.out.remove(v);
        }
        v.in.clear();
    }

    public Edge edge(Vertex from, Vertex to) {
        return from.out.get(to);
    }

    /**
     * 使用prim算法求解最小生成树，（只适用于无向图）
     */
    public Iterable<Edge> primMinSpanTree() {
        // 已知最小值的顶点集合以及其最小值
        Map<Vertex, Edge> answer = new HashMap<>();
        // 未知最小值的顶点组成的优先队列
        AdaptablePriorityQueue<Edge, Vertex> q = new HeapAdaptablePriorityQueue<>(Comparator.comparingDouble(o -> o.value));
        Map<Vertex, Entry<Edge, Vertex>> map = new HashMap<>();
        Vertex begin = vertices.get(0);
        map.put(begin, q.insert(new Edge(null, null, 0), begin));
        while (!q.isEmpty()) {
            Entry<Edge, Vertex> curEntry = q.removeMin();
            Vertex cur = curEntry.getValue();
            if (cur != begin) {
                answer.put(cur, curEntry.getKey());
            }
            for (Map.Entry<Vertex, Edge> entry : cur.out.entrySet()) {
                Vertex v = entry.getKey();
                Edge e = entry.getValue();
                if (answer.containsKey(v)) {
                    continue;
                }
                Entry<Edge, Vertex> adjEntry = map.get(v);
                if (adjEntry == null) {
                    map.put(v, q.insert(edge(cur, v), v));
                } else {
                    double oldVal = adjEntry.getKey().value;
                    Edge newEdge = edge(cur, v);
                    if (newEdge.value < oldVal) {
                        q.replaceKey(adjEntry, newEdge);
                    }
                }
            }
        }
        return answer.values();
    }

    public Iterable<Edge> kruskalMinSpanTree() {
        Set<Edge> knownEdges = new HashSet<>();
        GenericDisjointSet<Vertex> set = new GenericDisjointSet<>(vertices);
        Queue<Edge> unknownEdges = new PriorityQueue<>(Comparator.comparingDouble(o -> o.value));
        for (Vertex v : vertices) {
            for (Edge e : v.out.values()) {
                unknownEdges.offer(e);
            }
        }
        while (knownEdges.size() != vertices.size() - 1) {
            Edge minEdge = unknownEdges.poll();
            if (set.inSameSet(minEdge.begin, minEdge.end)) {
                continue;
            }
            knownEdges.add(minEdge);
            set.union(minEdge.begin, minEdge.end);
        }
        return knownEdges;
    }

    public List<Vertex> dfs() {
        LinkedList<Vertex> list = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        for (Vertex v : vertices) {
            dfs(v, list, visited);
        }
        return list;
    }

    public List<Vertex> dfs(Vertex begin) {
        LinkedList<Vertex> list = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        dfs(begin, list, visited);
        return list;
    }

    private void dfs(Vertex cur, LinkedList<Vertex> list, Set<Vertex> visited) {
        if (visited.contains(cur)) {
            return;
        }
        visited.add(cur);
        list.addLast(cur);
        for (Vertex v : cur.out.keySet()) {
            dfs(v, list, visited);
        }
    }

    public List<Vertex> dfsNonRecurse() {
        LinkedList<Vertex> answer = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        for (Vertex v : vertices) {
            if (!visited.contains(v)) {
                dfsNonRecurse(v, answer, visited);
            }
        }
        return answer;
    }

    public List<Vertex> dfsNonRecurse(Vertex begin) {
        LinkedList<Vertex> answer = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        dfsNonRecurse(begin, answer, visited);
        return answer;
    }

    private Vertex nextAdj(Vertex cur, Set<Vertex> visited) {
        for (Vertex v : cur.out.keySet()) {
            if (!visited.contains(v)) {
                return v;
            }
        }
        return null;
    }

    private void dfsNonRecurse(Vertex begin, LinkedList<Vertex> answer, Set<Vertex> visited) {
        answer.addLast(begin);
        Deque<Vertex> stack = new ArrayDeque<>();
        stack.addLast(begin);
        visited.add(begin);
        while (!stack.isEmpty()) {
            Vertex vertex = stack.peekLast();
            vertex = nextAdj(vertex, visited);
            while (vertex != null) {
                answer.addLast(vertex);
                stack.addLast(vertex);
                visited.add(vertex);
                vertex = nextAdj(vertex, visited);
            }
            stack.pollLast();
        }
    }

    public List<Vertex> bfs(Vertex begin) {
        LinkedList<Vertex> answer = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        bfs(begin, answer, visited);
        return answer;
    }

    public List<Vertex> bfs() {
        LinkedList<Vertex> answer = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        for (Vertex v : vertices) {
            if (!visited.contains(v)) {
                bfs(v, answer, visited);
            }
        }
        return answer;
    }

    private void bfs(Vertex begin, LinkedList<Vertex> answer, Set<Vertex> visited) {
        Queue<Vertex> q = new LinkedList<>();
        answer.addLast(begin);
        visited.add(begin);
        q.offer(begin);
        while (!q.isEmpty()) {
            Vertex cur = q.poll();
            for (Vertex adj : cur.out.keySet()) {
                if (!visited.contains(adj)) {
                    answer.addLast(adj);
                    visited.add(adj);
                    q.offer(adj);
                }
            }
        }
    }

    public static Graph<Integer> randomDirectedGraph(int nVertices, int nEdges, boolean hasWeight) {
        return randomDirectedGraph(nVertices, nEdges, hasWeight, null);
    }

    public static Graph<Integer> randomDirectedGraph(int nVertices, int nEdges, boolean hasWeight, Integer seed) {
        Graph<Integer> graph = new Graph<>();
        Graph<Integer>.Vertex[] vertices = new Graph.Vertex[nVertices];
        for (int i = 0; i < nVertices; i++) {
            vertices[i] = graph.insertVertex(i);
        }
        Random random = new Random();
        if (seed != null) {
            random.setSeed(seed);
        }
        if (nEdges / nVertices > nVertices / 10) {
            // 稠密图
            List<Pair<Integer, Integer>> pairs = new ArrayList<>(nVertices * nVertices);
            for (int x = 0; x < nVertices; x++) {
                for (int y = 0; y < nVertices; y++) {
                    pairs.add(new Pair<>(x, y));
                }
            }
            Collections.shuffle(pairs, random);
            for (int i = 0; i < nEdges; i++) {
                graph.insertEdge(hasWeight ? random.nextDouble() : 1, vertices[pairs.get(i).getKey()], vertices[pairs.get(i).getValue()]);
            }
        } else {
            // 稀疏图
            for (int i = 0; i < nEdges; ) {
                int begin = random.nextInt(nVertices);
                int end = random.nextInt(nVertices);
                if (begin != end && !vertices[begin].out.containsKey(vertices[end])) {
                    graph.insertEdge(hasWeight ? random.nextDouble() : 1, vertices[begin], vertices[end]);
                    i++;
                }
            }
        }
        return graph;
    }

    public Set<Vertex> maximalIndependentSet() {
        Set<Vertex> marked = new HashSet<>();
        Set<Vertex> mis = new HashSet<>();
        Random random = new Random();
        while (!vertices.isEmpty()) {
            for (Vertex v : vertices) {
                int degree = v.out.size();
                if (Math.abs(random.nextInt()) % (degree) == 0) {
                    marked.add(v);
                }
            }
            loop:
            for (Vertex v : vertices) {
                if (marked.contains(v)) {
                    int degree = v.out.size();
                    for (Vertex neighbor : v.out.keySet()) {
                        int neighborDegree = neighbor.out.size();
                        if (neighborDegree > degree || (neighborDegree == degree && neighbor.value.compareTo(v.value) > 0)) {
                            if (marked.contains(neighbor)) {
                                marked.remove(v);
                                continue loop;
                            }
                        }
                    }
                    mis.add(v);
                }
            }
            for (Vertex v : mis) {
                for (Vertex neighbor : v.out.keySet()) {
                    vertices.remove(neighbor);
                }
                vertices.remove(v);
            }
            marked.clear();
        }
        return mis;
    }
}
