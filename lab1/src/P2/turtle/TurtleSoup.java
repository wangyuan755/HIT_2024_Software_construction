/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for(int i = 0; i < 4; i++)
        {
            turtle.forward(sideLength);
            turtle.turn(90);
        }

    }

    public static double calculateRegularPolygonAngle(int sides) {
        return 180 -  (double)360 / sides;
    }
    public static int calculatePolygonSidesFromAngle(double angle) {
        return (int)Math.round(360 / (180 - angle));
    }
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = calculateRegularPolygonAngle(sides);
        for(int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180 - angle);
        }
    }

    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double x = targetX - currentX;
        double y = targetY - currentY;
        double angle = Math.toDegrees(Math.atan2(y, x));
        angle = 90 - angle - currentBearing;
        angle = angle >= 0 ? angle : (angle + 360);
        return angle;
    }

    //重载，之后要用
    public static double calculateBearingToPoint(double currentBearing, double currentX, double currentY,
                                                 double targetX, double targetY) {
        double x = targetX - currentX;
        double y = targetY - currentY;
        double angle = Math.toDegrees(Math.atan2(y, x));
        angle = 90 - angle - currentBearing;
        angle = angle >= 0 ? angle : (angle + 360);
        return angle;
    }

    public static double Distance(double curx,double cury,double disx,double disy)
    {
        double x = disx - curx;
        double y = disy - cury;
        return Math.sqrt(x*x +y*y);
    }


    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        if(xCoords.size() == 0) {
            return new ArrayList<>();
        } //坐标集为空时返回空列表
        ArrayList<Double> ld = new ArrayList<Double>();
        int currentX = xCoords.get(0), currentY = yCoords.get(0);
        double currentBearing = 0;
        for(int i = 1; i < xCoords.size(); i++) {//循环调用就行了//
            currentBearing = calculateBearingToPoint(currentBearing, currentX, currentY, xCoords.get(i), yCoords.get(i));
            ld.add(currentBearing);
            currentX = xCoords.get(i);
            currentY = yCoords.get(i);
        }
        return ld;
    }


    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        if (points.size() <= 3)
            return points;

        HashSet<Point> result = new HashSet<Point>();
        Point start = points.iterator().next();
        //构建迭代器,找最边缘的点
        for (Point p : points) {
            if (p.x() < start.x() || (p.x() == start.x() && p.y() < start.y()))
                start = p;
        }
        result.add(start);
        double currentBearing = 0; // 初始化方向为向上
        double angle, min;
        Point current = start;
        Point target = points.iterator().next();
        if (target == start)
            target = points.iterator().next();

        while (target != start) {
            min = 360;
            for (Point p : points) {
                if (p == current)
                    continue;
                angle = calculateBearingToPoint(currentBearing, current.x(), current.y(), p.x(), p.y());
                if ((angle < min) || ((angle == min) && (Distance(current.x(), current.y(), p.x(), p.y()) > Distance(current.x(), current.y(), target.x(), target.y())))) {
                    target = p;
                    min = angle;
                }
            }
            result.add(target);
            current = target;
            currentBearing = min;
        }
        return result;
    }


    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle,int length,int edge) {


        for(int i = 0; i < edge; i++)
        {
            turtle.forward(length);
            turtle.turn((double) 360 /edge);
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawSquare(turtle, 40);
        drawPersonalArt(turtle,5,100);

        // draw the window
        turtle.draw();
    }

}
