package org.launchcode.controllers;

import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.launchcode.models.Job;
import org.launchcode.models.Location;
import org.launchcode.models.Employer;
import org.launchcode.models.CoreCompetency;
import org.launchcode.models.PositionType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {
        Job job = jobData.findById(id);
        model.addAttribute("job",job);
        System.out.println("$$$$$$$$$$$$$"+job.getName());
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addJob(@ModelAttribute @Valid JobForm jobForm, Errors errors) {
        if(errors.hasErrors()){
            return "new-job";
        }
        Location location = jobData.getLocations().findById(jobForm.getLocationId());
        Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
        PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionId());
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        Job job = new Job(jobForm.getName(), employer, location, positionType, coreCompetency);
        jobData.add(job);
        return "redirect:/job/?id="+job.getId();
    }
}
