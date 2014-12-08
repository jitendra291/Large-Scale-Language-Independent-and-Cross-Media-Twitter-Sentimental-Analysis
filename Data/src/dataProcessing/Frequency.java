package dataProcessing;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Frequency {
	public static class Map extends Mapper<LongWritable, Text, Text, LongWritable>
	{
		private final static LongWritable one = new LongWritable(1);
		private final static Text word = new Text();
		public void map(LongWritable key, Text line, Context context) throws IOException, InterruptedException
		{
			HashMap<Text, Integer> hm = new HashMap<>();
			String [] values = line.toString().split(" ");
			for(String value:values)
			{
				Integer tmp = hm.get(new Text(value));
				if(tmp==null)
					tmp=1;
				else
					tmp++;
				hm.put(new Text(line), tmp );
			}
			for(String value:values)
			{
				Integer tmp = hm.get(new Text(value));
				if(tmp!=null)
				{
					context.write(new Text(value), new LongWritable(hm.get(new Text(value))));
					hm.put(new Text(value), 0);
				}
			}	
		}
	}
public static class Reduce extends Reducer<Text, LongWritable, Text, LongWritable>
{
	public void reduce (Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException
	{
		long sum =0;
		for(LongWritable value:values)
			sum += value.get();
		context.write(key, new LongWritable(sum));
		
	}
}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "Frequency");
		job.setJarByClass(Frequency.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		job.setMapperClass(Map.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.out.println(job.waitForCompletion(true) ? 0:1);
	}

}
