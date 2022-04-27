package org.camunda.bpm.extension.hooks.listeners;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.hooks.services.FormSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

/**
 * This class transforms all the form document data into CAM variables
 *
 * @author ji.zhao@telus.com
 */
@Named("ExtractManagerGUID")
public class ExtractManagerGUID  extends BaseListener implements TaskListener, ExecutionListener {

    @Autowired
    private FormSubmissionService formSubmissionService;

    @Override
    public void notify(DelegateExecution execution) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String guid = oidcUser.getClaimAsString("bcgovguid");
        String idir = oidcUser.getClaimAsString("idir");

        execution.setVariable("managerGuid", guid);
        execution.setVariable("managerIdir", idir);       
    }
}
