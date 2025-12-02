package rank;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.*;

public class Rank {

    public static void main(String[] args) {

        String input = args[0];
        String output = args[1];

        SparkSession spark = SparkSession.builder().getOrCreate();

        //loader??
        
        spark.stop();
    }
}
