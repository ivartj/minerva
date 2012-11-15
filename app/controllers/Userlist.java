package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import play.*;
import views.html.*;

import models.*;

/**
 * Manage a database of computers
 */
public class UserList extends Controller {
    
    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.UserList.list(0, "fullName", "asc", "")
    );
    
    /**
     * Handle default path requests, redirect to computers list
     */
    public static Result index() {
        return GO_HOME;
    }

    /**
     * Display the paginated list of computers.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on computer names
     */
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            userlist.render(
                User.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
   
    
   

}
            
