package com.myown.application.async_demo.felles;

public class MessageTool {
	
	private static final String BEGIN_SCRIPT_TAG = "<script type='text/javascript'>\n";

    private static final String END_SCRIPT_TAG = "</script>\n";
    
    private static final String JUNK = "<!-- Comet is a programming technique that enables web "
			+ "servers to send data to the client without having any need "
			+ "for the client to request it. -->\n";
    
	public String escape(String orig) {
        StringBuffer buffer = new StringBuffer(orig.length());

        for (int i = 0; i < orig.length(); i++) {
            char c = orig.charAt(i);
            switch (c) {
            case '\b':
                buffer.append("\\b");
                break;
            case '\f':
                buffer.append("\\f");
                break;
            case '\n':
                buffer.append("<br />");
                break;
            case '\r':
                // ignore
                break;
            case '\t':
                buffer.append("\\t");
                break;
            case '\'':
                buffer.append("\\'");
                break;
            case '\"':
                buffer.append("\\\"");
                break;
            case '\\':
                buffer.append("\\\\");
                break;
            case '<':
                buffer.append("&lt;");
                break;
            case '>':
                buffer.append("&gt;");
                break;
            case '&':
                buffer.append("&amp;");
                break;
            default:
                buffer.append(c);
            }
        }

        return buffer.toString();
    }
	
	public String toJsonp(String jsCmd, String name, String message) {
		//window.parent.app.update
        return jsCmd + "({ name: \"" + escape(name) + "\", message: \"" + escape(message) + "\" });\n";
    }
	
	public String asScript( String str )
	{
		return BEGIN_SCRIPT_TAG + str + END_SCRIPT_TAG;
	}

	public static String getJunk() {
		return JUNK;
	}

}
