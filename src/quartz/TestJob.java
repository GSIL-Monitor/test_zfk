package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job{  
    
    //Instances of Job must have a public no-argument constructor  
    public TestJob(){  
          
    }  
      
    public void execute(JobExecutionContext arg0) throws JobExecutionException {  
        //看打印出的当前对象每次都不一样，就等于每次执行一次任务都新建一个job实例  
        System.out.println("我的任务就是调用当前Job："+this+"不断刷屏!!!");  
    }  
  
} 
