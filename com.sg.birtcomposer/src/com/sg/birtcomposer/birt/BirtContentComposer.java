package com.sg.birtcomposer.birt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.birt.report.engine.api.DocxRenderOption;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.core.runtime.Assert;

import com.sg.birtcomposer.ComposerActivator;

public class BirtContentComposer {

	public static final int OUTPUT_HTML = 0;

	public static final int OUTPUT_PDF = 1;

	public static final int OUTPUT_DOCX = 2;

	public static final int OUTPUT_EXCEL = 3;

	public static final String DS_PROP_FILELIST = "FILELIST";

	private IReportRunnable reportDesign;

	private Map<String, Object> taskParameter;

	private OutputStream outPutStream;

	private int outputType = 0;

	private IReportEngine engine;
	
	public BirtContentComposer(){
		engine = ComposerActivator.getEngine();
	}

	/**
	 * 传递到报表的参数和值
	 * 
	 * @param parameter
	 * @param value
	 */
	public void setTaskParameterValue(String parameter, Object value) {
		if (taskParameter == null) {
			taskParameter = new HashMap<String, Object>();
			taskParameter.put(parameter, value);
		}
	}

	public void generateReport() throws Exception {

		/*
		 * 加载设计模板
		 */
		Assert.isNotNull(reportDesign,
				"Must set report design before generate ContentBody");

		IRunAndRenderTask task = createTask();

		task.run();
		task.close();
	}

	private IRunAndRenderTask createTask() {

		IRunAndRenderTask task = engine.createRunAndRenderTask(reportDesign);

		IRenderOption renderOptions = createRenderOption();
		// 设置渲染
		Assert.isNotNull(renderOptions,
				"Must set report render option before generate ContentBody");

		task.setRenderOption(renderOptions);

		// 设置参数
		if (taskParameter != null) {
			Iterator<String> iter = taskParameter.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				task.setParameterValue(key, taskParameter.get(key));
			}
		}

		return task;
	}

	private IRenderOption createRenderOption() {
		switch (outputType) {
		case OUTPUT_HTML:
			return getHtmlRenderOption();
		case OUTPUT_PDF:
			return getPdfRenderOption();
		case OUTPUT_DOCX:
			return getDocxRenderOption();
		case OUTPUT_EXCEL:
			return getExcelRenderOption();
		default:
			break;
		}
		return null;
	}

	private IRenderOption getDocxRenderOption() {
		DocxRenderOption render = new DocxRenderOption();
		render.setOutputFormat("docx");
		OutputStream stream = getOutPutStream();
		Assert.isNotNull(stream,
				"Must set output stream before generate ContentBody");
		render.setOutputStream(stream);
		render.setOption(IRenderOption.HTML_PAGINATION, Boolean.TRUE);
		render.setEmitterID("org.eclipse.birt.report.engine.emitter.docx");
		render.setOption(IRenderOption.RENDER_DPI, 96);
		render.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
		return render;
	}

	private IRenderOption getExcelRenderOption() {
		EXCELRenderOption render = new EXCELRenderOption();
		render.setOutputFormat("xlsx");
		OutputStream stream = getOutPutStream();
		Assert.isNotNull(stream,
				"Must set output stream before generate ContentBody");
		render.setOutputStream(stream);
		render.setEmitterID("org.eclipse.birt.report.engine.emitter.xlsx");
		render.setSupportedImageFormats("PNG");
		return render;
	}

	private IRenderOption getPdfRenderOption() {
		PDFRenderOption pdfRender = new PDFRenderOption();
		OutputStream stream = getOutPutStream();
		Assert.isNotNull(stream,
				"Must set output stream before generate ContentBody");
		pdfRender.setOutputFormat(IRenderOption.OUTPUT_FORMAT_PDF);
		pdfRender.setOutputStream(stream);
		return pdfRender;
	}

	private IRenderOption getHtmlRenderOption() {
		HTMLRenderOption htmlRender = new HTMLRenderOption();
		htmlRender.setOutputFormat(HTMLRenderOption.HTML);
		OutputStream stream = getOutPutStream();
		Assert.isNotNull(stream,
				"must set output stream before generate ContentBody");
		htmlRender.setOutputStream(stream);
		htmlRender.setImageDirectory(System.getProperty("java.io.tmpdir"));
		htmlRender.setSupportedImageFormats("PNG");
		return htmlRender;
	}

	/**
	 * 更新数据源的属性和值
	 * 
	 * @param properties
	 * @throws SemanticException
	 */
	public void updateDataSources(Map<String, Object> properties)
			throws Exception {
		ReportDesignHandle designHandle = (ReportDesignHandle) reportDesign
				.getDesignHandle();
		SlotHandle dataSources = designHandle.getDataSources();
		int count = dataSources.getCount();
		for (int i = 0; i < count; i++) {
			DesignElementHandle handle = dataSources.get(i);
			// map.put("FILELIST", dataURL);
			handle.setProperties(properties);
		}
	}

	/**
	 * 设置数据源属性值,例如设置XML格式的数据源，{@link PROP_FILELIST}
	 * 
	 * @param propName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @throws SemanticException
	 */
	public void setDataSourcesProperties(String propName, Object value)
			throws Exception {
		Assert.isNotNull(reportDesign,
				"reportDesign cannot be null, inboke readReportDesign first.");
		ReportDesignHandle designHandle = (ReportDesignHandle) reportDesign
				.getDesignHandle();
		SlotHandle dataSources = designHandle.getDataSources();
		int count = dataSources.getCount();
		for (int i = 0; i < count; i++) {
			DesignElementHandle handle = dataSources.get(i);
			handle.setProperty(propName, value);
		}
	}

	/**
	 * 由流产生报表设计器
	 * 
	 * @param fs
	 * @throws EngineException
	 * @throws IOException
	 */
	public void readReportDesign(InputStream fs) throws Exception {
		try {
			reportDesign = engine.openReportDesign(fs);
		} finally {
			fs.close();
		}
	}

	public OutputStream getOutPutStream() {
		return outPutStream;
	}

	public void setOutPutStream(OutputStream outPutStream) {
		this.outPutStream = outPutStream;
	}

	public int getOutputType() {
		return outputType;
	}

	public void setOutputType(int outputType) {
		this.outputType = outputType;
	}

}
