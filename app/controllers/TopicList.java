package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import play.*;
import views.html.*;

import models.*;

public class TopicList extends Controller{

	 public static Result GO_HOME = redirect(
		        routes.TopicList.list(0, "name", "asc", "")
		    );
		    
		    /**
		     * Handle default path requests, redirect to computers list
		     */
		    public static Result index() {
		        return GO_HOME;
		    }
	
	 public static Result list(int page, String sortBy, String order, String filter) {
	        return ok(
	            topiclist.render(
	                Topic.page(page, 10, sortBy, order, filter),
	                sortBy, order, filter
	            )
	        );
	    }
}
