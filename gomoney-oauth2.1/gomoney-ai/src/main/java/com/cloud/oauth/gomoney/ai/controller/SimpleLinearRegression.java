package com.cloud.oauth.gomoney.ai.controller;

public class SimpleLinearRegression {
    private double beta0; // 截距
    private double beta1; // 回归系数

    // 计算回归系数
    public void fit(double[] X, double[] y) {
        if (X.length != y.length) {
            throw new IllegalArgumentException("X and y must have the same length.");
        }

        int n = X.length;
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += X[i];
            sumY += y[i];
            sumXY += X[i] * y[i];
            sumX2 += X[i] * X[i];
        }

        // 计算回归系数
        beta1 = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        beta0 = (sumY - beta1 * sumX) / n;
    }

    // 使用回归系数进行预测
    public double predict(double x) {
        return beta0 + beta1 * x;
    }

    // 打印回归系数
    public void printCoefficients() {
        System.out.println("Intercept (β0): " + beta0);
        System.out.println("Slope (β1): " + beta1);
    }

    public static void main(String[] args) {
        // 示例数据
        double[] X = {1, 2, 3, 4, 5};
        double[] y = {2, 3, 5, 7, 11};

        SimpleLinearRegression regression = new SimpleLinearRegression();
        regression.fit(X, y);

        // 打印回归系数
        regression.printCoefficients();

        // 预测
        double newX = 6;
        double prediction = regression.predict(newX);
        System.out.println("Prediction for x = " + newX + ": " + prediction);
    }
}
