**To update your vs code repo:**<br />
git fetch origin<br />
git merge<br />
<br />
**Commit changes:**<br />
git add .<br />
git commit -m "comment goes here"<br />
git push origin <branch-name><br />
<br />
**Change branch:** <br />
git checkout <branch-name>

To run:
javac -cp "$(hadoop classpath):$SPARK_HOME/jars/*" rank/*.java

jar cvf rank.jar rank/*.class

spark-submit --master local[*] --class rank.Rank rank.jar \
    hdfs:///Project/dataset.csv \
    hdfs:///Project/outputViolence \
    hdfs:///Project/outputLife

To remove:
hdfs dfs -rm -r /Project/outputViolence
hdfs dfs -rm -r /Project/outputLife

