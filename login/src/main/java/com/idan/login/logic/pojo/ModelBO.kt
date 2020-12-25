package com.idan.login.logic.pojo

/**
 * @Creator yangzhidan
 * @Date    2020/12/20/0020
 * @Remark
 */


/**
 * 数据库中的user
 */
data class UserBO(val name:String)

data class User(val newSession:Session,val login_stat:String,val login_failure_msg:String)

data class Session(val id:Int,val userId:Int,val userName:String,val userRealName:String)