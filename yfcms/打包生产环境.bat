
echo 开始清理译Product环境...
call mvn clean

echo 开始编译打包Dev环境....
call mvn -Denv=Product install
call mvn -Denv=Product install

echo 编译打包Product环境结束

cmd