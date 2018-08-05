``登录``
url：api/student/login
methods:GET
data: {
    code:code
}
``结果``
{
  "code": 0,
  "msg": "成功",
  "data": id
 }

``获取学生资料``
url：api/student/infoid
methods：GET
data：{
    id:1
}
``结果``
{
  "code": 0,
  "msg": "成功",
  "data": {
    "nid": "123",
    "name": "123",
    "phone": "1",
    "avatarUrl": "1"
  }
}