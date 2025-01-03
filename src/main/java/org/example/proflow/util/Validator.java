package org.example.proflow.util;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.model.Profile;

public class Validator {

    public static boolean isValid(HttpSession session, int profileId) {
        Profile loggedInProfile = (Profile) session.getAttribute("profile");
        if( loggedInProfile == null)  {
            return false;
        }
        return loggedInProfile.getId() == profileId;
    }

    public static boolean isProjectOwned(int profileId, int projectProfileId) {
        return projectProfileId == profileId;
    }


}
