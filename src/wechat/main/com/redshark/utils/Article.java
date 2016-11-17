package com.redshark.utils;


/**
 * @Description:图文消息中Article 类的定义
 * @author ximing.fu
 * @time:2015年11月5日 下午2:00:43
 *
 */
public class Article {

	// 图文消息名称
	private String Title;
	// 图文消息描述
	private String Description;
	// 图片链接，支持JPG、PNG 格式，较好的效果为大图640*320，小图80*80，限制图片 链接的域名需要与开发者填写的基本资料中的Url 一致
	private String PicUrl;
	// 点击图文消息跳转链接
	private String Url;
	
	//自定区分类型字段
	private String Type;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

}
