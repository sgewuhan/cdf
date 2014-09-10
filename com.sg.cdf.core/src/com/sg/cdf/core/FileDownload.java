package com.sg.cdf.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	      String filename = request.getParameter("name"); //$NON-NLS-1$

	        byte[] download = getBytes(request);

	        if (download == null || download.length == 0) {
	            return;
	        }

	        // Send the file in the response
	        response.setContentType("application/octet-stream"); //$NON-NLS-1$

	        response.setContentLength(download.length);
	        String fn = URLEncoder.encode(filename, "UTF-8"); //$NON-NLS-1$
	        String contentDisposition = "attachment; filename=\"" + fn + "\""; //$NON-NLS-1$ //$NON-NLS-2$
	        response.setHeader("Content-Disposition", contentDisposition); //$NON-NLS-1$
	        ServletOutputStream os = null;
	        try {
	            os = response.getOutputStream();
	            os.write(download);
	        } catch (IOException e) {
	        } finally {
	            if (os != null) {
	                os.close();
	            }
	        }
	    }
	    
	    protected byte[] getBytes(HttpServletRequest request){
			String filename = request.getParameter("name"); //$NON-NLS-1$
			String context = request.getParameter("context"); //$NON-NLS-1$
			try {
				filename = URLDecoder.decode(filename, "utf-8"); //$NON-NLS-1$
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			File file = new File(CDF.FILE_SERVER_PATH+File.separator+context+File.separator+filename);
			
			try {
				if(file.isFile()){
					return getBytes(file);
				}
			} catch (IOException e) {
			}
			return new byte[]{};
	    }
	    
		public byte[] getBytes(File file)
				throws IOException {
			
			InputStream is = new FileInputStream(file);

			// Get the size of the file
			long length = file.length();
			
			/*
			 * You cannot create an array using a long type. It needs to be an int
			 * type. Before converting to an int type, check to ensure that file is
			 * not loarger than Integer.MAX_VALUE;
			 */
			if (length > Integer.MAX_VALUE) {
				is.close();
				return null;
			}

			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while ((offset < bytes.length)
					&& ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {

				offset += numRead;

			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				is.close();
				throw new IOException("Could not completely read file ");
			}

			is.close();
			return bytes;
		}
	
}
