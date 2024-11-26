package org.example.proflow.service;

import org.example.proflow.model.Profile;
import org.example.proflow.model.SubProject;
import org.example.proflow.repository.SubProjectRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SubProjectService {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    SubProjectRepository subProjectRepository;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public SubProjectService(SubProjectRepository subProjectRepository){
        this.subProjectRepository = subProjectRepository;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    public void addSubProject(SubProject subProject) throws SQLException{
        subProjectRepository.addSubProject(subProject);
    }

    //***READ PROFILE(S)***--------------------------------------------------------------------------------------------R
    public List<SubProject> getAllSubProjects(){
        return subProjectRepository.getAllSubProjects();
    }

    public SubProject getSubProjectById(int id) throws SQLException{
        return subProjectRepository.getSubProjectById(id);
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    public void updateSubProject(SubProject subProject) throws SQLException{
        subProjectRepository.updateSubProject(subProject);
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    public void deleteSubProject(int id) throws SQLException{
        subProjectRepository.deleteSubProject(id);
    }

    //**END***----------------------------------------------------------------------------------------------------------
}
