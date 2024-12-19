package org.example.proflow.service;

import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;
import org.example.proflow.util.interfaces.SubProjectRepositoryInterface;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SubProjectService {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final SubProjectRepositoryInterface subProjectRepository;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public SubProjectService(SubProjectRepositoryInterface subProjectRepository) {
        this.subProjectRepository = subProjectRepository;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    public void addSubProject(SubProject subProject) throws SQLException {
        subProjectRepository.addSubProject(subProject);
    }

    //***READ PROFILE(S)***--------------------------------------------------------------------------------------------R
    public List<SubProject> getAllSubProjects() {
        return subProjectRepository.getAllSubProjects();
    }

    public SubProject getSubProjectById(int id) throws SQLException {
        return subProjectRepository.getSubProjectById(id);
    }

    public List<Task> getTasksFromSubProject(int subProjectId) throws SQLException {
        return subProjectRepository.getTasksFromSubProject(subProjectId);
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    public void updateSubProject(SubProject subProject) throws SQLException {
        subProjectRepository.updateSubProject(subProject);
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    public void deleteSubProject(int id) throws SQLException {
        subProjectRepository.deleteSubProject(id);
    }


    //Bruges i update, sørger for der ikke overskrives med null/0 når man ikke har skrevet noget
    public void mergeSubProject(SubProject source, SubProject target) {
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }
        if (source.getStartDate() != null) {
            target.setStartDate(source.getStartDate());
        }
        if (source.getEndDate() != null) {
            target.setEndDate(source.getEndDate());
        }
        if (source.getStatus() != null) {
            target.setStatus(source.getStatus());
        }
        if (source.getBudget() != 0) {
            target.setBudget(source.getBudget());
        }
        if (source.getAssignedTo() != null) {
            target.setAssignedTo(source.getAssignedTo());
        }
        if (source.getTotalEstHours() != 0) {
            target.setTotalEstHours(source.getTotalEstHours());
        }
        if (source.getActualPrice() != 0) {
            target.setActualPrice(source.getActualPrice());
        }
    }


    //**END***----------------------------------------------------------------------------------------------------------
}
