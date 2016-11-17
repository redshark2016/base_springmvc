package com.redshark.app.utils;

import com.redshark.app.constants.AppConstant;
import com.redshark.app.model.AppReturnData;
import com.redshark.utils.StringUtil;



/**
 * 返回组装类
* @ClassName: AppReturnDataUtil  
* @Description: TODO 
* @author jianyue.yan
* @date 2016-10-20 上午10:57:12  
*
 */
public abstract class AppReturnDataUtil {

	/**
	 * 成功
	 * @param 
	 * @return
	 */
	public static void success(AppReturnData returnData , String message){
		returnData.setStatus(AppConstant.SUCCESS_FLAG);
		returnData.setMessage(message);
	}
	
	/**
	 * 失败
	 * @param 
	 * @return
	 */
	public static void error(AppReturnData returnData , String message){
		returnData.setStatus(AppConstant.ERROR_FLAG);
		String msg = returnData.getMessage();
		if(!StringUtil.isBlank(message)){
			if(StringUtil.isBlank(msg)){
				returnData.setMessage(message);
			}else{
				returnData.setMessage(msg + "," + message);
			}
		}
	}
}
