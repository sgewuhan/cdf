package com.sg.cdf.officeconverter.officeprovider;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class WordConverter extends AbstractOfficeConverter {

	@Override
	protected ActiveXComponent getActiveXComponent() throws Exception {
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		app.setProperty("Visible", false);
		return app;
	}

	@Override
	protected Dispatch openDocument(ActiveXComponent app, String filename)
			throws Exception {
		Dispatch dis = app.getProperty("Documents").toDispatch();
		System.out.println("打开文档..." + filename);
		dis = Dispatch.invoke(
				dis,
				"Open",
				Dispatch.Method,
				new Object[] { filename, new Variant(false), new Variant(true),
						new Variant(false) }, new int[1]).toDispatch();
		Dispatch.put(dis, "RemovePersonalInformation", false);
		return dis;
	}

	@Override
	protected void convert(Dispatch dis, String toFilename) throws Exception {
		System.out.println("转换文档到PDF..." + toFilename);
		Dispatch.call(dis, "ExportAsFixedFormat", toFilename, 17);
	}

	@Override
	protected void dispose(ActiveXComponent app, Dispatch dis) throws Exception {
		if (dis != null) {
			Dispatch.call(dis, "Close", false);
		}
		if (app != null) {
			app.invoke("Quit", 0);
			app = null;
		}
	}
}
