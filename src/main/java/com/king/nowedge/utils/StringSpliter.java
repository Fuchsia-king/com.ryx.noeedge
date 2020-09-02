package com.king.nowedge.utils;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class StringSpliter {
	
	private static int seq = 0;  
	 private static final int ROTATION = 99999;   
	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		
		
//		File file = new File("D:\\work\\ryx\\abc.txt");
//        BufferedReader reader = null;
//        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
//            reader = new BufferedReader(new FileReader(file));
//            String tempString = null;
//            int line = 1;
//            // 一次读入一行，直到读入null为文件结束
//            while ((tempString = reader.readLine()) != null) {
//                // 显示行号
//
//    		 	URL urlfile = null;
//    	        HttpURLConnection httpUrl = null;
//    	        BufferedInputStream bis = null;
//    	        BufferedOutputStream bos = null;
//    	        String  remoteFilePath= tempString;
//    	        
//    	        try
//    	        {
//    	            urlfile = new URL(remoteFilePath);
//    	            String name = urlfile.getFile();
//    	            File f = new File("c:\\"+name);
//    	            httpUrl = (HttpURLConnection)urlfile.openConnection();
//    	            httpUrl.connect();
//    	            bis = new BufferedInputStream(httpUrl.getInputStream());
//    	            bos = new BufferedOutputStream(new FileOutputStream(f));
//    	            int len = 2048;
//    	            byte[] b = new byte[len];
//    	            while ((len = bis.read(b)) != -1)
//    	            {
//    	                bos.write(b, 0, len);
//    	            }
//    	            bos.flush();
//    	            bis.close();
//    	            httpUrl.disconnect();
//    	        }
//    	        catch (Exception e)
//    	        {
//    	            e.printStackTrace();
//    	        }
//    	        finally
//    	        {
//    	            try
//    	            {
//    	            	if(null != bis){
//    	            		bis.close();
//    	            	}
//    	            	
//    	            	if(null != bis){
//    	            		bis.close();
//    	            	}
//    	            }
//    	            catch (IOException e)
//    	            {
//    	                e.printStackTrace();
//    	            }
//    	        }
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e1) {
//                	 e1.printStackTrace();
//                }
//            }
//        }
		
//		Date date = new Date(); 
//		if (seq > ROTATION) seq = 0;  
//	    date.setTime(System.currentTimeMillis());  
//	    String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++); 
//	    
//	    System.out.println(str);
//	  
		
		Double f = 1000000000000000000000000000000000000.0;
		System.out.println(String.format("%.2f", f));
		
//		
		StringSpliter stringSpliter = new StringSpliter();
		stringSpliter.SplitString("四大国有商业银行投资银行平台跨境结构融资部副总裁，资深融资租赁专家，擅长融资结构设计、跨境融资安排、租赁政策法规研究、项目风险管理、税务报表安排等领域。具有多年从业经验，精通融资租赁跨境业务结构与操作过程，是前海融资租赁俱乐部备受推崇的重量级讲师之一");
		List<String> list = stringSpliter.getList();
		for(String s:list){
			System.out.println(s);
		}
		
		//System.out.println(stringSpliter.getSplitBuffer());
		
	}
	
	static Long startMillis = 0L ;
	
	static Long endMillis = 0L ;
	
	
	private String splitSign = " ";
	
	

	
	public String getSplitSign() {
		return splitSign;
	}

	public void setSplitSign(String splitSign) {
		this.splitSign = splitSign;
	}

	/**
	 * 
	 */
	private List<String> list = new ArrayList<String>(); 
	
	private StringBuffer splitBuffer = new StringBuffer();
	
	
	public void setLib(Map<String, Object> lib){
		SplitThread.setWordsLib(lib);
	}
	
	public Map<String, Object> getLib(){
		return SplitThread.getWordsLib();
	}
	
	public List<String> getList() {
		return list;
	}


	public void setList(List<String> list) {
		this.list = list;
	}


	public StringBuffer getSplitBuffer() {
		return splitBuffer;
	}


	public void setSplitBuffer(StringBuffer splitBuffer) {
		this.splitBuffer = splitBuffer;
	}

	
	private static int SUB_LENGTH = 100;
	

	/**
	 * 标示全部线程是否完全结束
	 */
	Boolean isTotalFinished = false; 
	
	Integer finishedThreadCount = 0;
	
	public void addFinishedThreadCount(){
		
		synchronized(finishedThreadCount){
			finishedThreadCount ++ ;
			if(finishedThreadCount == splitThreadCount){
				isTotalFinished = true;
				endMillis = System.currentTimeMillis();	
				//System.out.println("set finishedThreadCount ["+ endMillis +"] split words time used ");
			}
		}		
	}
	
	/**
	 * 分词线程的数量
	 */
	int splitThreadCount = 0;
	
	/**
	 * @throws Throwable 
	 * 
	 */
	public void SplitString(String source) throws Throwable
	{
		if(null == source){
			throw new Exception ("源字符串不能为空");
		}
		
		int totalLength = source.length();
		String subStr = "";
		

		Long totalStartMillis = System.currentTimeMillis();
		
		while(source.length()>0){
			
			int startIndex = (source.length() - SUB_LENGTH >0) ?source.length() - SUB_LENGTH : 0;
			subStr = source.substring(startIndex);
			SplitThread splitThread = new SplitThread();
			splitThread.setStr(subStr);
			splitThread.setStringSpliter(this);
			splitThread.start();
			splitThreadCount ++ ;
			source = source.substring( 0 , startIndex );
			
		}		
		
		startMillis = System.currentTimeMillis();
		
		while(!isTotalFinished){
			endMillis = System.currentTimeMillis();	
		}		
		
		Long totalEndMillis = System.currentTimeMillis();
		
		endMillis = System.currentTimeMillis();
		
		//System.out.println("while finishedThreadCount time used [" + startMillis + "]["+ endMillis +"] split words time used : " + (endMillis - startMillis) + "ms");		
		
		//System.out.println("words length ["+ totalLength +"] ,whole time used [" + totalStartMillis + "]["+ totalEndMillis +"] split words time used : " + (totalEndMillis - totalStartMillis) + "ms");
		
	}
	

	/**
	 * 结果输出
	 * @param str
	 */
	public void putList(String str){
		
		if(StringUtils.isNotEmpty(str)){	
			
			if(!list.contains(str)){
				synchronized(list){
					list.add(str);
				};
				
				synchronized(splitBuffer){
					splitBuffer = splitBuffer.insert(0, str).insert(0 , splitSign);
				}
			}
			
		}
	}		

}


class SplitThread extends Thread {
	
	static String WORD_LIB = "words.properties";
	
	public static Map<String,Object> WORDS_LIB_MAP = new HashMap<String,Object>();

	static Long startMillis = 0L ;
	
	static Long endMillis = 0L;
	
	

	public SplitThread() throws Throwable{
		synchronized(this){
			init();
		}
	}
	
	public static void setWordsLib(Map<String,Object> lib){
		WORDS_LIB_MAP = lib;
	}
	
	public static Map<String,Object> getWordsLib(){
		return WORDS_LIB_MAP;
	}
	
	
	private void init() throws Throwable {
		
		startMillis = System.currentTimeMillis();
		
		if(null == WORDS_LIB_MAP || WORDS_LIB_MAP.size() == 0){
			try{
				
				url = StringSpliter.class.getClassLoader().getResource(WORD_LIB);
				if(null == url){
					throw new Throwable("can not find words library url : [ " + url.getPath() + " ]");
				}
				URLConnection uc = url.openConnection(); 
				if(null == uc){
					throw new Throwable("can not open words library url : [ " + url.getPath() + " ]");
				}
		        InputStream is = uc.getInputStream(); 
				if(null == is){
					throw new Throwable("can not find words library : [ " + WORD_LIB + " ]");
				}
				else{
					BufferedReader reader = null;
					
			        try {
			            reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			            String tempString = null;
			            while ((tempString = reader.readLine()) != null) {
			            	WORDS_LIB_MAP.put(tempString, null);
			            }
			            reader.close();
			        } catch (IOException e) {
						//logger.error(e.getMessage(),e);
			        	throw e;
			        } finally {
			            if (reader != null) {
			                try {
			                    reader.close();
			                } catch (IOException e1) {
			                	
			                }
			            }			            
			        }
				}
			}
			catch(Throwable e){
				throw e;
				//logger.error(e.getMessage(),e);
			}
		}
		endMillis = System.currentTimeMillis();
		
		//System.out.println("initailize words lib time used : " + (endMillis - startMillis) + "ms");
		
	}

	private int threadId;

		
	
    public SplitThread(String threadName) {
        super(threadName);
    }
    
    private String str;
    
    private Boolean isFinished = false;
    
    private StringSpliter stringSpliter;
    
    
    
 


	public int getThreadId() {
		return threadId;
	}


	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}


	

	public StringSpliter getStringSpliter() {
		return stringSpliter;
	}


	public void setStringSpliter(StringSpliter stringSpliter) {
		this.stringSpliter = stringSpliter;
	}


	public String getStr() {
		return str;
	}


	public void setStr(String str) {
		this.str = str;
	}

	

	
	public Boolean getIsFinished() {
		return isFinished;
	}


	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}


	public void run() {
		Split(str);
    }
	
	
	/**
	 * 
	 * @param ch
	 * @return
	 */
	Boolean isChineseChar(char ch){
		Boolean isChineseChar = false;
		byte[] bytes = ("" + ch).getBytes(); 
		if(bytes.length==2){ 
	          int[] ints=new int[2]; 
	          ints[0]=bytes[0]& 0xff; 
	          ints[1]=bytes[1]& 0xff; 
	          if(ints[0]>=0x81 && ints[0]<=0xFE && ints[1]>=0x40 && ints[1]<=0xFE){ 
	        	  isChineseChar=true; 
	          }	                       
	    }  
		return isChineseChar;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	Boolean isNumberOrLetter(String str){
		String regx = "[\\da-zA-Z]+";					
		Pattern pattern = Pattern.compile(regx);
		return pattern.matcher(str).matches();
	}
	
	private void putList(String str){		
		splitCount ++ ;
		stringSpliter.putList(str);		
	}
	
	int splitCount = 0 ;

	private URL url;
	
	
	
	
	public int getSplitCount() {
		return splitCount;
	}


	public void setSplitCount(int splitCount) {
		this.splitCount = splitCount;
	}


	/***
	 * 
	 * @param str
	 */
	private void Split(String str)
	{
		startMillis = System.currentTimeMillis(); 

		////System.out.println(" start time : " + startMillis);
		
		try{
			
			Boolean bMatch = false;
			
			Integer length = str.length();

			String temp = "";
			
			for(int i = 0 ; i <= length ;){
				
				bMatch = false;
							
				String subString = str.substring(i,length);
				if(subString.length()>0 && WORDS_LIB_MAP.containsKey(subString)){
					if(temp.length()>0){
						putList(temp.toString());
						temp = "";	
					}
					bMatch = true;
					putList(subString);
					i = 0 ;
					length = length - subString.length();
				}
				else if(length - i ==1 || length ==1){
					
					bMatch = true;
					i = 0;
					length = length - 1;
					
					char charAt = str.charAt(length);
					String charAtString = String.valueOf(charAt);				
					
					if(isNumberOrLetter(String.valueOf(charAt))){
						temp = charAtString + temp;
					}
					else {
						if(temp.length()>0){
							putList(temp.toString());
							temp = "";	
						}
					}
				}
				if(!bMatch){
					i++;
				}
			}
			if(temp.length()>0){
				putList(temp.toString());
				temp = "";
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}	
		
		endMillis = System.currentTimeMillis();	
		
		//System.out.println("sub length  "+ str.length() +" [" + startMillis + "]["+ endMillis +"] split words time used : " + (endMillis - startMillis) + "ms");
		
		stringSpliter.addFinishedThreadCount();	
		
	}
	
}