import java.util.*;

public class Main {

    // DFS Method
    public static void dfs(String vertex,
                           Map<String, List<String>> graph,
                           Set<String> visited) {

        visited.add(vertex);
        System.out.print(vertex + " ");

        for (String neighbor : graph.get(vertex)) {

            if (!visited.contains(neighbor)) {
                dfs(neighbor, graph, visited);
            }
        }
    }

    // BFS Method
    public static void bfs(String start,
                           Map<String, List<String>> graph) {

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {

            String vertex = queue.poll();
            System.out.print(vertex + " ");

            for (String neighbor : graph.get(vertex)) {

                if (!visited.contains(neighbor)) {

                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    public static void dijkstra(
            Map<String, Map<String, Integer>> graph,
            String start,
            String end) {

        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        PriorityQueue<String> pq =
                new PriorityQueue<>(Comparator.comparingInt(distance::get));

        for (String city : graph.keySet()) {
            distance.put(city, Integer.MAX_VALUE);
        }

        distance.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {

            String current = pq.poll();

            for (String neighbor : graph.get(current).keySet()) {

                int newDistance =
                        distance.get(current)
                                + graph.get(current).get(neighbor);

                if (newDistance < distance.get(neighbor)) {

                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, current);

                    pq.add(neighbor);
                }
            }
        }

        System.out.println("\nShortest Distance: "
                + distance.get(end));

        List<String> path = new ArrayList<>();

        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }

        Collections.reverse(path);

        System.out.println("Shortest Path: "
                + String.join(" -> ", path));
    }

    public static void main(String[] args) {

        Map<String, List<String>> graph = new HashMap<>();

        graph.put("A", Arrays.asList("C", "B", "D"));
        graph.put("B", Arrays.asList("A", "C", "E", "G"));
        graph.put("C", Arrays.asList("A", "B", "D"));
        graph.put("D", Arrays.asList("C", "A"));
        graph.put("E", Arrays.asList("G", "F", "B"));
        graph.put("F", Arrays.asList("G", "E"));
        graph.put("G", Arrays.asList("F", "B"));

        // DFS
        System.out.println("DFS Traversal:");
        dfs("A", graph, new HashSet<>());

        // BFS
        System.out.println("\n\nBFS Traversal:");
        bfs("A", graph);

        Map<String, Map<String, Integer>> roads = new HashMap<>();

        roads.put("Edinburgh", new HashMap<>());
        roads.put("Stirling", new HashMap<>());
        roads.put("Perth", new HashMap<>());
        roads.put("Dundee", new HashMap<>());
        roads.put("Glasgow", new HashMap<>());

        roads.get("Edinburgh").put("Stirling", 50);
        roads.get("Edinburgh").put("Perth", 100);
        roads.get("Edinburgh").put("Glasgow", 70);

        roads.get("Stirling").put("Perth", 40);
        roads.get("Stirling").put("Glasgow", 50);
        roads.get("Stirling").put("Edinburgh", 50);

        roads.get("Perth").put("Dundee", 60);
        roads.get("Perth").put("Edinburgh", 100);
        roads.get("Perth").put("Stirling", 40);

        roads.get("Glasgow").put("Edinburgh", 70);
        roads.get("Glasgow").put("Stirling", 50);

        roads.get("Dundee").put("Perth", 60);

        dijkstra(roads, "Edinburgh", "Dundee");
    }
}