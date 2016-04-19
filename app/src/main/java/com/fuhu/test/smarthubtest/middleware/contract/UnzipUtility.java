package com.fuhu.test.smarthubtest.middleware.contract;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UnzipUtility {
	private static final String TAG = "UnzipUtility";
	private final static int BUFFER_SIZE = 4096;
	private final static String ENCRYPTION = "UTF-8";
	
	public static String getJsonStringFromGZIP(InputStream is) {
		long startTime = System.currentTimeMillis();
		
		String jsonString = null;
		try {
//			InputStream is = response.getEntity().getContent();
//			BufferedInputStream bis = new BufferedInputStream(is);
//			bis.mark(2);
//			byte[] header = new byte[2];
//			int result = bis.read(header);
//			bis.reset();
//			int contentType = getHeaderData(header);
//			// The content with zip
//			if (result != -1 && contentType == 0x1f8b) {
//				is = new GZIPInputStream(bis);
//			}
//			// The content without zip
//			else {
//				is = bis;
//			}
			jsonString = parseInputStreamToString(is);
			
			is.close();
//			bis.close();
//			Log.d("ZIP Task", "Get Json String From GZIP output : " + jsonString );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}

	private static int getHeaderData(byte[] data) {
		return (int)((data[0]<<8) | data[1]&0xFF);
	}
	
	public static String parseInputStreamToString(InputStream in) {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		try {
			while((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
			
			data = null;
			return new String(outStream.toByteArray(), ENCRYPTION);
		} catch (IOException e) {
//			e.printStackTrace();
		}
		
		return null;
	}
}