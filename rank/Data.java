package rank;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

import org.apache.hadoop.io.BytesWritable;

public class Data 
{
    // data in hadoop is /Final/dataset.csv

    public static class Doc 
    {
        public final JavaPairRDD<Integer, Double> violence;
        public final JavaPairRDD<Integer, Double> life;

        public Doc(JavaPairRDD<Integer, Double> violence, JavaPairRDD<Integer, Double> life)
        {
            this.violence = violence;
            this.life = life;
        }
    }

    public static Doc mapPairs(JavaSparkContext sc, String dataPath) 
    {
        JavaRDD<String> data = sc.textFile(dataPath);
        JavaPairRDD<Integer, Double> violencePair = data.mapToPair(s ->
        { 
            String[] line = s.split(",");
            int year = Integer.parseInt(line[0].trim());
            double violence = Double.parseDouble(line[1].trim());
            return new Tuple2<>(year, violence); // takes the year and pairs it with the violence score (year, violence score)
        });

        JavaPairRDD<Integer, Double> lifePair = data.mapToPair(s -> 
        {
            String[] line = s.split(",");
            int year = Integer.parseInt(line[0].trim());
            double life = Double.parseDouble(line[2].trim());
            return new Tuple2<>(year, life); // takes the year and pairs it with the violence score (year, violence score)
        });

        return new Doc(violencePair, lifePair);
    }
}
