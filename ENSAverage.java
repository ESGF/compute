package gov.llnl.aims;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class TSAverage {

  public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, FloatWritable> {

      Integer latSize;
      Integer lonSize;

      public void configure(JobConf job) {
          super.configure(job);
	  
          latSize=job.getInt("latSize", -1);
          lonSize=job.getInt("lonSize", -1);

      }


      /**
	 Mapper averages all values across the line
       */
    public void map(LongWritable key, Text value, OutputCollector<IntWritable, FloatWritable> output, Reporter reporter) throws IOException {
      String line = value.toString();


      StringTokenizer tokenizer = new StringTokenizer(line);

      int count = 0 ;
      long int_key =0;
      float vv =0; 


      while (tokenizer.hasMoreTokens()) {
	  switch (count) {
	  case 0:
	      
	      int_key = latSize * lonSize *Integer.parseInt(tokenizer.nextToken());
	      break;
	  case 1:
	      int_key += latSize * Integer.parseInt(tokenizer.nextToken());
	      break;
	  case 2:
	      int_key += Integer.parseInt(tokenizer.nextToken());
	      break;
	  case 3:
	      total = Float.parseFloat(tokenizer.nextToken());
	      break;
	  default:
	      throw new Exception("Help!");
	  count++;
      }
      LongWritable key_out = new LongWritable(int_key);      
      FloatWritable val = new FloatWritable(total /count );      

      output.collect(key_out, val);
    }
  }

    public static class Reduce extends MapReduceBase implements Reducer<LongWritable, FloatWritable, LongWritable, FloatWritable> {

	/**
	   reducer averages all keys
	 */
    public void reduce(LongWritable key, Iterator<FloatWritable> values, OutputCollector<LongWritable, FloatWritable> output, Reporter reporter) throws IOException {
     
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
    JobConf conf = new JobConf(TSAverage.class);
    conf.setJobName("time-splice-avergae");
    conf.set("LatSize", Integer.parseInt(args[2]));
    conf.set("LonSize", Integer.parseInt(args[3]));


    conf.setOutputKeyClass(LongWritable.class);
    conf.setOutputValueClass(FloatWritable.class);

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


