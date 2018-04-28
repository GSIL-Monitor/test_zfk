package quartz;

import java.util.Date;

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
import org.quartz.impl.triggers.CronTriggerImpl;

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

			//数据
			JobDataMap newJobDataMap = new JobDataMap();
			newJobDataMap.put("taskId", "11");
			newJobDataMap.put("name", "zhufukunTask11");
			// job可以在scheduled的sched.start()方法前被调用
			JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("11")
					.usingJobData(newJobDataMap).build();
			//触发时间
			String cronExpression = "0 0/1 * * * 11";
			System.out.println(isValidExpression(cronExpression));
			System.out.println(new CronExpression(cronExpression));
			CronTrigger trigger = TriggerBuilder.newTrigger().withSchedule(
					CronScheduleBuilder.cronSchedule(cronExpression)).build();
			sched.scheduleJob(job, trigger);
			sched.start();
			System.out.println("sched start");

			Thread.sleep(1000 * 60);
			//删除任务
			sched.deleteJob(new JobKey("11"));
			System.out.println("sched delete 11");

			newJobDataMap = new JobDataMap();
			newJobDataMap.put("taskId", "11");
			newJobDataMap.put("name", "zhufukunTask22");
			job = JobBuilder.newJob(MyJob.class).withIdentity("11")
					.usingJobData(newJobDataMap).build();
			trigger = TriggerBuilder.newTrigger().withSchedule(
					CronScheduleBuilder.cronSchedule(cronExpression)).build();
			sched.scheduleJob(job, trigger);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean isValidExpression(final String cronExpression){
        try {
        	//new CronExpression(cronExpression)
        	CronTriggerImpl trigger = new CronTriggerImpl();
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date != null && date.after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
}
