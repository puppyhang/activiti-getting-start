package com.ternence.activiti.controller;

import org.activiti.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理任务的接口，也就是待办任务的接口
 * 
 * @author Ternence
 */
@Controller
@RequestMapping("/process/assign")
public class ProcessAssignmentTaskController extends AbstractSystemController {

	@SuppressWarnings("unused")
	@Autowired
	private ProcessEngine processEngine;

}
