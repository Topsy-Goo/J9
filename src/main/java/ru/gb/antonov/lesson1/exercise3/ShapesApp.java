package ru.gb.antonov.lesson1.exercise3;

import java.util.Arrays;
import java.util.List;

public class ShapesApp {

    public static void main (String[] args) {
        List<Shape> shapes = Arrays.asList (
            new Square   (2),
            new Circle   (2),
            new Triangle (1, 2, 3));

        System.out.println();
        for (Shape s : shapes)
            System.out.printf ("\t%s.\tФормула рассчёта площади : %s.\n",
                               s.toString(), s.areaFormula());
    }

    abstract static public class Shape {
        abstract public String areaFormula ();
        abstract public double calcArea ();
    }

    public static class Circle extends Shape {
        private final double radius;

        public Circle (double r) {
            if (r < 0.0)   throw new IllegalArgumentException();
            radius = r;
        }
        @Override public String toString () {
            return String.format ("%s (радиус %.2f; площадь %.4f)",
                                  this.getClass().getSimpleName(), radius, calcArea());
        }
        public String areaFormula () {  return "S = π * R²";  }
        public double calcArea ()    {  return Math.PI * radius * radius;  }
    }

    public static class Square extends Shape {
        private final double side;

        public Square (double sd) {
            if (sd < 0.0)   throw new IllegalArgumentException();
            side = sd;
        }
        @Override public String toString () {
            return String.format ("%s (сторона %.2f; площадь %.2f)",
                                  this.getClass().getSimpleName(), side, calcArea());
        }
        public String areaFormula () {  return "S = a²";  }
        public double calcArea ()    {  return side * side;  }
    }

    public static class Triangle extends Shape {
        private final double sideA, sideB, sideC;

        public Triangle (double a, double b, double c) {
            if (a < 0.0 || b < 0.0 || c < 0.0 || a+b < c || a+c < b || b+c < a)
                throw new IllegalArgumentException();
            sideA = a;
            sideB = b;
            sideC = c;
        }
        @Override public String toString () {
            return String.format ("%s (стороны %.2f, %.2f, %.2f; площадь %.4f)",
                                  this.getClass().getSimpleName(), sideA, sideB, sideC, calcArea());
        }
        public String areaFormula () {  return "S = sqrt (p * (p - a) * (p - b) * (p - c))";  }

        public double calcArea () {
            double p = (sideA + sideB + sideC) / 2;
            return Math.sqrt (p * (p - sideA) * (p - sideB) * (p - sideC));
        }
    }
}
