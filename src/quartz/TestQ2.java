package quartz;

import java.util.Locale;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TestQ2 {

	private static Scheduler sched = null;

	public static void main(String[] args) {
		try {

			// 首先，必需要取得一个Scheduler的引用
			if (sched != null) {
				sched.shutdown();
			}
			SchedulerFactory sf = new StdSchedulerFactory();
			sched = sf.getScheduler();

			// jobs可以在scheduled的sched.start()方法前被调用
			// job 1将每隔20秒执行一次
			JobDataMap newJobDataMap = new JobDataMap();
			newJobDataMap.put("taskId", "11");
			newJobDataMap.put("name", "zhufukunTask11");
			JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("11")
					.usingJobData(newJobDataMap).build();
			CronTrigger trigger = TriggerBuilder.newTrigger().withSchedule(
					CronScheduleBuilder.cronSchedule("0 0/1 * * * ?")).build();
			sched.scheduleJob(job, trigger);
			sched.start();
			System.out.println("sched start");

			String cronExpression = "0 0/1 * * * ?";
			System.out.println(new CronExpression(cronExpression));

			Thread.sleep(1000 * 60 * 2);
			sched.deleteJob(new JobKey("11"));
			System.out.println("sched delete 11");

			newJobDataMap = new JobDataMap();
			newJobDataMap.put("taskId", "11");
			newJobDataMap.put("name", "zhufukunTask22");
			job = JobBuilder.newJob(MyJob.class).withIdentity("11")
					.usingJobData(newJobDataMap).build();
			trigger = TriggerBuilder.newTrigger().withSchedule(
					CronScheduleBuilder.cronSchedule("0 0/1 * * * ?")).build();
			sched.scheduleJob(job, trigger);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
