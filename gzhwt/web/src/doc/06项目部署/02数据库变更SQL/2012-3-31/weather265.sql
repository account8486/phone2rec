
/* 会议省市 */
DROP TABLE IF EXISTS `meeting_area`;

CREATE TABLE `meeting_area` (
	`code` varchar(16) DEFAULT NULL,  
	`name` varchar(32) DEFAULT NULL,
	`parent` varchar(32) DEFAULT NULL,
  	PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into meeting_area values('10','安徽','province');
insert into meeting_area values('11','澳门','province');
insert into meeting_area values('12','北京','province');
insert into meeting_area values('13','福建','province');
insert into meeting_area values('14','甘肃','province');
insert into meeting_area values('15','广东','province');
insert into meeting_area values('16','广西','province');
insert into meeting_area values('17','贵州','province');
insert into meeting_area values('18','海南','province');
insert into meeting_area values('19','河北','province');
insert into meeting_area values('20','河南','province');
insert into meeting_area values('21','黑龙江','province');
insert into meeting_area values('22','湖北','province');
insert into meeting_area values('23','湖南','province');
insert into meeting_area values('24','吉林','province');
insert into meeting_area values('25','江苏','province');
insert into meeting_area values('26','江西','province');
insert into meeting_area values('27','辽宁','province');
insert into meeting_area values('28','内蒙古','province');
insert into meeting_area values('29','宁夏','province');
insert into meeting_area values('30','青海','province');
insert into meeting_area values('31','山东','province');
insert into meeting_area values('32','山西','province');
insert into meeting_area values('33','陕西','province');
insert into meeting_area values('34','上海','province');
insert into meeting_area values('35','四川','province');
insert into meeting_area values('36','台湾','province');
insert into meeting_area values('37','天津','province');
insert into meeting_area values('38','西藏','province');
insert into meeting_area values('39','香港','province');
insert into meeting_area values('40','新疆','province');
insert into meeting_area values('41','云南','province');
insert into meeting_area values('42','浙江','province');
insert into meeting_area values('43','重庆','province');

insert into meeting_area values('1058424','安庆','10');

insert into meeting_area values('1058221','蚌埠','10');

insert into meeting_area values('1058326','巢湖','10');

insert into meeting_area values('1058427','池州','10');

insert into meeting_area values('1058236','滁州','10');

insert into meeting_area values('1058203','阜阳','10');

insert into meeting_area values('1058321','合肥','10');

insert into meeting_area values('1058116','淮北','10');

insert into meeting_area values('1058224','淮南','10');

insert into meeting_area values('1058437','黄山','10');

insert into meeting_area values('1058311','六安','10');

insert into meeting_area values('1058336','马鞍山','10');

insert into meeting_area values('1058122','宿州','10');

insert into meeting_area values('1058429','铜陵','10');

insert into meeting_area values('1058334','芜湖','10');

insert into meeting_area values('1058433','宣城','10');

insert into meeting_area values('1058102','亳州','10');

insert into meeting_area values('1145011','澳门','11');
insert into meeting_area values('1254511','北京','12');
insert into meeting_area values('1358847','福州','13');

insert into meeting_area values('1358927','龙岩','13');

insert into meeting_area values('1358834','南平','13');

insert into meeting_area values('1358846','宁德','13');

insert into meeting_area values('1358946','莆田','13');

insert into meeting_area values('1359131','泉州','13');

insert into meeting_area values('1358828','三明','13');

insert into meeting_area values('1359134','厦门','13');

insert into meeting_area values('1359126','漳州','13');
insert into meeting_area values('1452896','白银','14');

insert into meeting_area values('1452995','定西','14');

insert into meeting_area values('1456080','合作','14');

insert into meeting_area values('1452436','嘉峪关','14');

insert into meeting_area values('1452675','金昌','14');

insert into meeting_area values('1452533','酒泉','14');

insert into meeting_area values('1452889','兰州','14');

insert into meeting_area values('1452984','临夏','14');

insert into meeting_area values('1456096','陇南','14');

insert into meeting_area values('1453915','平凉','14');

insert into meeting_area values('1453923','庆阳','14');

insert into meeting_area values('1457006','天水','14');

insert into meeting_area values('1452679','武威','14');

insert into meeting_area values('1452652','张掖','14');

insert into meeting_area values('1559312','潮州','15');

insert into meeting_area values('1559289','东莞','15');

insert into meeting_area values('1559288','佛山','15');

insert into meeting_area values('1559287','广州','15');

insert into meeting_area values('1559293','河源','15');

insert into meeting_area values('1559297','惠州','15');

insert into meeting_area values('1559473','江门','15');

insert into meeting_area values('1559315','揭阳','15');

insert into meeting_area values('1559659','茂名','15');

insert into meeting_area values('1559109','梅州','15');

insert into meeting_area values('1559280','清远','15');

insert into meeting_area values('1559316','汕头','15');

insert into meeting_area values('1559501','汕尾','15');

insert into meeting_area values('1559082','韶关','15');

insert into meeting_area values('1559493','深圳','15');

insert into meeting_area values('1559663','阳江','15');

insert into meeting_area values('1559471','云浮','15');

insert into meeting_area values('1559658','湛江','15');

insert into meeting_area values('1559278','肇庆','15');

insert into meeting_area values('1559485','中山','15');

insert into meeting_area values('1559488','珠海','15');

insert into meeting_area values('1659211','百色','16');

insert into meeting_area values('1659644','北海','16');

insert into meeting_area values('1659425','崇左','16');

insert into meeting_area values('1659635','防城港','16');

insert into meeting_area values('1657957','桂林','16');

insert into meeting_area values('1659249','贵港','16');

insert into meeting_area values('1659023','河池','16');

insert into meeting_area values('1659065','贺州','16');

insert into meeting_area values('1659242','来宾','16');

insert into meeting_area values('1659046','柳州','16');

insert into meeting_area values('1659431','南宁','16');

insert into meeting_area values('1659632','钦州','16');

insert into meeting_area values('1659265','梧州','16');

insert into meeting_area values('1659453','玉林','16');
insert into meeting_area values('1757806','安顺','17');

insert into meeting_area values('1757707','毕节','17');

insert into meeting_area values('1757827','都匀','17');

insert into meeting_area values('1757816','贵阳','17');

insert into meeting_area values('1757825','凯里','17');

insert into meeting_area values('1756693','六盘水','17');

insert into meeting_area values('1757741','铜仁','17');

insert into meeting_area values('1757907','兴义','17');

insert into meeting_area values('1757713','遵义','17');
insert into meeting_area values('1859758','海口','18');

insert into meeting_area values('1859838','东方','18');

insert into meeting_area values('1859845','儋州','18');

insert into meeting_area values('1859849','琼中','18');

insert into meeting_area values('1859855','琼海','18');

insert into meeting_area values('1859856','文昌','18');

insert into meeting_area values('1859941','五指山','18');

insert into meeting_area values('1859948','三亚','18');

insert into meeting_area values('1954602','保定','19');

insert into meeting_area values('1954616','沧州','19');

insert into meeting_area values('1954423','承德','19');

insert into meeting_area values('1953892','邯郸','19');

insert into meeting_area values('1954702','衡水','19');

insert into meeting_area values('1954515','廊坊','19');

insert into meeting_area values('1954449','秦皇岛','19');

insert into meeting_area values('1953698','石家庄','19');

insert into meeting_area values('1954534','唐山','19');

insert into meeting_area values('1953798','邢台','19');

insert into meeting_area values('1954401','张家口','19');

insert into meeting_area values('2053898','安阳','20');

insert into meeting_area values('2053990','鹤壁','20');

insert into meeting_area values('2053978','济源','20');

insert into meeting_area values('2053982','焦作','20');

insert into meeting_area values('2057091','开封','20');

insert into meeting_area values('2057073','洛阳','20');

insert into meeting_area values('2057178','南阳','20');

insert into meeting_area values('2057171','平顶山','20');

insert into meeting_area values('2057051','三门峡','20');

insert into meeting_area values('2058005','商丘','20');

insert into meeting_area values('2053986','新乡','20');

insert into meeting_area values('2057297','信阳','20');

insert into meeting_area values('2057089','许昌','20');

insert into meeting_area values('2057083','郑州','20');

insert into meeting_area values('2057195','周口','20');

insert into meeting_area values('2057290','驻马店','20');

insert into meeting_area values('2057186','漯河','20');

insert into meeting_area values('2054900','濮阳','20');

insert into meeting_area values('2150842','大庆','21');

insert into meeting_area values('2150442','大兴安岭','21');

insert into meeting_area values('2150953','哈尔滨','21');

insert into meeting_area values('2150775','鹤岗','21');

insert into meeting_area values('2150468','黑河','21');

insert into meeting_area values('2150978','鸡西','21');

insert into meeting_area values('2150873','佳木斯','21');

insert into meeting_area values('2154094','牡丹江','21');

insert into meeting_area values('2150973','七台河','21');

insert into meeting_area values('2150745','齐齐哈尔','21');

insert into meeting_area values('2150884','双鸭山','21');

insert into meeting_area values('2150853','绥化','21');

insert into meeting_area values('2150774','伊春','21');
insert into meeting_area values('2257496','鄂州','22');

insert into meeting_area values('2257447','恩施','22');

insert into meeting_area values('2257498','黄冈','22');

insert into meeting_area values('2258407','黄石','22');

insert into meeting_area values('2257377','荆门','22');

insert into meeting_area values('2257476','荆州','22');

insert into meeting_area values('2257475','潜江','22');

insert into meeting_area values('2257362','神农架','22');

insert into meeting_area values('2257252','十堰','22');

insert into meeting_area values('2257381','随州','22');

insert into meeting_area values('2257483','天门','22');

insert into meeting_area values('2257494','武汉','22');

insert into meeting_area values('2257485','仙桃','22');

insert into meeting_area values('2257590','咸宁','22');

insert into meeting_area values('2257278','襄樊','22');

insert into meeting_area values('2257482','孝感','22');

insert into meeting_area values('2257461','宜昌','22');

insert into meeting_area values('2357662','常德','23');

insert into meeting_area values('2357687','长沙','23');

insert into meeting_area values('2357972','郴州','23');

insert into meeting_area values('2357872','衡阳','23');

insert into meeting_area values('2357749','怀化','23');

insert into meeting_area values('2357763','娄底','23');

insert into meeting_area values('2357766','邵阳','23');

insert into meeting_area values('2357773','湘潭','23');

insert into meeting_area values('2357649','湘西','23');

insert into meeting_area values('2357674','益阳','23');

insert into meeting_area values('2357865','永州','23');

insert into meeting_area values('2357584','岳阳','23');

insert into meeting_area values('2357558','张家界','23');

insert into meeting_area values('2357780','株洲','23');
insert into meeting_area values('2450936','白城','24');

insert into meeting_area values('2454371','白山','24');

insert into meeting_area values('2454161','长春','24');

insert into meeting_area values('2454172','吉林','24');

insert into meeting_area values('2454260','辽源','24');

insert into meeting_area values('2454157','四平','24');

insert into meeting_area values('2450949','松原','24');

insert into meeting_area values('2454363','通化','24');

insert into meeting_area values('2454292','延边','24');
insert into meeting_area values('2558343','常州','25');

insert into meeting_area values('2558141','淮安','25');

insert into meeting_area values('2558044','连云港','25');

insert into meeting_area values('2558238','南京','25');

insert into meeting_area values('2558259','南通','25');

insert into meeting_area values('2558357','苏州','25');

insert into meeting_area values('2558131','宿迁','25');

insert into meeting_area values('2558246','泰州','25');

insert into meeting_area values('2558354','无锡','25');

insert into meeting_area values('2558027','徐州','25');

insert into meeting_area values('2558151','盐城','25');

insert into meeting_area values('2558245','扬州','25');

insert into meeting_area values('2558248','镇江','25');

insert into meeting_area values('2658617','抚州','26');

insert into meeting_area values('2657993','赣州','26');

insert into meeting_area values('2657799','吉安','26');

insert into meeting_area values('2658527','景德镇','26');

insert into meeting_area values('2658502','九江','26');

insert into meeting_area values('2658606','南昌','26');

insert into meeting_area values('2657786','萍乡','26');

insert into meeting_area values('2658637','上饶','26');

insert into meeting_area values('2657796','新余','26');

insert into meeting_area values('2657793','宜春','26');

insert into meeting_area values('2658627','鹰潭','26');
insert into meeting_area values('2754339','鞍山','27');

insert into meeting_area values('2754346','本溪','27');

insert into meeting_area values('2754324','朝阳','27');

insert into meeting_area values('2754662','大连','27');

insert into meeting_area values('2754497','丹东','27');

insert into meeting_area values('2754353','抚顺','27');

insert into meeting_area values('2754237','阜新','27');

insert into meeting_area values('2754453','葫芦岛','27');

insert into meeting_area values('2754337','锦州','27');

insert into meeting_area values('2754347','辽阳','27');

insert into meeting_area values('2754338','盘锦','27');

insert into meeting_area values('2754342','沈阳','27');

insert into meeting_area values('2754249','铁岭','27');

insert into meeting_area values('2754471','营口','27');
insert into meeting_area values('2853602','阿拉善','28');

insert into meeting_area values('2853513','巴彦淖尔','28');

insert into meeting_area values('2853446','包头','28');

insert into meeting_area values('2854218','赤峰','28');

insert into meeting_area values('2853543','鄂尔多斯','28');

insert into meeting_area values('2853463','呼和浩特','28');

insert into meeting_area values('2850527','呼伦贝尔','28');

insert into meeting_area values('2854135','通辽','28');

insert into meeting_area values('2853512','乌海','28');

insert into meeting_area values('2853480','乌兰察布','28');

insert into meeting_area values('2854102','锡林郭勒','28');

insert into meeting_area values('2850838','兴安盟','28');

insert into meeting_area values('2953817','固原','29');

insert into meeting_area values('2953518','石嘴山','29');

insert into meeting_area values('2953612','吴忠','29');

insert into meeting_area values('2953614','银川','29');

insert into meeting_area values('2953704','中卫','29');

insert into meeting_area values('3052737','德令哈','30');

insert into meeting_area values('3052957','共和','30');

insert into meeting_area values('3056043','果洛','30');

insert into meeting_area values('3052875','海东','30');

insert into meeting_area values('3052853','海晏','30');

insert into meeting_area values('3052968','同仁','30');

insert into meeting_area values('3052866','西宁','30');

insert into meeting_area values('3056029','玉树','30');

insert into meeting_area values('3154734','滨州','31');

insert into meeting_area values('3154714','德州','31');

insert into meeting_area values('3154736','东营','31');

insert into meeting_area values('3154906','菏泽','31');

insert into meeting_area values('3154823','济南','31');

insert into meeting_area values('3154915','济宁','31');

insert into meeting_area values('3154828','莱芜','31');

insert into meeting_area values('3154806','聊城','31');

insert into meeting_area values('3154938','临沂','31');

insert into meeting_area values('3154857','青岛','31');

insert into meeting_area values('3154945','日照','31');

insert into meeting_area values('3154827','泰安','31');

insert into meeting_area values('3154774','威海','31');

insert into meeting_area values('3154843','潍坊','31');

insert into meeting_area values('3154765','烟台','31');

insert into meeting_area values('3158024','枣庄','31');

insert into meeting_area values('3154830','淄博','31');
insert into meeting_area values('3253882','长治','32');

insert into meeting_area values('3253487','大同','32');

insert into meeting_area values('3253976','晋城','32');

insert into meeting_area values('3253776','晋中','32');

insert into meeting_area values('3253868','临汾','32');

insert into meeting_area values('3253764','吕梁','32');

insert into meeting_area values('3253578','朔州','32');

insert into meeting_area values('3253772','太原','32');

insert into meeting_area values('3253674','忻州','32');

insert into meeting_area values('3253782','阳泉','32');

insert into meeting_area values('3253959','运城','32');

insert into meeting_area values('3357245','安康','33');

insert into meeting_area values('3357016','宝鸡','33');

insert into meeting_area values('3357127','汉中','33');

insert into meeting_area values('3357143','商洛','33');

insert into meeting_area values('3353947','铜川','33');

insert into meeting_area values('3357045','渭南','33');

insert into meeting_area values('3357036','西安','33');

insert into meeting_area values('3357048','咸阳','33');

insert into meeting_area values('3353845','延安','33');

insert into meeting_area values('3353646','榆林','33');
insert into meeting_area values('3458362','上海','34');
insert into meeting_area values('3556171','阿坝','35');

insert into meeting_area values('3557313','巴中','35');

insert into meeting_area values('3556294','成都','35');

insert into meeting_area values('3557328','达州','35');

insert into meeting_area values('3556198','德阳','35');

insert into meeting_area values('3556146','甘孜','35');

insert into meeting_area values('3557415','广安','35');

insert into meeting_area values('3557206','广元','35');

insert into meeting_area values('3556386','乐山','35');

insert into meeting_area values('3556391','眉山','35');

insert into meeting_area values('3556196','绵阳','35');

insert into meeting_area values('3557411','南充','35');

insert into meeting_area values('3557504','内江','35');

insert into meeting_area values('3556666','攀枝花','35');

insert into meeting_area values('3557405','遂宁','35');

insert into meeting_area values('3556571','西昌','35');

insert into meeting_area values('3556287','雅安','35');

insert into meeting_area values('3556492','宜宾','35');

insert into meeting_area values('3556298','资阳','35');

insert into meeting_area values('3556396','自贡','35');

insert into meeting_area values('3557602','泸州','35');

insert into meeting_area values('3659554','高雄','36');

insert into meeting_area values('3658968','台北','36');

insert into meeting_area values('3659158','台中','36');
insert into meeting_area values('3754527','天津','37');
insert into meeting_area values('3855437','阿里','38');

insert into meeting_area values('3856137','昌都','38');

insert into meeting_area values('3855591','拉萨','38');

insert into meeting_area values('3856312','林芝','38');

insert into meeting_area values('3855299','那曲','38');

insert into meeting_area values('3855578','日喀则','38');

insert into meeting_area values('3855597','山南','38');
insert into meeting_area values('3945007','香港','39');
insert into meeting_area values('4051628','阿克苏','40');

insert into meeting_area values('4051730','阿拉尔','40');

insert into meeting_area values('4051076','阿勒泰','40');

insert into meeting_area values('4051704','阿图什','40');

insert into meeting_area values('4051238','博乐','40');

insert into meeting_area values('4051368','昌吉','40');

insert into meeting_area values('4052203','哈密','40');

insert into meeting_area values('4051828','和田','40');

insert into meeting_area values('4051709','喀什','40');

insert into meeting_area values('4051243','克拉玛依','40');

insert into meeting_area values('4051656','库尔勒','40');

insert into meeting_area values('4051356','石河子','40');

insert into meeting_area values('4051133','塔城','40');

insert into meeting_area values('4051573','吐鲁番','40');

insert into meeting_area values('4051463','乌鲁木齐','40');

insert into meeting_area values('4051431','伊宁','40');
insert into meeting_area values('4156748','保山','41');

insert into meeting_area values('4156768','楚雄','41');

insert into meeting_area values('4156751','大理','41');

insert into meeting_area values('4156844','德宏','41');

insert into meeting_area values('4156444','迪庆','41');

insert into meeting_area values('4156975','红河','41');

insert into meeting_area values('4156778','昆明','41');

insert into meeting_area values('4156651','丽江','41');

insert into meeting_area values('4156951','临沧','41');

insert into meeting_area values('4156643','怒江','41');

insert into meeting_area values('4156783','曲靖','41');

insert into meeting_area values('4156964','思茅','41');

insert into meeting_area values('4156994','文山','41');

insert into meeting_area values('4156959','西双版纳','41');

insert into meeting_area values('4156875','玉溪','41');

insert into meeting_area values('4156586','昭通','41');
insert into meeting_area values('4258457','杭州','42');

insert into meeting_area values('4258450','湖州','42');

insert into meeting_area values('4258452','嘉兴','42');

insert into meeting_area values('4258549','金华','42');

insert into meeting_area values('4258646','丽水','42');

insert into meeting_area values('4258465','宁波','42');

insert into meeting_area values('4258453','绍兴','42');

insert into meeting_area values('4258651','台州','42');

insert into meeting_area values('4258659','温州','42');

insert into meeting_area values('4258477','舟山','42');

insert into meeting_area values('4258633','衢州','42');

insert into meeting_area values('4357516','重庆','43');