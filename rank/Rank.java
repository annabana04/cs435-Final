package rank;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;

public class Rank {

    public static void main(String[] args) {

        String input = args[0];
        String outputV = args[1];
        String outputW = args[2];

        SparkConf conf = new SparkConf().setAppName("CS435FINAL");
        JavaSparkContext sc = new JavaSparkContext(conf);

        Data.Doc doc = Data.mapPairs(sc, input);

        JavaRDD<String> V = doc.violence.sortByKey(true).map(t -> String.format("%d %.20f", t._1, t._2));

        V.saveAsTextFile(outputV);

        JavaRDD<String> W = doc.life.sortByKey(true).map(t -> String.format("%d %.20f", t._1, t._2));

        W.saveAsTextFile(outputW);

        sc.stop();
    }
}
