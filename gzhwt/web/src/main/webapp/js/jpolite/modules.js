// Module ID & link definitions
// Format:
// moduleId:{l:"url_of_this_module",
// 			 t:"title_for_this_module",
// 			 c:"optional color definition for title bar"}
var _modules={

	m101:{l:"/meeting/pages/admin/pri/meeting/map.jsp",	t:"Module:m201"}
};

// Layout definitions for each tab, aka, which modules go to which columns under which tab
// Format:
//	{i:"id_of_the_module	(refer to _modules)",
//	c:"column_it_belongs_to	(c1, c2, c3)"
//	t:"tab_it_belongs_to	(t1, t2, ...)"}
var _layout=[
	{i:'m101',c:'c1',t:'t1'},{i:'m102',c:'c2',t:'t1'},{i:'m103',c:'c3',t:'t1'},

	{i:'m201',c:'c1',t:'t2'},{i:'m202',c:'c2',t:'t2'},{i:'m203',c:'c3',t:'t2'},
	{i:'m204',c:'c1',t:'t2'},{i:'m206',c:'c2',t:'t2'},{i:'m205',c:'c3',t:'t2'},

	{i:'m301',c:'c1',t:'t3'},{i:'m302',c:'c2',t:'t3'},{i:'m303',c:'c3',t:'t3'},

	{i:'m400',c:'c1',t:'t4'},{i:'m401',c:'c2',t:'t4'},

	{i:'m500',c:'c1',t:'t5'},{i:'m501',c:'c2',t:'t5'},

	{i:'m700',c:'c1',t:'t7'},{i:'m701',c:'c2',t:'t7'},
							 {i:'m702',c:'c2',t:'t7'},

	{i:'m601',c:'c1',t:'t6'},
	{i:'m602',c:'c1',t:'t6'}
];

// Column width definitions for each tab
// Valid values are pixel, % or auto
// Currently, "auto" is only valid on column2
// You can support more features by refining function HeaderTabClick()
var _tabs={
	t1:{c1:"33.3%",c2:"33.3%",c3:"33.3%",helper:true},
	t2:{c1:"33.3%",c2:"33.3%",c3:"33.3%",helper:true},
	t3:{c1:"50%",c2:"50%",c3:"100%",helper:true},
	t4:{c1:"270px",c2:"auto",c3:0,helper:true},
	t5:{c1:"270px",c2:"auto",c3:0,helper:true},
	t6:{c1:"100%",c2:0,c3:0},
	t7:{c1:"270px",c2:"auto",c3:0}
};