package quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

//import org.slf4j.Logger; 
//import org.slf4j.LoggerFactory; 
import org.quartz.DateBuilder;
import org.quartz.Job; 
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext; 
import org.quartz.JobExecutionException; 
import org.quartz.JobKey; 
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

//import static org.quartz.JobBuilder.newJob; 
import static org.quartz.TriggerBuilder.newTrigger; 


public class TestQ {
    public static void main(String[] args) throws Exception { 
    	SchedulerFactory sf = new StdSchedulerFactory();  
        Scheduler scheduler = sf.getScheduler();  

        //JobDetail Conveys the detail properties of a given Job instance. JobDetails are to be created/defined with JobBuilder.   
        //JobBuilder无构造函数，所以只能通过JobBuilder的静态方法newJob(Class<? extends Job> jobClass)生成JobBuilder实例  
        //withIdentity(String name,String group)参数用来定义jobKey，如果不设置，也会自动生成一个独一无二的jobKey用来区分不同的job  
        //build()方法 Produce the JobDetail instance defined by this JobBuilder.  
        JobDetail job = JobBuilder.newJob(JobTest.class).withIdentity("job1", "group1").build();  

        //use TriggerBuilder to instantiate an actual Trigger  
        //withIdentity(String name,String group)参数用来定义TriggerKey，如果不设置，也会自动生成一个独一无二的TriggerKey用来区分不同的trigger  
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey("trigger1", "group1")).startNow()  
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())  
                        .build();  

        // Tell quartz to schedule the job using our trigger  
        scheduler.scheduleJob(job, trigger);  
          
        // Start up the scheduler  
        scheduler.start();  
          
        //当前主线程睡眠2秒  
        System.out.println(Thread.currentThread().getName());  
        Thread.sleep(30*1000);  
          
        // shut down the scheduler  
        scheduler.shutdown(true);   
    	
    	
    	
    	
        //SimpleTriggerExample example = new SimpleTriggerExample(); 
        //example.run(); 
 
    } 
}



class JobTest implements Job{  
    
    //Instances of Job must have a public no-argument constructor  
    public JobTest(){  
          
    }  
      
    public void execute(JobExecutionContext arg0) throws JobExecutionException {  
        //看打印出的当前对象每次都不一样，就等于每次执行一次任务都新建一个job实例  
        System.out.println("我的任务就是调用当前Job："+this+"不断刷屏!!!");  
    }  
  
} 



class SimpleJob implements Job { 
	 
//    private static Logger _log = LoggerFactory.getLogger(SimpleJob.class); 
 
    public SimpleJob() {} 
 
 
    public void execute(JobExecutionContext context) 
        throws JobExecutionException { 
 
        JobKey jobKey = context.getJobDetail().getKey(); 
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        //这里的jobkey，API里是这样说的： 
        //它标识一个唯一的jobDetail 
        //key是由组名称和job名称组成,job名称必须是惟一的。 
        //如果只有一个组名称，那么它的组名称默认就被指定为这个组名称。 
        //意思就是job的组名称和job名称 
        System.out.println("jobKey: " + jobKey + "执行时间：" + sdf.format(new Date()));
    } 
 
} 


class SimpleTriggerExample { 
	 
    
    public void run() throws Exception { 
//        Logger log = LoggerFactory.getLogger(SimpleTriggerExample.class); 
 
        System.out.println("------- Initializing -------------------"); 
        SchedulerFactory sf = new StdSchedulerFactory(); 
        Scheduler sched = sf.getScheduler(); 
        System.out.println("------- Initialization Complete --------"); 
 
        System.out.println("------- Scheduling Jobs ----------------"); 
        //当前时间加15秒 
        Date startTime = DateBuilder.nextGivenSecondDate(null, 15); 
        JobDetail job = JobBuilder.newJob(SimpleJob.class) 
            .withIdentity("job1", "group1") 
            .build(); 
         
        SimpleTrigger trigger = (SimpleTrigger) newTrigger()  
            .withIdentity("trigger1", "group1") 
            .startAt(startTime) 
            .build(); 
 
        Date ft = sched.scheduleJob(job, trigger); 
        sched.addJob(job, true); 
        System.out.println(job.getKey() + 
                " will run at: " + ft +   
                " and repeat: " + trigger.getRepeatCount() +  
                " times, every " + trigger.getRepeatInterval() / 1000 + " seconds"); 
         
        System.out.println("------- 开始 Scheduling Jobs... --------------"); 
        System.out.println("------- 等待 30 秒... --------------"); 
        sched.start(); 
        try { 
            Thread.sleep(30 * 1000);  
        } catch (Exception e) { 
        } 
        
        System.out.println("------- 关闭 ---------------------"); 
        sched.shutdown(true); 
        System.out.println("------- 关闭完成 -----------------"); 
        SchedulerMetaData metaData = sched.getMetaData(); 
        System.out.println("执行了 " + metaData.getNumberOfJobsExecuted() + " 个工作。"); 
    } 
 

 
} 



