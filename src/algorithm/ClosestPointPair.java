package algorithm;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 找到平面点集中最近的点对
 */
public class ClosestPointPair {
    private Pair minPair;
    private List<Point> points;

    public ClosestPointPair(List<Point> points) {
        this.points = points;
        List<Point> listX = new ArrayList<>(points);
        List<Point> listY = new ArrayList<>(points);
        listX.sort(Comparator.comparingDouble(Point::getX));
        listY.sort(Comparator.comparingDouble(Point::getY));
        minPair = compute(listX, 0, points.size() - 1, listY);
    }

    public ClosestPointPair(int size, double beginX, double endX, double beginY, double endY, Random random) {
        this(randomPoints(size, beginX, endX, beginY, endY, random));
    }

    private static double randBetween(double begin, double end, Random random) {
        return begin + random.nextDouble() * (end - begin);
    }

    public static List<Point> randomPoints(int size, double beginX, double endX, double beginY, double endY, Random random) {
        if (random == null) {
            random = new Random();
        }
        List<Point> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(new Point(randBetween(beginX, endX, random), randBetween(beginY, endY, random)));
        }
        return list;
    }

    /**
     * 在小规模的列表中找到最小的点对
     */
    private static Pair computeSmall(List<Point> list, double minDis, Pair minPair) {
        for (int x = 0; x < list.size(); x++) {
            Point a = list.get(x);
            for (int y = x + 1; y < list.size(); y++) {
                Point b = list.get(y);
                if (b.y - a.y > minDis) {
                    break;
                }
                double newDis = distance(a, b);
                if (newDis < minDis) {
                    minDis = newDis;
                    minPair = new Pair(a, b);
                }
            }
        }
        return minPair;
    }

    private static Pair compute(List<Point> listX, int ind1, int ind2, List<Point> listY) {
        if (ind2 - ind1 < 100) {
            return computeSmall(listY, Double.POSITIVE_INFINITY, null);
        } else {
            int indMid = (ind2 + ind1) / 2;
            Point pointMid = listX.get(indMid);
            List<Point> listY1 = new ArrayList<>(indMid - ind1 + 1);
            List<Point> listY2 = new ArrayList<>(ind2 - indMid);
            for (int i = ind1; i <= ind2; i++) {
                Point point = listX.get(i);
                if (point.x <= pointMid.x) {
                    listY1.add(point);
                } else {
                    listY2.add(point);
                }
            }
            Pair minPair1 = compute(listX, ind1, indMid, listY1);
            Pair minPair2 = compute(listX, indMid + 1, ind2, listY2);
            double minDis = minPair1.distance();
            Pair minPair = minPair1;
            double dis2 = minPair2.distance();
            if (dis2 < minDis) {
                minDis = dis2;
                minPair = minPair2;
            }
            List<Point> strip = new ArrayList<>();
            for (Point point : listY) {
                if (Math.abs(point.x - pointMid.x) <= minDis) {
                    strip.add(point);
                }
            }
            return computeSmall(strip, minDis, minPair);
        }
    }

    public static class Point {
        private double x;
        private double y;

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public static class Pair {
        private Point a;
        private Point b;

        public Pair(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        public Point getA() {
            return a;
        }

        public Point getB() {
            return b;
        }

        public double distance() {
            return ClosestPointPair.distance(a, b);
        }
    }

    public Pair getMinPair() {
        return minPair;
    }

    public List<Point> getPoints() {
        return points;
    }
}
