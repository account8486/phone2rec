
echo 开始清理译测试环境...
call mvn clean

echo 开始编译打包测试环境....
call mvn -Denv=Test package


echo 编译打包测试环境结束

cmd