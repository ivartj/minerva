package controllers;

import views.html.sandbox;
import play.mvc.*;
import mouse.runtime.SourceString;
import play.data.Form;

public class Markup extends Controller {

	private static Form<SandboxData> sandboxForm = form(SandboxData.class);

	public static class SandboxData {
		String markup;
	}

	public static Result sandbox() {
		String markup = "";

		try {
			String reqmarkup[] = request().body().asFormUrlEncoded().get("markup");
			markup = reqmarkup[0];
		} catch(Exception e) {}


		return ok(sandbox.render(markup, parse(markup)));
	}

	public static String parse(String markup) {
		MarkupParser parser = new MarkupParser();
		parser.parse(new SourceString(markup));
		return parser.semantics().getParsed();
	}

}
