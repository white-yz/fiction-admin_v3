webpackJsonp([11],{CuRo:function(e,t){},NHnr:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=n("7+uW"),o={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},staticRenderFns:[]};var a=n("VU/8")({name:"App"},o,!1,function(e){n("bbB1")},null,null).exports,i=n("YaEn"),c=n("NYxO");r.default.use(c.a);var l=new c.a.Store({state:{HOST:"http://127.0.0.1:8888"}}),u=n("zL8q"),s=n.n(u),d=(n("tvR6"),n("CuRo"),n("j1ja"),n("vO7p")),m=n.n(d),h=n("TXmL");r.default.use(h.a),r.default.use(s.a),r.default.use(m.a);var p=new h.a({locale:"CN",messages:{US:{common:{search:"Filter keywords",t1:"User gender ratio",t2:"Distribution of Book Types",t3:"Sex ratio of writers",t4:"Nationality Distribution of Writers",t5:"User age group ratio",t6:"Age distribution and ranking of writers",consumerCount:"Total number of users",bookCount:"The total number of books",writerCount:"The total number of authors",batchDel:"  Batch delete",confirm:"Confirm",cancel:"Cancel",operation:"Operation",sex:"gender",woman:"Woman",man:"Man",combination:"Combination",unknown:"Unknown",birth:"Birth",selectDate:"Select date",location:"location",introduction:"Introduction",style:"Style",updatePic:"Update pic",phone:"Phone number",email:"Email",signature:"Signature",delText:"Unrecoverable after deletion. Are you sure to delete?",pwd:"password"},login:{loginTxt:"login",username:"please enter user name",password:"Please enter password"},header:{manageName:"Hi Read Background management",signOut:"sign out"},leftNav:{Info:"home",Consumer:"user ",Writer:"writer ",Classify:"classify"},writer:{enterName:"Please enter author name search",addWriter:"Add the writer",editWriter:"Editor the writer",delWriter:"Delete the writer",writerName:"Writer name",pic:"Writer picture"},book:{enterName:"Please enter the book name search",add:"Add the book",edit:"Editor the book",del:"Delete the book",Name:"Book name",pic:"Book picture"},directory:{enterName:"Please enter directory name search",add:"Add the directory",edit:"Editor the directory",del:"Delete the directory",Name:"Directory name",content:"Content"},user:{enterName:"Please enter the user name search",add:"Add the user",edit:"Editor the user",del:"Delete the user",Name:"User name",pic:"User picture"}},CN:{common:{search:"筛选关键词",t1:"用户性别比例",t2:"书籍类型分布",t3:"作家性别比例",t4:"作家地区分布",t5:"用户年龄段比例",t6:"作家年龄段分布及排序",consumerCount:"用户总数",bookCount:"书籍总数",writerCount:"作家总数",batchDel:"批量删除",confirm:"确定",cancel:"取消",operation:"操作",updatePic:"更新图片",delText:"删除后不可恢复，是否确定删除？",freezeText:"用户账号将被冻结，是否确定继续？",thawText:"用户账号将解冻，是否确定继续？",sex:"性别",woman:"女",man:"男",combination:"组合",unknown:"不明",birth:"生日",selectDate:"选择日期",location:"地区",style:"风格",introduction:"简介",isCharge:"是否收费",phone:"电话号码",email:"电子邮箱",signature:"签名",pwd:"密码",state:"状态"},login:{loginTxt:"登录",username:"请输入用户名",password:"请输入密码"},header:{manageName:"嗨阅读后台管理",signOut:"退出登录"},leftNav:{Info:"系统首页",Consumer:"用户管理",Writer:"作家管理",Classify:"分类管理"},writer:{enterName:"请输入作家名搜索",addWriter:"添加作家",editWriter:"编辑作家",delWriter:"删除作家",writerName:"作家名",pic:"作家图片"},book:{enterName:"请输入书籍名搜索",add:"添加书籍",edit:"编辑书籍",del:"删除书籍",Name:"书籍名",pic:"书籍图片"},directory:{enterName:"请输入目录名搜索",add:"添加目录",edit:"编辑目录",del:"删除目录",Name:"目录名",content:"内容"},user:{enterName:"请输入用户名搜索",add:"添加用户",edit:"编辑用户",del:"删除用户",freeze:"冻结用户",thaw:"解冻用户",Name:"用户名",pic:"用户图片"}},HK:{common:{search:"篩選關鍵詞",t1:"用戶性別比例",t2:"書籍類型分布",t3:"作家性別比例",t4:"作家地區分布",t5:"用戶年齡段比例",t6:"作家年齡段分布及排序",consumerCount:"用戶總數",bookCount:"書籍總數",writerCount:"作家總數",batchDel:"  批量刪除",confirm:"確定",cancel:"取消",operation:"操作",sex:"性別",woman:"女",man:"男",combination:"組合",unknown:"不明",birth:"生日",selectDate:"選擇日期",location:"地區",introduction:"簡介",style:"風格",updatePic:"更新圖片",phone:"電話號碼",email:"電子郵箱",signature:"簽名",delText:"刪除後不可恢復，是否確定刪除？",pwd:"密碼"},login:{loginTxt:"登錄",username:"請輸入用戶名",password:"請輸入密碼"},header:{manageName:"嗨閱讀後臺管理",signOut:"退出登錄"},leftNav:{Info:"系統首頁",Consumer:"用戶管理",Writer:"作家管理",Classify:"分类管理"},writer:{enterName:"請輸入作家名搜索",addWriter:"添加作家",editWriter:"編輯作家",delWriter:"刪除作家",writerName:"作家名",pic:"作家圖片"},book:{enterName:"請輸入書籍名搜索",add:"添加書籍",edit:"編輯書籍",del:"刪除書籍",Name:"書籍名",pic:"書籍圖片"},directory:{enterName:"請輸入目錄名搜索",add:"添加目錄",edit:"编辑目錄",del:"删除目錄",Name:"目錄名",content:"内容"},user:{enterName:"請輸入用戶名搜索",add:"添加用戶",edit:"編輯用戶",del:"删除用户",Name:"用戶名",pic:"用戶圖片"}}}});new r.default({el:"#app",router:i.a,store:l,i18n:p,render:function(e){return e(a)}})},YaEn:function(e,t,n){"use strict";var r=n("7+uW"),o=n("/ocq");r.default.use(o.a),t.a=new o.a({routes:[{path:"/",component:function(e){return Promise.all([n.e(0),n.e(1)]).then(function(){var t=[n("P7ryO")];e.apply(null,t)}.bind(this)).catch(n.oe)}},{path:"/Home",component:function(e){return n.e(2).then(function(){var t=[n("lO7g")];e.apply(null,t)}.bind(this)).catch(n.oe)},children:[{path:"/Info",component:function(e){return Promise.all([n.e(0),n.e(9)]).then(function(){var t=[n("J262")];e.apply(null,t)}.bind(this)).catch(n.oe)}},{path:"/Consumer",component:function(e){return Promise.all([n.e(0),n.e(3)]).then(function(){var t=[n("D3eR")];e.apply(null,t)}.bind(this)).catch(n.oe)}},{path:"/Writer",component:function(e){return Promise.all([n.e(0),n.e(4)]).then(function(){var t=[n("uVhY")];e.apply(null,t)}.bind(this)).catch(n.oe)}},{path:"/Book",component:function(e){return Promise.all([n.e(0),n.e(5)]).then(function(){var t=[n("F5ps")];e.apply(null,t)}.bind(this)).catch(n.oe)}},{path:"/Directory",component:function(e){return Promise.all([n.e(0),n.e(6)]).then(function(){var t=[n("b9lL")];e.apply(null,t)}.bind(this)).catch(n.oe)}},{path:"/Collect",component:function(e){return Promise.all([n.e(0),n.e(8)]).then(function(){var t=[n("dym1")];e.apply(null,t)}.bind(this)).catch(n.oe)}},{path:"/Classify",component:function(e){return Promise.all([n.e(0),n.e(7)]).then(function(){var t=[n("kE/w")];e.apply(null,t)}.bind(this)).catch(n.oe)}}]}]})},bbB1:function(e,t){},tvR6:function(e,t){}},["NHnr"]);
//# sourceMappingURL=app.c657b935ee3927aaf046.js.map