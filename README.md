# GKCalendarServer
	GKCalendar客户端对应的服务端，基于java，MySQL数据库，Struts2框架开发。



#GKCalendar服务器接口文档
## 注意：
* 所有需要登录的操作请求均需要传token参数与username
* 本文档主机未定，暂时使用localhost:8080替代主机


## 1. 用户模块

### 1.1 登录
	请求
		接口：http://localhost:8080/GKCalendarServer/user_login
		参数：username（用户名）、 password（密码）
		示例：http://localhost:8080/GKCalendarServer/user_login?username=root&password=root
	
	返回
		{
    		"message": 1, // 登录成功
    		"ret_code": 200,
    		"token": "3506402"
		}
		
		{
			"message":0, // 登录失败
			"ret_code":200
		}
		
### 1.2 注册
	请求
		接口：http://localhost:8080/GKCalendarServer/user_register
		参数：username（用户名）、 password（密码）
		示例：http://localhost:8080/GKCalendarServer/user_register?username=root&password=root
	
	返回
		{
			"message":"注册成功", 
			"ret_code":200
		}
		
		{
			"message":"注册失败", 
			"ret_code":200
		}
### 1.3 退出	
	请求
		接口：http://localhost:8080/GKCalendarServer/user_logout
		参数：username（用户名）、 token
		示例：http://localhost:8080/GKCalendarServer/user_logout?username=root&token=3506402
	
	返回
		{
			"message":"退出成功", 
			"ret_code":200
		}
		
		{
			"message":"请先登录", 
			"ret_code":200
		}

## 2. 日程模块

### 2.1 获取个人日程
	请求
		接口：http://localhost:8080/GKCalendarServer/calendar_query
		参数：username（用户名）、 token
		示例：http://localhost:8080/GKCalendarServer/calendar_query?username=root&token=3506402
	
	返回
		{
    		"message": [
       					{
            					"creator": "root",
            					"calendar_id": "1",
            					"version": 8,
            					"unixstamp": 99,
            					"calendar_title": "毕业答辩",
            					"content": "123"
     			 			}
    		],
    		"ret_code": 200
		}
		
### 2.2 创建个人日程
	请求
		接口：http://localhost:8080/GKCalendarServer/calendar_create
		参数：username（用户名）、 token 、 calendar_name(日程名) 、 date（unix时间戳）、content（日程内容）
		示例：http://localhost:8080/GKCalendarServer/calendar_create?username=root&calendar_name=helloworld&date=198198198&content=hellowworld!&token=3506402
	
	返回
		{
		"message":"创建日程成功",
		"ret_code":200
		}
		
### 2.3 添加成员
	请求
		接口：http://localhost:8080/GKCalendarServer/calendar_addMember
		参数：username（用户名）、 token 、 newMemberName(新用户名) 、 calendar_id（日程id）		示例：http://localhost:8080/GKCalendarServer/calendar_addMember?username=root&newMemberName=root1&token=3506402&calendar_id=11
	
	返回
		{
		"message":"添加成员成功",
		"ret_code":200
		}
		
### 2.4 修改日程内容
	请求
		接口：http://localhost:8080/GKCalendarServer/calendar_alter
		参数：username（用户名）、 token 、 version(客户端日程版本号) 、 calendar_id（日程id）、 date（unix时间戳）、content（日程内容）
		示例：http://localhost:8080/GKCalendarServer/calendar_alter?version=8&calendar_id=1&username=root&content=123&date=99&token=3506402
	
	返回
		{
		"calendar_id":1,
		"message":"success", // 成功更新
		"ret_code":200,
		"version":9
		}
		
		{
		"calendar_id":1,
		"message":"failure", // 失败更新
		"ret_code":200,
		"version":8 // 日程服务端当前版本
		}