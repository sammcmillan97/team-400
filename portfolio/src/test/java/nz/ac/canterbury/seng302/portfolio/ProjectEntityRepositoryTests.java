package nz.ac.canterbury.seng302.portfolio;

import nz.ac.canterbury.seng302.portfolio.entities.ProjectEntity;
import nz.ac.canterbury.seng302.portfolio.entities.SprintEntity;
import nz.ac.canterbury.seng302.portfolio.repositories.ProjectEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
public class ProjectEntityRepositoryTests {

    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private ProjectEntityRepository projectEntityRepository;

    @BeforeEach
    void cleanDatabase() {
        projectEntityRepository.deleteAll();
    }

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(projectEntityRepository).isNotNull();
    }

    @Test
    void findProjects() {
        ProjectEntity project1 = new ProjectEntity("Project1", "Test Project", Date.valueOf("2022-04-15"), Date.valueOf("2022-05-16"));
        ProjectEntity project2 = new ProjectEntity("Project1", "Test Project", Date.valueOf("2022-03-22"), Date.valueOf("2022-04-01"));
        List<ProjectEntity> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projectEntityRepository.saveAll(projects);
        List<ProjectEntity> projectsFromDatabase = StreamSupport.stream(projectEntityRepository.findAll().spliterator(), false).toList();
        assertThat(projectsFromDatabase.get(0).getProject_id()).isEqualTo(projects.get(0).getProject_id());
        assertThat(projectsFromDatabase.get(0).getProject_name()).isEqualTo(projects.get(0).getProject_name());
        assertThat(projectsFromDatabase.get(0).getDescription()).isEqualTo(projects.get(0).getDescription());
        assertThat(projectsFromDatabase.get(0).getStart_date()).isEqualTo(projects.get(0).getStart_date());
        assertThat(projectsFromDatabase.get(0).getEnd_date()).isEqualTo(projects.get(0).getEnd_date());
        assertThat(projectsFromDatabase.get(1).getProject_id()).isEqualTo(projects.get(1).getProject_id());
        assertThat(projectsFromDatabase.get(1).getProject_name()).isEqualTo(projects.get(1).getProject_name());
        assertThat(projectsFromDatabase.get(1).getDescription()).isEqualTo(projects.get(1).getDescription());
        assertThat(projectsFromDatabase.get(1).getStart_date()).isEqualTo(projects.get(1).getStart_date());
        assertThat(projectsFromDatabase.get(1).getEnd_date()).isEqualTo(projects.get(1).getEnd_date());
    }

    @Test
    void findProjectById() {
        ProjectEntity project1 = new ProjectEntity("Project1", "Test Project", Date.valueOf("2022-04-15"), Date.valueOf("2022-05-16"));
        ProjectEntity project2 = new ProjectEntity("Project1", "Test Project", Date.valueOf("2022-03-22"), Date.valueOf("2022-04-01"));
        List<ProjectEntity> projects = new ArrayList<ProjectEntity>();
        projects.add(project1);
        projects.add(project2);
        projectEntityRepository.saveAll(projects);
        ProjectEntity retrievedProject1 = projectEntityRepository.findById(projects.get(0).getProject_id()).orElse(null);
        ProjectEntity retrievedProject2 = projectEntityRepository.findById(projects.get(1).getProject_id()).orElse(null);

        assertThat(retrievedProject1).isNotNull();
        assertThat(retrievedProject1.getProject_id()).isEqualTo(projects.get(0).getProject_id());
        assertThat(retrievedProject1.getProject_name()).isEqualTo(projects.get(0).getProject_name());
        assertThat(retrievedProject1.getDescription()).isEqualTo(projects.get(0).getDescription());
        assertThat(retrievedProject1.getStart_date()).isEqualTo(projects.get(0).getStart_date());
        assertThat(retrievedProject1.getEnd_date()).isEqualTo(projects.get(0).getEnd_date());

        assertThat(retrievedProject2).isNotNull();
        assertThat(retrievedProject2.getProject_id()).isEqualTo(projects.get(1).getProject_id());
        assertThat(retrievedProject2.getProject_name()).isEqualTo(projects.get(1).getProject_name());
        assertThat(retrievedProject2.getDescription()).isEqualTo(projects.get(1).getDescription());
        assertThat(retrievedProject2.getStart_date()).isEqualTo(projects.get(1).getStart_date());
        assertThat(retrievedProject2.getEnd_date()).isEqualTo(projects.get(1).getEnd_date());

    }

    @Test
    void updateSprint() {
        ProjectEntity project1 = new ProjectEntity("Project1", "Test Project", Date.valueOf("2022-04-15"), Date.valueOf("2022-05-16"));

        projectEntityRepository.save(project1);

        // Check that the project was inserted correctly
        ProjectEntity retrievedProject = projectEntityRepository.findById(project1.getProject_id()).orElse(null);
        assertThat(retrievedProject).isNotNull();
        assertThat(retrievedProject.getProject_id()).isEqualTo(project1.getProject_id());
        assertThat(retrievedProject.getProject_name()).isEqualTo(project1.getProject_name());
        assertThat(retrievedProject.getDescription()).isEqualTo(project1.getDescription());
        assertThat(retrievedProject.getStart_date()).isEqualTo(project1.getStart_date());
        assertThat(retrievedProject.getEnd_date()).isEqualTo(project1.getEnd_date());

        ProjectEntity newProject = new ProjectEntity(project1.getProject_id(), "Changed Project Name", project1.getDescription(), Date.valueOf("2022-04-15"), Date.valueOf("2022-05-16"));
        projectEntityRepository.save(newProject);

        // Use original project id to fetch updated sprint to confirm it's using the same id
        // Check that the project was updated correctly
        retrievedProject = projectEntityRepository.findById(project1.getProject_id()).orElse(null);
        assertThat(retrievedProject).isNotNull();
        assertThat(retrievedProject.getProject_id()).isEqualTo(newProject.getProject_id());
        assertThat(retrievedProject.getProject_name()).isEqualTo(newProject.getProject_name());
        assertThat(retrievedProject.getDescription()).isEqualTo(newProject.getDescription());
        assertThat(retrievedProject.getStart_date()).isEqualTo(newProject.getStart_date());
        assertThat(retrievedProject.getEnd_date()).isEqualTo(newProject.getEnd_date());

    }
}
