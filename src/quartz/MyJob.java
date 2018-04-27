package quartz;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class MyJob implements Job {

	static Map<String, String> ing = new HashMap<String, String>();

	@Override
	public void execute(JobExecutionContext context) {
		String taskId = null;
		try {
			JobDataMap newJobDataMap = context.getJobDetail().getJobDataMap();
			taskId = newJobDataMap.getString("taskId");
			if (taskId == null) {
				return;
			} else if (ing.get(taskId) != null) {
				System.out.println("开始任务失败，“" + taskId + "”正在执行！");
				return;
			} else {
				ing.put(taskId, "1");
				System.out.println("开始执行任务“" + taskId + "”");
			}

			for (int i = 0; i < 60; i++) {
				System.out.println(newJobDataMap.get("name") + "==" + (i + 1));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (taskId != null) {
				ing.remove(taskId);
				System.out.println("结束执行任务“" + taskId + "”");
			}
		}
	}

}
