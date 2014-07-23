package com.sg.cdf.demo.demo2.contentprovider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.sg.birtcomposer.birt.BirtContentComposer;
import com.sg.cdf.core.content.Attachment;
import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.content.HTMLContentBody;
import com.sg.cdf.core.content.Timeliness;
import com.sg.cdf.core.request.IDistributionJob;

public class ContentProviderDemo extends ContentProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5604403271479270353L;

	public String templateURL;

	public String dataURL;

	public ContentProviderDemo() {
	}

	@Override
	public IStatus run(IDistributionJob job) {
		/*
		 * ���ñ���
		 */
		setTitle("��Ŀ�ܱ�");

		/*
		 * ���ùؼ���
		 */
		setKeyWord("��Ʒ�з�, �������, Ԥ�㳬֧״��");

		/*
		 * ������Ч��
		 */
		Calendar now = Calendar.getInstance();
		Date start = now.getTime();
		now.add(Calendar.YEAR, 1);
		Date end = now.getTime();
		Timeliness timeliness = new Timeliness(start, end);
		setTimeliness(timeliness);

		/*
		 * ʹ��birt��Ϊ�����������׼����������
		 */
		try {
			// ��ȡģ��
			URL url = new URL(templateURL);
			BirtContentComposer composer = new BirtContentComposer();
			composer.readReportDesign(url.openStream());
			composer.setDataSourcesProperties(
					BirtContentComposer.DS_PROP_FILELIST, dataURL);
			//����HTML��Ϊ����
			String htmlFileName = System.getProperty("java.io.tmpdir")
					+ Long.toHexString(System.currentTimeMillis()) + ".html";
			generateHTML(composer, htmlFileName);
			//����PDF��Ϊ����1
			String pdfFileName = System.getProperty("java.io.tmpdir")
					+ Long.toHexString(System.currentTimeMillis()) + ".pdf";
			generatePDF(composer, pdfFileName);
			//����Word��Ϊ����2
			String wordFileName = System.getProperty("java.io.tmpdir")
					+ Long.toHexString(System.currentTimeMillis()) + ".docx";
			generateDOCX(composer, wordFileName);

			//���õ�MainBody
			HTMLContentBody mainBody = new HTMLContentBody(new File(htmlFileName));
			setMainBody(mainBody);
			//���õ�����
			Attachment attachment = new Attachment();
			attachment.addFile(pdfFileName);
			attachment.addFile(wordFileName);
			setAttachment(attachment);
			
		} catch (Exception e) {
			return new Status(IStatus.ERROR, "com.sg.cdf.demo", IStatus.ERROR,
					e.getMessage(), e);
		}

		return Status.OK_STATUS;
	}

	// private void generate(InputStream template, String dataXml,
	// String outputFileName) throws MalformedURLException, Exception,
	// IOException {
	// BirtContentComposer composer = new BirtContentComposer();
	// // String dataXml = "http://localhost:10080/html/xmlpo.xml";
	//
	// composer.readReportDesign(template);
	// // ��������Դ�����ģ�����Ѿ�����������Դ���Բ�����
	// if (dataXml != null) {
	// composer.setDataSourcesProperties(
	// BirtContentComposer.DS_PROP_FILELIST, dataXml);
	// }
	//
	// generateHTML(composer, outputFileName);
	//
	// generatePDF(composer, outputFileName);
	//
	// generateDOCX(composer, outputFileName);
	//
	// generateEXCEL(composer, outputFileName);
	// }

	private void generateEXCEL(BirtContentComposer composer,
			String outputFileName) throws Exception {
		composer.setOutputType(BirtContentComposer.OUTPUT_EXCEL);
		FileOutputStream os = new FileOutputStream(outputFileName);
		try {
			composer.setOutPutStream(os);
			composer.generateReport();
		} finally {
			os.close();
		}
	}

	private void generateDOCX(BirtContentComposer composer,
			String outputFileName) throws Exception {
		composer.setOutputType(BirtContentComposer.OUTPUT_DOCX);
		FileOutputStream os = new FileOutputStream(outputFileName);
		try {
			composer.setOutPutStream(os);
			composer.generateReport();
		} finally {
			os.close();
		}
	}

	private void generatePDF(BirtContentComposer composer, String outputFileName)
			throws Exception {
		composer.setOutputType(BirtContentComposer.OUTPUT_PDF);
		OutputStream os = new FileOutputStream(outputFileName);
		try {
			composer.setOutPutStream(os);
			composer.generateReport();
		} finally {
			os.close();
		}
	}

	private void generateHTML(BirtContentComposer composer,
			String outputFileName) throws Exception {
		composer.setOutputType(BirtContentComposer.OUTPUT_HTML);
		FileOutputStream os = new FileOutputStream(outputFileName);
		try {
			composer.setOutPutStream(os);
			composer.generateReport();
		} finally {
			os.close();
		}
	}

	// public static IReportEngine getEngineNoOSGi(String engineHome) throws
	// Exception {
	// EngineConfig config = new EngineConfig();
	// config.setEngineHome(engineHome);
	// Platform.startup(config);
	// IReportEngineFactory factory = (IReportEngineFactory) Platform
	// .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
	// return factory.createReportEngine(config);
	// }

	public static void main(String[] args) {
		try {
			// ���Ҫ����ʹ�ã���Ҫ���lib
			BirtContentComposer composer = new BirtContentComposer();// "H:/birt-runtime-4_4_0/ReportEngine");

			InputStream template = ContentProviderDemo.class
					.getResourceAsStream("sample.rptdesign");
			composer.readReportDesign(template);
			// ��������Դ�����ģ�����Ѿ�����������Դ���Բ�����
			URL dataXml = ContentProviderDemo.class.getResource("xmlpo.xml");
			String outputFileName = "h:/output2";

			if (dataXml != null) {
				composer.setDataSourcesProperties(
						BirtContentComposer.DS_PROP_FILELIST, dataXml);
			}

			ContentProviderDemo demo = new ContentProviderDemo();
			demo.generateHTML(composer, outputFileName);
			System.out.println("HTML Generated!");

			demo.generatePDF(composer, outputFileName);
			System.out.println("PDF Generated!");

			demo.generateDOCX(composer, outputFileName);
			System.out.println("DOCX Generated!");

			demo.generateEXCEL(composer, outputFileName);
			System.out.println("EXCEL Generated!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
