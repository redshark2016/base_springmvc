package com.redshark.app.model;



/**
 * 接口返回格式组装
* @ClassName: AppReturnData  
* @Description: TODO 
* @author jianyue.yan
* @date 2016-10-20 上午10:56:05  
*
 */
public class AppReturnData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Object data;
	private String status;
	private String message;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}