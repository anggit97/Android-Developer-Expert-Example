package com.anggitprayogo.dicoding.myjobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anggitprayogo.dicoding.myjobscheduler.service.GetCurrentWheatherJobService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnStart, btnCancel, btnCheckIsSchedulerIsRunning;
    private int jobId = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCancel = findViewById(R.id.btn_cancel);
        btnStart = findViewById(R.id.btn_start);
        btnCheckIsSchedulerIsRunning = findViewById(R.id.btn_check_scheduler);

        btnCancel.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnCheckIsSchedulerIsRunning.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startJob();
                break;
            case R.id.btn_cancel:
                cancelJob();
                break;
            case R.id.btn_check_scheduler:
                if (isJobServiceOn(this)){
                    Toast.makeText(this, "Scheduler running", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Scheduler not running", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startJob() {
//        ComponentName componentName = new ComponentName(this, GetCurrentWheatherJobService.class);
//        JobInfo.Builder builder = new JobInfo.Builder(jobId, componentName);
//
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//        builder.setRequiresDeviceIdle(true);
//        builder.setRequiresCharging(false);
//
//        //1000ms = 1 detik
//        builder.setPeriodic(10000);
//
//        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
//        jobScheduler.schedule(builder.build());
//
//        Toast.makeText(this, "Job services started", Toast.LENGTH_SHORT).show();

        ComponentName mServiceComponent = new ComponentName(this, GetCurrentWheatherJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);

        // 1000 ms = 1 detik
        builder.setPeriodic(18000);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show();
    }

    private void cancelJob() {
        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(jobId);
        Toast.makeText(this, "Job Service Canceled", Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean isJobServiceOn( Context context ) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE ) ;

        boolean hasBeenScheduled = false ;

        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == jobId ) {
                hasBeenScheduled = true ;
                break ;
            }
        }

        return hasBeenScheduled ;
    }
}
