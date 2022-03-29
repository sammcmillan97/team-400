package nz.ac.canterbury.seng302.portfolio.service;

import nz.ac.canterbury.seng302.portfolio.model.Project;
import nz.ac.canterbury.seng302.portfolio.model.ProjectRepository;
import nz.ac.canterbury.seng302.portfolio.model.Sprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// more info here https://codebun.com/spring-boot-crud-application-using-thymeleaf-and-spring-data-jpa/

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository repository;
    @Autowired
    private SprintService sprintService;

    /**
     * Get list of all projects
     */
    public List<Project> getAllProjects() {
        List<Project> list = (List<Project>) repository.findAll();
        return list;
    }

    /**
     * Get project by id
     */
    public Project getProjectById(Integer id) throws Exception {

        Optional<Project> project = repository.findById(id);
        if(project!=null) {
            return project.get();
        }
        else
        {
            throw new Exception("Project not found");
        }
    }

    public void saveProject(Project project) {
        Project projectSaved = repository.save(project);

    }

    public void deleteProjectById(int id) {
        repository.deleteById(id);
    }

    public String getSprintsAsJson(int projectId) {

        List<Sprint> sprints = sprintService.getByParentProjectId(projectId);

        boolean firstLoop = true;
        StringBuilder sprintsJson = new StringBuilder();
        String currentSprint;
        for (Sprint sprint: sprints) {
            currentSprint = "";
            currentSprint += "\"id\":\"" + sprint.getId() + '"';
            currentSprint += ", \"title\":\"" + sprint.getLabel() + '"';
            currentSprint += ", \"startDate\":\"" + sprint.getStartDateCalendarString() + '"';
            currentSprint += ", \"endDate\":\"" + sprint.getDayAfterEndDateCalendarString() + '"';

            currentSprint = "{" + currentSprint + "}";
            if (firstLoop) {
                sprintsJson.append(currentSprint);
                firstLoop = false;
            } else {
                sprintsJson.append(", ").append(currentSprint);
            }

        }
        return "{" + sprintsJson + "}";
    }
}
