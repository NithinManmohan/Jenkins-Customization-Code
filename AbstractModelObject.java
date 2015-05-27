/*
 * Added the getCurrentlyLoggedInUser method, which retrieves the username of the user currently logged in to jenkins
 * 
 */
package hudson.model;

import hudson.model.Cause.UserCause;
import hudson.model.Cause.UserIdCause;
import hudson.search.SearchFactory;

import org.acegisecurity.context.SecurityContextHolder;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.Stapler;

import javax.servlet.ServletException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import hudson.search.SearchableModelObject;
import hudson.search.Search;
import hudson.search.SearchIndexBuilder;
import hudson.search.SearchIndex;
import hudson.slaves.OfflineCause;

/**
 * {@link ModelObject} with some convenience methods.
 * 
 * @author Kohsuke Kawaguchi
 */
public abstract class AbstractModelObject implements SearchableModelObject {
    /**
     * Displays the error in a page.
     */
    protected final void sendError(Exception e, StaplerRequest req, StaplerResponse rsp) throws ServletException, IOException {
        req.setAttribute("exception", e);
        sendError(e.getMessage(),req,rsp);
    }

    protected final void sendError(Exception e) throws ServletException, IOException {
        sendError(e,Stapler.getCurrentRequest(),Stapler.getCurrentResponse());
    }

    protected final void sendError(String message, StaplerRequest req, StaplerResponse rsp) throws ServletException, IOException {
        req.setAttribute("message",message);
        rsp.forward(this,"error",req);
    }

    /**
     * @param pre
     *      If true, the message is put in a PRE tag.
     */
    protected final void sendError(String message, StaplerRequest req, StaplerResponse rsp, boolean pre) throws ServletException, IOException {
        req.setAttribute("message",message);
        if(pre)
            req.setAttribute("pre",true);
        rsp.forward(this,"error",req);
    }

    protected final void sendError(String message) throws ServletException, IOException {
        sendError(message,Stapler.getCurrentRequest(),Stapler.getCurrentResponse());
    }

    /**
     * Convenience method to verify that the current request is a POST request.
     * 
     * @deprecated 
     *      Use {@link RequirePOST} on your method.
     */
    protected final void requirePOST() throws ServletException {
        StaplerRequest req = Stapler.getCurrentRequest();
        if (req==null)  return; // invoked outside the context of servlet
        String method = req.getMethod();
        if(!method.equalsIgnoreCase("POST"))
            throw new ServletException("Must be POST, Can't be "+method);
    }

    /**
     * Default implementation that returns empty index.
     */
    protected SearchIndexBuilder makeSearchIndex() {
        return new SearchIndexBuilder().addAllAnnotations(this);
    }

    public final SearchIndex getSearchIndex() {
        return makeSearchIndex().make();
    }

    public Search getSearch() {
        for (SearchFactory sf : SearchFactory.all()) {
            Search s = sf.createFor(this);
            if (s!=null)
                return s;
        }
        return new Search();
    }

    /**
     * Default implementation that returns the display name.
     */
    public String getSearchName() {
        return getDisplayName();
    }
    
    public String getCurrentlyLoggedInUser() {
    	String userId =	SecurityContextHolder.getContext().getAuthentication().getName(); 
    	User u = User.get(userId, false);
    	String loggedInUser = u.getFullName();
    	if(!loggedInUser.isEmpty())	{
    		return loggedInUser; //returns the user who is currently logged in to Jenkins
    	}
    	else {
    		return "anonymous";
    	}
    }
}
