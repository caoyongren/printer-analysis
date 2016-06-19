package com.github.openthos.printer.localprint.task;

import com.github.openthos.printer.localprint.APP;
import com.github.openthos.printer.localprint.model.JobItem;

import java.util.List;

/**
 * 取消打印任务 C3
 * Created by bboxh on 2016/6/5.
 */
public class JobCancelTask<Progress> extends CommandTask<JobItem, Progress, Boolean> {
    @Override
    protected String[] setCmd(JobItem... params) {

        JobItem item = params[0];

        return new String[]{"sh", "proot.sh", "cancel",String.valueOf(item.getJobId())};
    }

    @Override
    protected Boolean handleCommand(List<String> stdOut, List<String> stdErr) {
        boolean stat = true;
        // TODO: 2016/6/5  取消打印任务 C3
        for(String line:stdErr){
            if (line.contains("cancel: cancel-job failed:") && line.contains("is already canceled - can't cancel.")) {
                stat = false;
                ERROR = line;
            }
            if (line.contains("cancel: cancel-job failed:") && line.contains("does not exist.")){
                stat = false;
                ERROR = line;
            }
        }

        APP.sendRefreshJobsIntent();        //发送更新打印任务信息Intent

        return stat;
    }

    @Override
    protected String bindTAG() {
        return "JobCancelTask";
    }
}
