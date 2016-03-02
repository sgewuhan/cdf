package com.sg.cdf.demo.app;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.client.service.UrlLauncher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.sg.cdf.core.request.ContentDistributionRequest;
import com.sg.cdf.core.request.CommonDistributionRequest;
import com.sg.cdf.core.request.IDistributionJob;
import com.sg.cdf.demo.demo2.contentprovider.ContentProviderDemo;
import com.sg.cdf.persistence.mongodb.MongoDBByteDistribution;

public class DemoEntryPoint extends AbstractEntryPoint {

	private static final String URL = "http://127.0.0.1:10080/cdfdistribute?id=demo.worknotice&name=http����";
	private static final String URL2 = "http://127.0.0.1:10080/cdfdistribute?"
			+ "id=demo.worknotice1" + "&name=http���ô��ݲ���" + "&db=pm2"
			+ "&col=document"
			+ "&condition={_id:{$oid:\"5282dacae0ccf8afc27a1a9f\"}}";
	private Browser browser;
	private Text addressBar;
	private Button confirm;
	private Button internalInvoke;
	private Text addressBar1;
	private Button confirm1;
	private Button button;

	@Override
	protected void createContents(Composite parent) {

		// 1.ʹ���������������
		addressBar = new Text(parent, SWT.BORDER);
		addressBar.setMessage("�������ַ");
		addressBar.setText(URL);

		confirm = new Button(parent, SWT.PUSH);
		confirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = addressBar.getText();
				browser.setUrl(text);
			}
		});
		confirm.setText("HTTP��ʽ����");
		browser = new Browser(parent, SWT.BORDER);

		// 2.ʹ��OSGi��ʽ����
		internalInvoke = new Button(parent, SWT.PUSH);
		internalInvoke.setText("�ڲ���ʽ����");
		internalInvoke.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				invoke();
			}
		});

		// 3.ʹ��Http��ʽ���ò����ݲ���
		addressBar1 = new Text(parent, SWT.BORDER);
		addressBar1.setMessage("�������ַ");
		addressBar1.setText(URL2);

		confirm1 = new Button(parent, SWT.PUSH);
		confirm1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = addressBar1.getText();
				UrlLauncher launcher = RWT.getClient().getService(
						UrlLauncher.class);
				launcher.openURL(text);
			}
		});
		confirm1.setText("HTTP��ʽ���ò����ݲ���");

		// 4. ��������Լ����ݲ���
		button = new Button(parent, SWT.NONE);
		button.setText("������ò����ݲ���");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				invoke2();
			}
		});
		layout(parent);
	}

	private void invoke2() {
		// ��������
		ContentDistributionRequest req = new ContentDistributionRequest(
				"�ֹ��ڲ����ò�������");

		// ע��־û�,�����ע�ᣬ�÷��������޷����棬Ҳ�޷������
		req.setPersistence(new MongoDBByteDistribution());

		// ���ò���
		req.setParameterValue("templateURL",
				"http://localhost:10080/html/sample.rptdesign");
		req.setParameterValue("dataURL",
				"http://localhost:10080/html/xmlpo.xml");

		// ע�������ṩ��
		req.registerContentProvider(new ContentProviderDemo());

	
		// ��ӷ�������ע��÷�������������չ�н���ע��
//		req.registeDistributor(new DistributorByEmailWithParameter());
		// ������������,������
		req.createDistributionJob(true);

	}

	private void invoke() {
		CommonDistributionRequest request = new ContentDistributionRequest(
				"demo.worknotice", "�ڲ����ò��ԣ�ʹ��Extention");
		IDistributionJob dj = request.createDistributionJob();
		dj.launch();
	}

	private void layout(Composite parent) {
		parent.setLayout(new FormLayout());
		FormData fd = new FormData();
		addressBar.setLayoutData(fd);
		fd.left = new FormAttachment(0, 10);
		fd.top = new FormAttachment(0, 10);
		fd.width = 360;

		fd = new FormData();
		confirm.setLayoutData(fd);
		fd.left = new FormAttachment(addressBar, 10);
		fd.top = new FormAttachment(0, 10);

		fd = new FormData();
		browser.setLayoutData(fd);
		fd.left = new FormAttachment();
		fd.top = new FormAttachment();
		fd.height = 1;
		fd.width = 1;

		fd = new FormData();
		internalInvoke.setLayoutData(fd);
		fd.left = new FormAttachment(0, 10);
		fd.top = new FormAttachment(addressBar, 10);

		fd = new FormData();
		addressBar1.setLayoutData(fd);
		fd.left = new FormAttachment(0, 10);
		fd.top = new FormAttachment(internalInvoke, 10);
		fd.width = 360;

		fd = new FormData();
		confirm1.setLayoutData(fd);
		fd.left = new FormAttachment(addressBar1, 10);
		fd.top = new FormAttachment(internalInvoke, 10);

		fd = new FormData();
		button.setLayoutData(fd);
		fd.left = new FormAttachment(0, 10);
		fd.top = new FormAttachment(confirm1, 10);
	}

}
