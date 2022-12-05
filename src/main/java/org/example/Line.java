package org.example;

class Line {
    Integer x = 0;
    Integer y = 0;
    Integer x1 = 0;
    Integer y1 = 0;
    double k = Double.valueOf(1);
    double b = Double.valueOf(0);

    Line(Integer X, Integer Y, Integer X1, Integer Y1) {
        x = X;
        y = Y;
        x1 = X1;
        y1 = Y1;
        k = (double) (Y1 - Y) / (X1 - X);
        b = Y - X * k;
    }

    public static void SolTXT(Line d1, Line d2) {
        if (d1.k == d2.k && d1.b != d2.b) {
            System.out.println("line 1: y = " + d1.k + "x + " + d1.b + "; line 2: y = " + d2.k + "x + " + d2.b + "; are ||");
        } else if (d1.k == d2.k && d1.b == d2.b) {
            System.out.println("line 1: y = " + d1.k + "x + " + d1.b + "; line 2: y = " + d2.k + "x + " + d2.b + "; are equal");
        } else {
            double x = (d2.b - d1.b) / (d1.k - d2.k);
            double y = x * d1.k + d1.b;
            System.out.println("line 1: y = " + d1.k + "x + " + d1.b + "; line 2: y = " + d2.k + "x + " + d2.b + "; (" + x + "; " + y + ")");
        }
    }

    public static String SolXML(Line d1, Line d2) {
        String answer = "";
        if (d1.k == d2.k && d1.b != d2.b) {
            return  "are ||";
        } else if (d1.k == d2.k && d1.b == d2.b) {
            return "are equal";
        } else {
            double x = (d2.b - d1.b) / (d1.k - d2.k);
            double y = x * d1.k + d1.b;
            return "(" + x + "; " + y + ")";
        }
    }
}