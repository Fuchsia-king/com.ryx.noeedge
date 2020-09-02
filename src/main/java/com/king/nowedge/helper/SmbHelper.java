package com.king.nowedge.helper;

import com.king.nowedge.dto.base.ResultDTO;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.UnknownHostException;

public class SmbHelper {
	
	private static final Log logger = LogFactory.getLog(SmbHelper.class);

	public static String DEFAULT_URL = "smb://administrator:Bigbird8123@120.25.64.188/teacher/";
	public static String BASE_URL = "smb://administrator:Bigbird8123@120.25.64.188/";
	
	private SmbFile smbFile = null;
	private SmbFileOutputStream smbOut = null;
	private static SmbHelper smbHelper = null; // 共享文件协议

	public static synchronized SmbHelper getInstance(String DEFAULT_URL) throws java.net.UnknownHostException {
		if (smbHelper == null)
			return new SmbHelper(DEFAULT_URL);
		return smbHelper;
	}
	
	public static synchronized SmbHelper getInstance(String baseUrl,String path) throws java.net.UnknownHostException {
		if (smbHelper == null)
			return new SmbHelper(baseUrl,path);
		return smbHelper;
	}

	/**
	 * @param DEFAULT_URL服务器路径
	 * @throws java.net.UnknownHostException
	 */
	private SmbHelper(String DEFAULT_URL) throws java.net.UnknownHostException {
		this.DEFAULT_URL = DEFAULT_URL;
		//this.init();
	}
	
	private SmbHelper(String baseUrl,String path) throws java.net.UnknownHostException {
		this.DEFAULT_URL = baseUrl + path;
		//this.init();
	}
	
	

	public void init() throws java.net.UnknownHostException {

		try {
			System.out.println("开始连接...DEFAULT_URL：" + this.DEFAULT_URL);
			smbFile = new SmbFile(this.DEFAULT_URL);
			smbFile.connect();
			System.out.println("连接成功...DEFAULT_URL：" + this.DEFAULT_URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.print(e);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print(e);
		}
	}

	// 4. 上传文件的方法

	/**
	 * 上传文件到服务器
	 */
	public ResultDTO<Boolean> uploadFile(File file) {
		ResultDTO<Boolean> result = null;
		BufferedInputStream bf = null;
		try {
			this.smbOut = new SmbFileOutputStream(this.DEFAULT_URL + "/" + file.getName(), false);
			bf = new BufferedInputStream(new FileInputStream(file));
			byte[] bt = new byte[8192];
			int n = bf.read(bt);
			while (n != -1) {
				this.smbOut.write(bt, 0, n);
				this.smbOut.flush();
				n = bf.read(bt);
			}
			result = new ResultDTO<Boolean>(true);
			System.out.println("文件传输结束...");
		} catch (SmbException e) {
			logger.error(e.getMessage(),e);
			 e.printStackTrace();
			 System.out.println(e);
			result = new ResultDTO<Boolean>("error", e.getMessage());
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(),e);
			 e.printStackTrace();
			 System.out.println(e);
			result = new ResultDTO<Boolean>("error", e.getMessage());
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(),e);
			 e.printStackTrace();
			 System.out.println("找不到主机...DEFAULT_URL：" + this.DEFAULT_URL);
			result = new ResultDTO<Boolean>("error", e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			 e.printStackTrace();
			 System.out.println(e);
			result = new ResultDTO<Boolean>("error", e.getMessage());
		} finally {
			try {
				if (null != this.smbOut)
					this.smbOut.close();
				if (null != bf)
					bf.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}

	public ResultDTO<Boolean> uploadFileBytes(String url, String saveFileName, byte[] bytes) {
		return upload(url + saveFileName, bytes);
	}
	
	
	public ResultDTO<Boolean> uploadFileBytes(String saveFileName, byte[] bytes) {
		return upload(DEFAULT_URL + saveFileName, bytes);
	}
	
	
	
	
	public ResultDTO<Boolean> upload(String url, byte[] bytes) {

		ResultDTO<Boolean> result = null;
		try {

			SmbFile smbFileOut = new SmbFile(url);
			if (!smbFileOut.exists())
				smbFileOut.createNewFile();
			
			SmbFileOutputStream out = new SmbFileOutputStream(smbFileOut);
			out.write(bytes);
			out.close();
			//smbFileOut.delete();			
			result = new ResultDTO<Boolean>(true);
			
		}catch (SmbAuthException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}catch(SmbException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch (IOException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch (Throwable e) { 
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		
		return result;
		
	}
	
	
	

	// 5. 在main方法里面测试

	public static void main(String[] args) throws java.net.UnknownHostException {
		// 服務器地址 格式為 smbHelper://电脑用户名:电脑密码@电脑IP地址/IP共享的文件夹
		String localFile = "F:/开关生产销售企业名录.xls"; // 本地要上传的文件
		File file = new File(localFile);
		SmbHelper smbHelper = SmbHelper.getInstance(DEFAULT_URL);
		smbHelper.uploadFile(file);// 上传文件
	}

}
