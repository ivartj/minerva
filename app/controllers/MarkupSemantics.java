package controllers;

class MarkupSemantics extends mouse.runtime.SemanticsBase
{
	final static int CUTLINE = 50;

	private StringBuilder parsed = new StringBuilder();

	public String getParsed() {
		return parsed.toString();
	}

	private static String escape(String input) {
		StringBuilder out = new StringBuilder();
		for(char c : input.toCharArray()) {
			switch(c) {
			case '<':
				out.append("&lt;");
				break;
			case '>':
				out.append("&gt;");
				break;
			case '&':
				out.append("&amp;");
				break;
			case '\n':
				out.append("<br>\n");
				break;
			case '\t':
				out.append("&nbsp; &nbsp; &nbsp; &nbsp; ");
				break;
			default:
				out.append(c);
			}
		}
		return out.toString();
	}

	void post() {
		Object tmp;
		for(int i = 0; i < rhsSize(); i++) {
			tmp = (String)rhs(i).get();
			if(tmp != null)
				parsed.append((String)tmp);
		}
	}

	void escape() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < rhsSize(); i++)
			sb.append(rhs(i).text());
		String out = escape(sb.toString());
		lhs().put(out);
	}

	void paragraph() {
		StringBuilder out = new StringBuilder("<p>");
		for(int i = 0; i < rhsSize(); i++)
			out.append((String)rhs(i).get());
		out.append("</p>\n\n");
		lhs().put(out.toString());
	}

	void multispace() {
		boolean nonbreak = true;
		StringBuilder out = new StringBuilder("");
		for(int i = 0; i < rhsSize(); i++) {
			out.append(nonbreak ? "&nbsp;" : " ");
			nonbreak = nonbreak ? false : true;
		}
		lhs().put(out.toString());
	}

	void startspace() {
		lhs().put("&nbsp;");
	}

	void newlinespace() {
		lhs().put("<br>\n&nbsp;");
	}

	private static String contract(String url) {
		if(url.length() <= CUTLINE)
			return url;
		StringBuilder out = new StringBuilder(url.substring(0, CUTLINE / 2));
		out.append("...");
		out.append(url.substring(url.length() - CUTLINE / 4, url.length()));
		return out.toString();
	}

	void link() {
		StringBuilder url = new StringBuilder("");
		for(int i = 0; i < rhsSize(); i++)
			url.append(rhs(i).text());
		String out = "<a href=\"" + escape(url.toString()) + "\">" + escape(contract(url.toString())) + "</a>";
		lhs().put(out);
	}

}
