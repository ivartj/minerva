package controllers;

import views.html.language;
import play.mvc.*;
import play.i18n.Lang;
import play.i18n.Messages;
import java.util.Map;
import models.FormToken;

public class Language extends Controller {

	// Retrieve localized message defined in a conf/messages* file.
	public static String get(String key, java.lang.Object... args) {
		return Messages.get(getLang(), key, args);
	}

	public static Result chooseLanguage() {
		Lang lang = getLangFromQueryString();
		if(lang != null) {
			changeLang(lang);
			String referer = request().getHeader("Referer");
			if(referer != null)
				return redirect(referer);
		}
		return ok(language.render());
	}

	private static void changeLang(Lang lang) {
		Map<String, String[]> queryMap;

		queryMap = request().queryString();
		if(FormToken.check("language", getMapString(queryMap, "formtoken")) == false && Authenticator.getCurrentUser() != null)
			return;
		response().setCookie("lang", lang.code());
	}

	public static Lang getLang() {
		Lang lang;

		lang = getLangFromQueryString();
		if(lang == null)
			lang = getLangFromCookies();
		if(lang == null)
			lang = lang();
		return lang;
	}

	private static Lang getLangFromQueryString() {
		String code;
		Lang lang = null;
		String reqlang[] = request().queryString().get("lang");
		if(reqlang == null || reqlang.length == 0)
			return null;
		code = reqlang[0];
		try {
			lang = Lang.forCode(code);
		} catch(Exception e) {
			lang = null;
		}
		return lang;
	}

	private static Lang getLangFromCookies() {
		String code;
		Lang lang = null;
		play.mvc.Http.Cookie cookie = request().cookies().get("lang");
		if(cookie == null)
			return null;
		code = cookie.value();
		try {
			lang = Lang.forCode(code);
		} catch(Exception e) {
			lang = null;
		}
		return lang;
	}

	private static String getMapString(Map<String, String[]> map, String key) {
		String array[];

		array = map.get(key);
		if(array == null)
			return "";
		if(array.length == 0)
			return "";
		return array[0];
	}

}
