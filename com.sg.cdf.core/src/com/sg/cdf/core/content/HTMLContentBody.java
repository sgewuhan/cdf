package com.sg.cdf.core.content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLContentBody extends TextContentBody {

	private static String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // ����script��������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script>
																											// }

	private static String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // ����style��������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style>
																											// }

	private static String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ

	private URL url;

	private File htmlFile;

	private String html;

	public HTMLContentBody(URL url) {
		super("");
		this.setUrl(url);
	}

	public HTMLContentBody(String html) {
		super("");
		this.setHtml(html);
	}

	public HTMLContentBody(File htmlFile) {
		super("");
		this.setHtmlFile(htmlFile);
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
		setText(html2Text(html));
	}

	public File getHtmlFile() {
		return htmlFile;
	}

	public void setHtmlFile(File htmlFile) {
		this.htmlFile = htmlFile;

		InputStream stream = null;
		try {
			stream = new FileInputStream(htmlFile);
			String htmlString = getStringFromInputStream(stream);
			setHtml(htmlString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
			}
		}
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;

		// ����Ϊhtml�ı�
		InputStream stream = null;
		try {
			stream = url.openStream();
			String htmlString = getStringFromInputStream(stream);
			setHtml(htmlString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
			}
		}
	}

	public String html2Text(String inputString) {
		String htmlStr = inputString; // ��html��ǩ���ַ���
		String textStr = "";
		try {

			Pattern p_script = Pattern.compile(regEx_script,
					Pattern.CASE_INSENSITIVE);
			Matcher m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // ����script��ǩ

			Pattern p_style = Pattern.compile(regEx_style,
					Pattern.CASE_INSENSITIVE);
			Matcher m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // ����style��ǩ

			Pattern p_html = Pattern.compile(regEx_html,
					Pattern.CASE_INSENSITIVE);
			Matcher m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // ����html��ǩ

			textStr = htmlStr;
		} catch (Exception e) {
		}
		return textStr;// �����ı��ַ���
	}

	

	public String getStringFromInputStream(InputStream stream)
			throws UnsupportedEncodingException, IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, "utf-8")); //$NON-NLS-1$
			StringBuilder builder = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				builder.append(line);
				builder.append('\n');
				line = reader.readLine();
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}
}
