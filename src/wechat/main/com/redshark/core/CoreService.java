package com.redshark.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.redshark.utils.Article;
import com.redshark.utils.HttpClient;
import com.redshark.utils.HttpURLConnectionUtil;
import com.redshark.utils.MD5Util;
import com.redshark.utils.MessageUtil;
import com.redshark.utils.NewsMessage;
import com.redshark.utils.StringUtil;
import com.redshark.utils.TextMessage;

/**
 * @Description:核心服务类
 * @author ximing.fu
 * @time:2015年11月5日 下午2:36:49
 *
 */
public class CoreService {

	/**
	 * 项目地址
	 */
	public static String PROJECT_PATH = "";
	/**
	 * 微信公众号用户名
	 */
	public static String WECHAT_USERNAME = "jianyue.yan@chinacreator.com";
	/**
	 * 微信公众号密码
	 */
	public static String WECHAT_PASSWORD = "abc635241";

	/**
	 * 微信公众号TOKEN标识位：与接口配置信息中的 token 要一致，这里赋予什么值，在接口配置信息中的Token就要填写什么值
	 */
	public static String TOKEN = "redshark2016";

	/**
	 * 微信公众号原始ID
	 */
	public static String WECHAT_TOUSERNAME_ID = "gh_c8133ca7cdff";

	/**
	 * 微信公众号原始ID
	 */
	public static String APPID = "wx3c8e6eb4d2a09075";

	/**
	 * 微信公众号原始ID
	 */
	public static String APPSECRET = "98bc2e83b1d7eafd0f52b10d4b29b69b";
	/**
	 * 获取access接口
	 */
	public static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	/**
	 * 上传图片接口
	 */
	public static String UPLOAD_PIC_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	/**
	 * 创建菜单接口
	 */
	public static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	/**
	 * 删除菜单接口
	 */
	public static String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/**
	 * 群发接口
	 */
	public static String GROUP_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	/**
	 * 群发预览接口
	 */
	public static String GROUP_PREVIEW_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
	/**
	 * 群发接口参数
	 */
	public static String GROUP_SEND_URL_PARAM = "{\"filter\":{\"is_to_all\":true\"group_id\":\"group_id_value\"},\"mpnews\":{\"media_id\":\"media_id_value\"},\"msgtype\":\"mpnews\"}";
	/**
	 * 图文接口
	 */
	public static String UPLOAD_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	/**
	 * 新增临时素材
	 */
	public static String UPLOAD_TEMPMEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=MEDIATYPE";
	
	

	/**
	 * 处理微信发来的请求：这里统一回复图文消息
	 *
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml 请求解析：调用消息工具类MessageUtil 解析微信发来的xml 格式的消息，解析的结果放在HashMap 里
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息内容
			String msgcontent = requestMap.get("Content");
			// 相对路径
			String path = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()
					+ "/";
			System.out.println("======================" + path);

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 回复图文消息
			List<Article> articleList = new ArrayList<Article>();
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			String jszx_url = "http://wcwzw.wangcheng.gov.cn/wx/instantMessaging_zxorglist.do";
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)
					&& !StringUtil.isBlank(msgcontent)) {
				articleList = buildArticleList("znjqr", msgcontent, path);
				if(articleList.size()>0){
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml 字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
					return respMessage;
				}else {
					//  &lta href='"+jszx_url+"'&gt“即时咨询”&lt/a&gt
					textMessage.setContent("很抱歉，没有找到“"+msgcontent+"”的相关信息，请点击<a href=\""+jszx_url+"\">“即时咨询”</a>，望城区政务服务中心各单位专业客服将竭诚为您服务。");
					
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;
				}
				
				/*respContent ="暂时没有"+msgcontent+"的相关信息！";
				if(articleList.size()>0){
					respContent ="";
					for(int i=0;i<articleList.size();i++){
						Article rpBean=articleList.get(i);
						respContent+=" <a href=\""+rpBean.getUrl()+"\">"+rpBean.getTitle()+"</a> \n";
						respContent+=" 窗口位置:"+rpBean.getPicUrl()+"  "+DateUtil.getDateTimeString()+"\n";
						respContent+="\n";
					}
				}
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);*/
			} // 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					/*respContent = "谢谢您的关注！";
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;*/
					
					Article article = new Article();
					article.setTitle("您好！欢迎关注望城区微政务公众服务号。");
					article.setDescription(null);
					article.setPicUrl(path + "res/wechat/images/dt2.jpg");
					article.setUrl("http://wangcheng.720eye.cn");
					articleList.add(article);
					
					article = new Article();
					article.setTitle("望城政务服务中心介绍");
					article.setDescription(null);
					article.setPicUrl(path + "res/wechat/images/gh.png");
					article.setUrl(path + "wechatContent_introduction.do");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml 字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
					return respMessage;
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
					System.out.println("自定义菜单事件...");
					String eventKey = requestMap.get("EventKey");

					if ("WECHAT_MALL".equals(eventKey)) {  //望城宣传
						articleList = buildArticleList("WECHAT_MALL", msgcontent, path);
						respContent = "今日暂时没有\"商城\"的相关信息！";
					}else if ("WCXW".equals(eventKey)) {
						articleList = buildArticleList("WCXW", msgcontent, path);
						respContent = "今日暂时没有\"政务资讯\"的相关信息！";
					}else if ("TZGG".equals(eventKey)) {
						articleList = buildArticleList("TZGG", msgcontent, path);
						respContent = "今日暂时没有\"通知公告\"的相关信息！";
					}else if("ZNDB".equals(eventKey)){
						respContent = "很抱歉，智能导办还在开发当中...";
						articleList = buildArticleList("ZNDB", msgcontent, path);
					}else if("znjqr".equals(eventKey)){
						Article article = new Article();
						article.setTitle("智能问答");
						article.setDescription("您好！请在微信对话框输入您要查询或办理业务的关键字，客服“小微”将在第一时间进行回复。如不能查到您需要的信息，请在工作日上班时间点击“即时咨询”，望城区政务服务中心各单位专业客服将竭诚为您服务。");
						article.setPicUrl(path + "res/wechat/images/xiaowei.png");
						//article.setUrl(null); 
						articleList.add(article);
					}
					// 判断list为null的情况;直接返回文本信息
					if (articleList == null || articleList.size() == 0) {
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
						return respMessage;
					}
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml 字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
					System.out.println("respMessage============================"+respMessage);
					return respMessage;
				}
			}else {
				// 其他类型的消息均回复“很抱歉，没有与您提问的关键字""相关的信息”
				respContent = "很抱歉，没有找到的相关信息。";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				return respMessage;
			}
			return respMessage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;

	}
	
	
	/**
	 * 封装List<Article>
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public static List<Article> buildArticleList(String flag,String msgcontent,String path) throws Exception{
		List<Article> articleList = new ArrayList<Article>();
		/*// 获取信息
		BackwechatContentService backwechatService = (BackwechatContentService) SpringContextUtil.getBean("backwechatContentService");
		BackwechatContentVo backwechatVo = new BackwechatContentVo();
		List<BackwechatContentVo> bList = new ArrayList<BackwechatContentVo>();
		if ("ZNDB".equals(flag)) {
			// 借用字段传递条件
			Article article = new Article();
			article.setTitle("智能导办");
			article.setDescription("智能导办是望城区微信公众平台为您提供的政务信息咨询服务功能，以全新的方式为您提供更加贴心的便捷服务。");
			article.setPicUrl(path + "uplodefile/wechatuploadfile/zndb.jpg");
			article.setUrl(null);
			articleList.add(article);
		}else if ("SY".equals(flag)) {
			
		}else if ("znjqr".equals(flag)) {   //文本消息回复响应   回复事项机构查询信息
			// 借用字段传递条件
			backwechatVo.setTitle(msgcontent);
			bList = backwechatService.getBaseOrgInfoList(backwechatVo);
		}else if ("WCXC".equals(flag)) {
			// 最多取10条
			backwechatVo.setType("1");
			backwechatVo.setLastupdatetimeString("5");
			bList = backwechatService.selectAllWxjzList(backwechatVo);
		}else if ("TZGG".equals(flag)) {
			// 最多取10条
			backwechatVo.setType("5");
			backwechatVo.setLastupdatetimeString("5");
			bList = backwechatService.selectAllWxjzList(backwechatVo);
		}else if ("WCXW".equals(flag)) {
			backwechatVo.setType("17");
			backwechatVo.setLastupdatetimeString("5");
			bList = backwechatService.selectAllWxjzList(backwechatVo);
		}
		if (bList != null && bList.size() > 0) {
			for (int i = 0; i < bList.size(); i++) {
				
				if (articleList.size()>8) {
					break;
				}
				
				BackwechatContentVo temp = bList.get(i);
				Article article = new Article();
				
				if (i!=0 && ("WCXW".equals(flag)||"TZGG".equals(flag)) && !StringUtil.isBlank(temp.getPublishtimeStr())) {
					article.setTitle(temp.getTitle()+"\n  "+temp.getPublishtimeStr());
				}else {
					article.setTitle(temp.getTitle());
				}
				article.setDescription(temp.getIntroduction());
				
				// 图片处理
				String picurl = temp.getPicurl();
				if (!StringUtil.isBlank(picurl)) {
					if (picurl.startsWith("http")) {
					}else {
						if (picurl.indexOf("/wx/") >-1 ) {
							picurl = path + picurl.split("/wx/")[1];
						}else {
							picurl = path + picurl;
						}
					}
					
					// 第一个是事项的话，设置默认大图吧
					if ("TZGG".equals(flag)){// 
						article.setPicUrl(null);
					}else if (i==0 && "S".equals(temp.getType())) {
						article.setPicUrl(path + "res/wechat/images/dt1.jpg");
					}else {
						article.setPicUrl(picurl);
					}
					if (i==0) {
						// 通知公告和事项用大图
						if ("S".equals(temp.getType()) || "TZGG".equals(flag)) {
							article.setPicUrl(path + "res/wechat/images/dt1.jpg");
						}else {
							article.setPicUrl(picurl);
						}
					}else if ("TZGG".equals(flag)){
						article.setPicUrl(path + "res/wechat/images/laba.jpg");
					}else{
						article.setPicUrl(picurl);
					}
				}else{
					if (i==0) {
						article.setPicUrl(path + "res/wechat/images/dt1.jpg");
					}else if ("TZGG".equals(flag)){
						article.setPicUrl(path + "res/wechat/images/laba.jpg");
					}else{
						article.setPicUrl(path + "res/wechat/images/gh.png");
					}
				}
				// url处理
				System.out.println("temp.getType()======================"+temp.getType());
				if ("S".equals(temp.getType())) {
					article.setUrl(path + "wechatBuss_workDetail.do?ecId=" + temp.getId());
				}else{
					article.setUrl(path + "wechatContent_detail.do?id=" + temp.getId());
				}
				articleList.add(article);
			}
			
			// 查看更多
			if ("WCXC".equals(flag)) {
				Article article = new Article();
				article.setTitle("查看更多望城宣传");
				article.setUrl(path + "wechatContent_publicity.do");
				articleList.add(article);
			}else if ("WCXW".equals(flag)) {
				Article article = new Article();
				article.setTitle("查看更多政务资讯");
				article.setUrl(path + "wechatContent_zwmessage.do?menutypes=1");
				articleList.add(article);
			}else if ("TZGG".equals(flag)) {
				Article article = new Article();
				article.setTitle("查看更多通知公告");
				article.setUrl(path + "wechatContent_publicmessage.do?menutypes=1");
				articleList.add(article);
			}
		}*/
		return articleList;
	}
	
	/**
	 * 处理list数据
	 * 	1.优先取出ispub标识符为1的
	 * 	2.如果1不够，再顺序取满3条
	 * @param zxList
	 * @return
	 * @throws Exception
	 *//*
	public static List<BackwechatContentVo> exeList(List<BackwechatContentVo> zxList) throws Exception{
		List<BackwechatContentVo> tempList = new ArrayList<BackwechatContentVo>();
		if (zxList != null && zxList.size()>0) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < zxList.size(); i++) {
				BackwechatContentVo temp = zxList.get(i);
				if ("1".equals(temp.getIspub())) {
					tempList.add(temp);
					idList.add(temp.getId());
				}
			}
			for (int i = 0; i < zxList.size(); i++) {
				BackwechatContentVo temp = zxList.get(i);
				if (tempList.size() <= 3 && !idList.contains(temp.getId())) {
					tempList.add(temp);
				}else {
					break;
				}
			}
		}
		return tempList;
	}*/
	

	/**
	 * 
	 1 上传图文消息内的图片获取URL【订阅号与服务号认证后均可用】 2 上传图文消息素材【订阅号与服务号认证后均可用】 3
	 * 根据分组进行群发【订阅号与服务号认证后均可用】 4 根据OpenID列表群发【订阅号不可用，服务号认证后可用】 5
	 * 删除群发【订阅号与服务号认证后均可用】 6 预览接口【订阅号与服务号认证后均可用】 7 查询群发消息发送状态【订阅号与服务号认证后均可用】 8
	 * 事件推送群发结果
	 * 主动推送上周的咨询和公告
	 * 
	 * @throws Exception
	 */
	public static void groupSending() throws Exception {
		/*
		
		com.chinacreator.wechat.service.BackwechatContentService backwechatContentServiceImpl = (com.chinacreator.wechat.service.BackwechatContentService) SpringContextUtil.getBean("wechatContentService");
		BackwechatContentService backwechatService = (BackwechatContentService) SpringContextUtil.getBean("backwechatContentService");
		BackwechatContentVo backwechatVo = new BackwechatContentVo();
		List<BackwechatContentVo> hzList = new ArrayList<BackwechatContentVo>();
		
		// 获取上周的资讯和公告
		backwechatVo.setType("17");
		backwechatVo.setIspub("1");
		List<BackwechatContentVo> zxList = backwechatService.selectAllWxjzList(backwechatVo);
		zxList = exeList(zxList);
		
		backwechatVo.setType("5");
		backwechatVo.setIspub("1");
		List<BackwechatContentVo> ggList = backwechatService.selectAllWxjzList(backwechatVo);
		ggList = exeList(ggList);
		
		hzList.addAll(zxList);
		hzList.addAll(ggList);
		
		if (hzList.size()>0) {
			
			String access_token = getAccess_token();
//			String url = UPLOAD_PIC_URL.replace("ACCESS_TOKEN",access_token);
			String upload_tempmedia_url_ = UPLOAD_TEMPMEDIA_URL.replace("ACCESS_TOKEN",access_token).replace("MEDIATYPE", "image");
			String url = upload_tempmedia_url_;
			
			String path =CoreService.class.getResource("/").getPath();
			String httpImgPath = path.split("WEB-INF")[0]+"res/wechat/httpImages/";
			String ewebeditorPath = path.split("WEB-INF")[0]+"ewebeditor/uploadfile/";
			path = path.split("WEB-INF")[0]+"res/wechat/images/";
			//File file = new File(path,"more02.png");
			
			// 先把已经定好的图片传上去
			File file = new File(path,"dt1.jpg");
			HttpURLConnectionUtil httpURLConnectionUtil = new HttpURLConnectionUtil(url, null,file.getPath());
			String dt_pic_re_url = httpURLConnectionUtil.executeForFile();
			
			file = new File(path,"laba.jpg");
			httpURLConnectionUtil = new HttpURLConnectionUtil(url, null,file.getPath());
			String laba_pic_re_url = httpURLConnectionUtil.executeForFile();
			
			file = new File(path,"gh.png");
			httpURLConnectionUtil = new HttpURLConnectionUtil(url, null,file.getPath());
			String gh_pic_re_url = httpURLConnectionUtil.executeForFile();
			
			List<String> httpfileList = new ArrayList<String>();
			List<Map> list = new ArrayList<Map>();
			for (int i = 0; i < hzList.size(); i++) {
				BackwechatContentVo temp = hzList.get(i);
				// 构造数据
				Map map = new HashMap();
				String picurl = temp.getPicurl();
				// 第一条如果没有图片的话，就设置为空
				if (StringUtil.isBlank(picurl)) {
					if (i == 0) {
						map.put("thumb_media_id", dt_pic_re_url);
					} else {
						if ("17".equals(temp.getType())) {
							map.put("thumb_media_id", gh_pic_re_url);
						}else if ("5".equals(temp.getType())) {
							map.put("thumb_media_id", laba_pic_re_url);
						}else{
							map.put("thumb_media_id", gh_pic_re_url);
						}
					}
				} else {
					String picname = "."+picurl.substring(picurl.lastIndexOf(".") + 1);
					// 图片仅支持jpg/png格式，大小必须在1MB以下。
					boolean picFlag = false;
					if(picname.endsWith(".jpg") || picname.endsWith(".png")){
						if (picurl.startsWith("http")) {
							// 网路图片处理 - 将图片下载到本地处理
							picurl = StringUtil.downLoadImageFroUrl(picurl, httpImgPath);
							httpfileList.add(picurl);
							picFlag = true;
						}else {
							// 本地图片处理==/wx/ewebeditor/uploadfile/20160621121023261001.jpg  /creatorepp/ewebeditor/uploadfile/20160822092227251.jpg
							if (picurl.indexOf("/ewebeditor/") >-1 ) {
								picurl = ewebeditorPath + picurl.split("/uploadfile/")[1];
								picFlag = true;
							}
						}
					}
					// 字节长度，1M=1024k=1048576字节
					if (!picFlag || new File(picurl).length() > 1048576) {
						if ("17".equals(temp.getType())) {
							map.put("thumb_media_id", gh_pic_re_url);
						}else if ("5".equals(temp.getType())) {
							map.put("thumb_media_id", laba_pic_re_url);
						}else{
							map.put("thumb_media_id", gh_pic_re_url);
						}
					}else {
						file = new File(picurl);
						httpURLConnectionUtil = new HttpURLConnectionUtil(url, null,file.getPath());
						String temp_pic_re_url = httpURLConnectionUtil.executeForFile();
						map.put("thumb_media_id", temp_pic_re_url);
					}
				}
				//map.put("thumb_media_id", id);
				//map.put("author", TOKEN);
				map.put("title", temp.getTitle());
				// 获取内容信息
				com.chinacreator.wechat.vo.BackwechatContentVo backwechatContentVotemp = new com.chinacreator.wechat.vo.BackwechatContentVo();
				backwechatContentServiceImpl.getContentById(temp.getId(), backwechatContentVotemp);
				map.put("content", StringUtil.getTextByhtml(backwechatContentVotemp.getContent()));
				//map.put("digest", temp.getTitle()+"......");
				map.put("show_cover_pic", "0");
				map.put("content_source_url", PROJECT_PATH + "wechatContent_detail.do?id=" + temp.getId());
				list.add(map);
			}
			
			Map map3 = new HashMap();
			map3.put("articles", list);
			
			Gson gson = new Gson();
			String articles_text = gson.toJson(map3);// 转换成json数据格式
			System.out.println(articles_text);
			
			// 上传图文
			String uploadnewsurl = UPLOAD_NEWS_URL.replace("ACCESS_TOKEN",access_token);
			httpURLConnectionUtil = new HttpURLConnectionUtil(uploadnewsurl, articles_text);
			// {"type":"news","media_id":"GM3HsLVdZa88beNacj0g2PR2sYcSkVhAuEOkxj78sCbArWXIlTPXuktrk2DWirVD","created_at":1447466168}
			String uploadnews_text = httpURLConnectionUtil.execute();
			System.out.println(uploadnews_text);
			JSONObject jsonObj = JSONObject.fromObject(uploadnews_text);
			String media_id = jsonObj.getString("media_id");
			
			// 群发消息
			String group_send_url_ = GROUP_SEND_URL.replace("ACCESS_TOKEN",access_token);
			// 预览消息
			String group_preview_url = GROUP_PREVIEW_URL.replace("ACCESS_TOKEN",access_token);
			
			//String group_send_url_param_ = GROUP_SEND_URL_PARAM.replace("group_id_value", "").replace("media_id_value", media_id);
			
			// 群发
			 JsonObject jObj = new JsonObject();
			 JsonObject filter = new JsonObject();
			 filter.addProperty("is_to_all", true);
			 filter.addProperty("group_id", "");
			 jObj.add("filter", filter);
			
			// 预览
			 JsonObject jObj = new JsonObject();
			 jObj.addProperty("towxname", "zjledming"); 
			 group_send_url_=group_preview_url;
			 
			 JsonObject mpnews = new JsonObject();
			 mpnews.addProperty("media_id", media_id);
			 jObj.add("mpnews", mpnews);
			 
			 jObj.addProperty("msgtype", "mpnews"); 
			 String group_send_url_param_ = jObj.toString();
			
			httpURLConnectionUtil = new HttpURLConnectionUtil(group_send_url_, group_send_url_param_);
			String group_send_url_returntext = httpURLConnectionUtil.execute();
			System.out.println("【==============每日主动推送开始==============】");
			System.out.println("【推送时间：】"+DateUtil.getDateString());
			System.out.println("【推送url：】"+group_send_url_);
			System.out.println("【推送参数：】"+group_send_url_param_);
			System.out.println("【反馈结果：】"+group_send_url_returntext);
			System.out.println("【==============每日主动推送结束==============】");
			
		}
		 */
	}

	/**
	 * 获取Access_token
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getAccess_token() throws Exception {
		String url = GET_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET",
				APPSECRET);
		HttpClient client = new HttpClient(url);
		String re = client.get();
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		JsonObject json = jsonparer.parse(re).getAsJsonObject();
		return json.get("access_token").getAsString();
	}

	/**
	 * 创建自定义菜单
	 * @return
	 * @throws Exception
	 */
	public static String createMenu(String path) throws Exception {
		// 先删除菜单
		deleteMenu();
		
		File file = new File(CoreService.class.getResource("/menu.json").getPath());
	    String content = FileUtils.readFileToString(file, "utf-8");
	    System.out.println("======================================"+content);
	    if (!StringUtil.isBlank(content)) {
	    	// 暂时写死
	    	//path = "http://fximing.nat123.net/creatorepp";
	    	content = content.replaceAll("http://path",path);
		}
	    System.out.println("===========菜单内容 ↓===========");
	    System.out.println(content);
	    System.out.println("===========菜单内容↑===========");
	    

		String access_token = getAccess_token();
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", access_token);
		/*HttpClient client = new HttpClient(url);
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("param1", new StringBody(sb.toString(), Charset.forName("UTF-8")));*/
		
		HttpURLConnectionUtil httpURLConnectionUtil = new HttpURLConnectionUtil(url, content);
		String re = httpURLConnectionUtil.execute();
		return re;
	}
	
	/**
	 * 获取api_ticket
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static String getJsapi_tiket(String token) throws Exception {
		String getTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + token;
		HttpClient client = new HttpClient(getTicket);
		String re = client.get();
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		JsonObject json = jsonparer.parse(re).getAsJsonObject();
		return json.get("ticket").getAsString();
	}
	
	
	public static String deleteMenu() throws Exception {
		String access_token = getAccess_token();
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", access_token);
		HttpURLConnectionUtil httpURLConnectionUtil = new HttpURLConnectionUtil(url);
		String re = httpURLConnectionUtil.execute();
		return re;
	}
	
	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		System.out.println("createSign=========="+sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8")
				.toUpperCase();
		return sign;

	}

	public static void main(String[] args) {
		
		try {
			SortedMap<String, String> configMap = new TreeMap<String, String>();
			configMap.put("jsapi_ticket", "kgt8ON7yVITDhtdwci0qecui9klCX7R82EqOhjMAw3aPct4rw5iUoCA9agkT-GH8KoyX5PTo9pOg8kUJVD0AwA");
			configMap.put("noncestr", "2016082411502304955040025");
			configMap.put("timestamp", "1472010623");
			configMap.put("url", "http://wcwzw.wangcheng.gov.cn/wx/wx/saoyisao.jsp");
			
			//该签名是用于前端js中wx.config配置中的signature值。
			System.out.println("configMap=="+configMap);
			String config_sign = createSign(configMap);
			System.out.println("=============================>"+config_sign);
			
			
			//System.out.println(getAccess_token());
			//createMenu();
			//deleteMenu();
//			groupSending();
			/*File file = new File(CoreService.class.getResource("/menu.json").getPath());
			String content = FileUtils.readFileToString(file, "utf-8");
		    System.out.println("======================================"+content);
		    if (content.startsWith("?")) {
		    	content = content.substring(1);
			} 
		    
		    String  a ="{\"button\":[ {      \"name\":\"认证上网\",      \"sub_button\":[      {          \"type\":\"view\",          \"name\":\"我要上网\",          \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx343a4c2aef3e9349&redirect_uri=http%3A%2F%2Fwww.qq.com%2Foauth2.html&response_type=code&scope=snsapi_userinfo&state=wx343a4c2aef3e9349#wechat_redirect\"       }]  },{      \"name\":\"走进望城\",      \"sub_button\":[      {          \"type\":\"click\",          \"name\":\"望城宣传\",          \"key\":\"WCXC\"       },       {          \"type\":\"click\",          \"name\":\"望城新闻\",          \"key\":\"WCXW\"       },        {          \"type\":\"view\",          \"name\":\"我要办事\",          \"url\":\"path/wechatBuss_genneral.do\"       },      {	          \"type\":\"click\",          \"name\":\"办事查询\",          \"key\":\"path/wechatBuss_queryIndex.do\"       }]  },{      \"name\":\"望城生活\",      \"sub_button\":[       {          \"type\":\"view\",          \"name\":\"便民查询\",          \"url\":\"path/wechatBuss_appointment.do\"       }]  },{          \"type\":\"view\",          \"name\":\"便民查询\",          \"url\":\"path/wechatBuss_userIndex.do\"       }  ]}";
		    
		    System.out.println("=====================================---"+content);
		    if (content.startsWith("?")) {
		    	content = content.substring(1);
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}