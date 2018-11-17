``获取学生信息``
/student/info
get

``学生修改自己信息``
/student/info
post
name，nid,phone,avatarUrl

``搜索班级信息``
/student/class
get
classId

``加入班级``
/student/class
put
classId

``查询学生的所有作业``
get
/student/homework

``获取作业详情``
/student/homework/detail
get
homeworkId

``提交作业``
/student/homework
post
image(支持数组)