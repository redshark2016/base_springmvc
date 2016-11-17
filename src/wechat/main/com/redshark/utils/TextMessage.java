package com.redshark.utils;


/**
 * @Description:响应消息之文本消息
 * @author ximing.fu
 * @time:2015年11月5日 下午1:56:31
 *
 */
public class TextMessage extends BaseMessage {

	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
