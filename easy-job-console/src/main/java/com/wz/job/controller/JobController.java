package com.wz.job.controller;

import com.wz.job.common.mapper.JobLogMapper;
import com.wz.job.common.mapper.JobTaskMapper;
import com.wz.job.common.model.JobLog;
import com.wz.job.common.model.JobTask;
import com.wz.job.common.utils.Constants;
import com.wz.job.common.utils.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>控制任务的增删改查</p>
 * Created by wangzi on 2017-09-24.
 */
@Controller
@RequestMapping("/job")
public class JobController {
    private static final String PREFIX = "templates";
    @Autowired
    private JobTaskMapper taskMapper;
    @Autowired
    private JobLogMapper logMapper;
    @Autowired
    private ZkClient zkClient;

    @RequestMapping("/tablePage")
    public String showTable() {
        return PREFIX + "/table";
    }

    @RequestMapping(value = "/searchJob")
    @ResponseBody
    public Map<String, Object> searchJob(@RequestParam(value = "jobName", required = false) String jobName,
                                      @RequestParam(value = "pageSize", required = false) String pageSize,
                                      @RequestParam(value = "offset", required = false) String offset) {
        List<JobTask> list;
        if (StringUtils.isBlank(pageSize) || StringUtils.isBlank(offset)) {
            pageSize = Constants.DEFAULT_PAGESIZE;
            offset = Constants.DEFAULT_OFFSET;
        }
        list = taskMapper.queryJobs(jobName, pageSize, offset);
        if (list != null) {
            list.forEach(l -> {
                String status = zkClient.getData(Constants.ROOT_PATH + "/" + l.getJobId());
                if (StringUtils.isNotEmpty(status) && Constants.STATUS_START.equals(status.substring(0, status.indexOf(Constants.DATA_SPLIT)))) {
                    l.setStatus(true);
                }
            });
        }
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("rows", list);
        result.put("total", taskMapper.queryJobCount(jobName));
        return result;
    }

    @ResponseBody
    @RequestMapping("/searchLog")
    public Map<String, Object>  searchLog(Integer jobId) {
        Map<String,Object> map = new HashMap<>(1);
        List<JobLog> list = logMapper.queryLogsByJobId(jobId);
        map.put("rows",list);
        return map;
    }

    @RequestMapping("/createJobPage")
    public String showCreate() {
        return PREFIX + "/create";
    }

    @RequestMapping("/createJob")
    public String create(JobTask job) {
        if (StringUtils.isBlank(job.getDubboAppProtocol())){
            job.setDubboAppProtocol(Constants.DEFAULT_DUBBOPROTOCOL);
        }
        if (StringUtils.isBlank(job.getZkAddress())){
            job.setZkAddress(Constants.DEFAULT_ZKADDRESS);
        }
        taskMapper.insertJob(job);
        zkClient.createPath(Constants.ROOT_PATH + "/" + job.getJobId(), Constants.STATUS_STOP + "_" + job.getJobType());
        return PREFIX + "/table";
    }

    @RequestMapping("/updateJobPage")
    public String update(Integer jobId, ModelMap model) {
        JobTask job = taskMapper.queryJobById(jobId);
        model.addAttribute("jobModel", job);
        return PREFIX + "/update";
    }

    @RequestMapping("/updateJob")
    public String updateJob(JobTask job) {
        taskMapper.updateJob(job);
        return PREFIX + "/table";
    }

    @RequestMapping("/deleteJob")
    public String deleteJob(Integer jobId) {
        taskMapper.deleteJob(jobId);
        zkClient.deletePath(Constants.ROOT_PATH + "/" + jobId);
        return PREFIX + "/page";
    }

    @RequestMapping("/viewJobPage")
    public String viewJob(Integer jobId, ModelMap model) {
        JobTask job =  taskMapper.queryJobById(jobId);
        model.addAttribute("jobModel", job);
        return PREFIX + "/view";
    }

    @RequestMapping("/startJob")
    public String startJob(String jobId, String jobType) {
        zkClient.updateData(Constants.ROOT_PATH + "/" + jobId, Constants.STATUS_START + "_" + jobType);
        return PREFIX + "/table";
    }

    @RequestMapping("/stopJob")
    public String stopJob(String jobId, String jobType) {
        zkClient.updateData(Constants.ROOT_PATH + "/" + jobId, Constants.STATUS_STOP + "_" + jobType);
        return PREFIX + "/table";
    }
}
