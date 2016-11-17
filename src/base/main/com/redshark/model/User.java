package com.redshark.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.management.relation.RoleInfo;

public class User implements Serializable {
	private static final long serialVersionUID = -2465519067844351347L;
	private String id;
	private String user_name;
	private String password;
	private String idcard;// 身份证
	private String real_name;// 真实姓名
	private Date birthday;
	private String email;// 邮箱
	private String question;// 密码提示问题
	private String answer;// 找回密码答案
	private Date reg_date;// 注册时间
	private String type;// 用户身份
	private String login_num;// 登录次数
	private Date last_login_date;// 最后登录时间
	private Date current_login_date;// 当前登录时间
	private Integer auditType;// 认证类型
	private Long status;// 用户状态 0：禁用 1： 
	private String lastlogin;
	private String organization;
	private List<RoleInfo> roleList;
	private Date last_update_time;
	private String old_user_name;  //二期资源网中用户名，用于绑定二期账号信息
	private Integer sort; //排序
	
	private String active_flag;
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}



	public String getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}


	public Integer getAuditType() {
		return auditType;
	}

	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}


	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public List<RoleInfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getLogin_num() {
		return login_num;
	}

	public void setLogin_num(String login_num) {
		this.login_num = login_num;
	}

	public Date getLast_login_date() {
		return last_login_date;
	}

	public void setLast_login_date(Date last_login_date) {
		this.last_login_date = last_login_date;
	}

	public Date getCurrent_login_date() {
		return current_login_date;
	}

	public void setCurrent_login_date(Date current_login_date) {
		this.current_login_date = current_login_date;
	}

	public Date getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}

	public String getOld_user_name() {
		return old_user_name;
	}

	public void setOld_user_name(String old_user_name) {
		this.old_user_name = old_user_name;
	}

	public String getActive_flag() {
		return active_flag;
	}

	public void setActive_flag(String active_flag) {
		this.active_flag = active_flag;
	}



}
