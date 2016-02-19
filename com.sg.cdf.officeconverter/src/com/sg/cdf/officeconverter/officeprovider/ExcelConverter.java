package com.sg.cdf.officeconverter.officeprovider;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ExcelConverter extends AbstractOfficeConverter {

	@Override
	protected ActiveXComponent getActiveXComponent() throws Exception {
		ActiveXComponent app = new ActiveXComponent("Excel.Application");
		app.setProperty("Visible", false);
		return app;
	}

	@Override
	protected Dispatch openDocument(ActiveXComponent app, String filename)
			throws Exception {
		Dispatch dis = app.getProperty("Workbooks").toDispatch();
		System.out.println("打开文档..." + filename);
		return Dispatch
				.invoke(dis,
						"Open",
						Dispatch.Method,
						new Object[] { filename, new Variant(false),
								new Variant(false) }, new int[9]).toDispatch();
	}

	@Override
	protected void convert(Dispatch dis, String toFilename) throws Exception {
		System.out.println("转换文档到PDF..." + toFilename);
		Dispatch.invoke(dis, "SaveAs", Dispatch.Method, new Object[] {
				toFilename, new Variant(57), new Variant(false),
				new Variant(57), new Variant(57), new Variant(false),
				new Variant(true), new Variant(57), new Variant(false),
				new Variant(true), new Variant(false) }, new int[1]);
	}

	@Override
	protected void dispose(ActiveXComponent app, Dispatch dis) throws Exception {
		if (dis != null) {
			Dispatch.call(dis, "Close", new Variant(false));
		}
		if (app != null) {
			app.invoke("Quit", new Variant[] {});
			app = null;
		}
	}

}
