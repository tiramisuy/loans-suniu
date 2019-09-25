##影像资料服务器
1、基本流程
  a、上传图片：app/browser>nginx>app server>file server
  b、访问图片：app/browser>nginx>file server
  c、app服务器怎么跟图片服务器打交道？app server  POST  file server
2、生成缩略图
  a、文件服务器只保存原图，如果访问的缩略图不存在，则生成后保存
  b、根据需要生成不用尺寸的缩略图Nginx+lua+graphicsmagick
3、图片服务器的目录结构怎么设计？
  a、/www/img/biz_name/2017/06/20/e10adc3949ba59abbe56e057f20f883e.png
4、图片服务器安全性问题
  a、访问控制
  b、防止盗链