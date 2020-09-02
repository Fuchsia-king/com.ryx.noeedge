package com.king.nowedge.wxpay;

import com.king.nowedge.dto.base.ResultDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GetWxOrderno {
	
	
	private static final Log logger = LogFactory.getLog(GetWxOrderno.class);
	
	public static DefaultHttpClient httpclient;

	static {
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient);
	}

	/**
	 * description:获取预支付id
	 * 
	 * @param url
	 * @param xmlParam
	 * @return
	 * @author ex_yangxiaoyi
	 * @see
	 */
	public static String getPayNo(String url, String xmlParam) {
		//DefaultHttpClient client = new DefaultHttpClient();
		//client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		String prepay_id = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			if (jsonStr.indexOf("FAIL") != -1) {
				return prepay_id;
			}
			Map map = doXMLParse(jsonStr);
			prepay_id = (String) map.get("prepay_id");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			httpost.releaseConnection();
		}
		return prepay_id;
	}

	/**
	 * description:获取扫码支付连接
	 * 
	 * @param url
	 * @param xmlParam
	 * @return
	 * @author ex_yangxiaoyi
	 * @see
	 */

	public static String post(String url, String xmlParam) {

		String ret = "";
		//DefaultHttpClient client = new DefaultHttpClient();
		//client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		HttpResponse response;
		try {
			response = httpclient.execute(httpost);
			ret = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ClientProtocolException e) {
			ret = e.getMessage();
		} catch (IOException e) {
			ret = e.getMessage();
		}
		finally{
			httpost.releaseConnection();
		}
		return ret;

	}

	public static ResultDTO<WeixinPrepayResult> payByWeixin(String url, String xmlParam) {

		WeixinPrepayResult weixinPrepayResult = new WeixinPrepayResult();
		ResultDTO<WeixinPrepayResult> result = null;
		//DefaultHttpClient client = new DefaultHttpClient();
		//client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		String code_url = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");

			logger.error("payByWeixinResponse->" + jsonStr) ;
			
			Map map = doXMLParse(jsonStr);
			
			String returnCode = (String) map.get("return_code");
			String returnMsg = (String) map.get("return_msg");
			
			weixinPrepayResult.setReturnCode(returnCode);
			weixinPrepayResult.setReturnMsg(returnMsg);

			
				
			if(map.containsKey("result_code")){
				String resultCode = (String) map.get("result_code");
				weixinPrepayResult.setResultCode(resultCode);
			}
			
			if(map.containsKey("err_code")){
				String errCode = (String) map.get("err_code");
				weixinPrepayResult.setErrCode(errCode);
			}
			
			if(map.containsKey("err_code_des")){
				String errCodeDes = (String) map.get("err_code_des");
				weixinPrepayResult.setErrCodeDes(errCodeDes);
			}
			/**
			 * <xml><return_code><![CDATA[SUCCESS]]></return_code>
			 * <return_msg><![CDATA[OK]]></return_msg>
			 * <appid><![CDATA[wx24c114aa01b8c042]]></appid>
			 * <mch_id><![CDATA[1316852201]]></mch_id>
			 * <nonce_str><![CDATA[GuRKr8LY0PNoKzhi]]></nonce_str>
			 * <sign><![CDATA[EBAF36EDE7FC7CFB3F80A6CE09003A3D]]></sign>
			 * <result_code><![CDATA[SUCCESS]]></result_code>
			 * <prepay_id><![CDATA
			 * [wx2016053010231009943b5ba40374345638]]></prepay_id>
			 * <trade_type><![CDATA[NATIVE]]></trade_type>
			 * <code_url><![CDATA
			 * [weixin://wxpay/bizpayurl?pr=PVJK268]]></code_url> </xml>
			 */
			
			if(map.containsKey("code_url")){
				String codeUrl = (String) map.get("code_url");
				weixinPrepayResult.setCodeUrl(codeUrl);
			}

			if(map.containsKey("prepay_id")){
				String prepayId = (String) map.get("prepay_id");			
				weixinPrepayResult.setPrepayId(prepayId);
			}
			
			
			result = new ResultDTO<WeixinPrepayResult>(weixinPrepayResult);
			
			
		} catch (Exception e) {
			result = new ResultDTO<WeixinPrepayResult>("error", e.getMessage());
		}
		finally{
			httpost.releaseConnection();
		}
		return result;
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

}