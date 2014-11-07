package gov.llnl.aims;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class TSAverage {

  public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {


      /**
	 Mapper averages all values across the line
       */
    public void map(LongWritable key, Text value, OutputCollector<IntWritable, FloatWritable> output, Reporter reporter) throws IOException {
      String line = value.toString();


      StringTokenizer tokenizer = new StringTokenizer(line);

      int count =-1 ;
      int int_key =0;
      float total =0; 
      while (tokenizer.hasMoreTokens()) {
	  if(count=-1)
	     int_key = Int.parseInt(tokenizer.nextToken());
	  else
	      total += Float.parseFloat(tokenizer.nextToken());
	  count++;
      }
      private final static IntWritable key_out = new IntWritable(int_key);      
      private final static FloatWritable val = new FloatWritable(total /count );      
      output.collect(key_out, val);
    }
  }

    public static class Reduce extends MapReduceBase implements Reducer<IntWritable, FloatWritable, IntWritable, FloatWritable> {

	/**
	   reducer averages all keys
	 */
    public void reduce(IntWritable key, Iterator<FloatWritable> values, OutputCollector<IntWritable, FloatWritable> output, Reporter reporter) throws IOException {
     
	float sum = 0;
	int count = 0; 
      while (values.hasNext()) {
        sum += values.next().get();
	count++;
      }
      output.collect(key, new FloatWritable(sum / count));
    }
  }

  public static void main(String[] args) throws Exception {
    JobConf conf = new JobConf(WordCount.class);
    conf.setJobName("wordcount");

    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(IntWritable.class);

    conf.setMapperClass(Map.class);
    conf.setCombinerClass(Reduce.class);
    conf.setReducerClass(Reduce.class);

    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));

    JobClient.runJob(conf);
  }
}


