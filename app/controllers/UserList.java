package controllers;

import java.util.*;

import play.mvc.*;
import play.data.*;
import play.*;

import views.html.*;

import models.*;

public class UserList extends Controller {
    
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            userlist.render(
                User.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
}
