package me.examples.iceberg;

public class SparkSessionProvider {

    private static SparkSession spark;

    public static SparkSession getSession() {
        if(null == spark) {

        }
        return spark;
    }

    private SparkConf configSession() {
        SparkConf conf = new SparkConf();

    }
}
