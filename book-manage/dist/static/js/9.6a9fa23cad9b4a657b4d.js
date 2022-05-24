webpackJsonp([9],{"7Udu":function(t,a){},J262:function(t,a,r){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var e=r("BO1k"),s=r.n(e),o=r("gyMJ"),n={data:function(){var t=this;return this.sexSettings={dimension:"talkDuration",metrics:"总数",radius:["40%","65%"]},this.sexExtend={legend:{show:!0,orient:"horizontal",icon:"circle",bottom:0,itemGap:20,formatter:function(a){var r="";return t.sexData&&(r=t.sexData.rows.find(function(t){return t.talkDuration===a})["总数"]),a+"   "+r+"   "}},series:{center:["50%","35%"],label:{normal:{show:!1}}},color:["#FA6090","#5253FD","#05C8D6","yellow"]},this.areaSettings={dimension:["talkDuration"],metrics:["总数"],dataOrder:{label:"总数",order:"desc"},itemStyle:{normal:{label:{show:!0,position:"right"}}},max:[100,100]},this.areaExtend={"yAxis.0.splitLine.show":!1,"xAxis.0.splitLine.show":!1,"xAxis.0.axisLabel.show":!1,yAxis:{axisLabel:{textStyle:{color:"#9B9B9B",padding:[10,15,5,6]}}},series:function(t){return t.forEach(function(t){t.barWidth=45}),t}},{consumerCount:0,bookCount:0,writerCount:0,consumer:[],consumerSex:{columns:["性别","总数"],rows:[{"性别":"男","总数":0},{"性别":"女","总数":0}]},year:{columns:["年份","总数"],rows:[{"年份":"50后","总数":0,"标准":"195"},{"年份":"60后","总数":0,"标准":"196"},{"年份":"70后","总数":0,"标准":"197"},{"年份":"80后","总数":0,"标准":"198"},{"年份":"90后","总数":0,"标准":"199"},{"年份":"00后","总数":0,"标准":"200"},{"年份":"10后","总数":0,"标准":"201"},{"年份":"其他","总数":0}]},options:{color:["#87cefa","#ffc0cb"]},options1:{color:["#006600"]},bookStyle:{columns:["风格","总数"],rows:[{"风格":"武侠仙侠","总数":0},{"风格":"穿越小说","总数":0},{"风格":"历史军事","总数":0},{"风格":"现代言情","总数":0},{"风格":"都市生活","总数":0},{"风格":"悬疑科幻","总数":0},{"风格":"宫斗权谋","总数":0},{"风格":"奇幻玄幻","总数":0},{"风格":"","总数":0}]},country:{columns:["国籍","总数"],rows:[{"国籍":"上海","总数":0},{"国籍":"遂宁","总数":0},{"国籍":"成都","总数":0},{"国籍":"重庆","总数":0},{"国籍":"北京","总数":0},{"国籍":"浙江","总数":0},{"国籍":"广东","总数":0},{"国籍":"厦门","总数":0}]},sexData:{rows:[{talkDuration:"女","总数":0},{talkDuration:"男","总数":0},{talkDuration:"组合","总数":0},{talkDuration:"不明","总数":0}]},colors:["#CC00FF"],areaData:{rows:[{talkDuration:"50后","总数":0,"标准":"195"},{talkDuration:"60后","总数":0,"标准":"196"},{talkDuration:"70后","总数":0,"标准":"197"},{talkDuration:"80后","总数":0,"标准":"198"},{talkDuration:"90后","总数":0,"标准":"199"},{talkDuration:"00后","总数":0,"标准":"200"},{talkDuration:"10后","总数":0,"标准":"201"},{talkDuration:"其他","总数":0}]}}},mounted:function(){this.getConsumer(),this.getBook(),this.getWriter()},methods:{getConsumer:function(){var t=this;Object(o.j)().then(function(a){t.consumer=a,t.consumerCount=a.length,t.consumerSex.rows[0]["总数"]=t.setSex(1,t.consumer),t.consumerSex.rows[1]["总数"]=t.setSex(0,t.consumer);var r=!0,e=!1,o=void 0;try{for(var n,i=s()(a);!(r=(n=i.next()).done);r=!0){var l=n.value;t.getYear(l.birth.slice(0,4))}}catch(t){e=!0,o=t}finally{try{!r&&i.return&&i.return()}finally{if(e)throw o}}t.year.rows[7]["总数"]-=7*a.length})},getYear:function(t){var a=!0,r=!1,e=void 0;try{for(var o,n=s()(this.year.rows);!(a=(o=n.next()).done);a=!0){var i=o.value;t.includes(i["标准"])?i["总数"]++:this.year.rows[7]["总数"]++}}catch(t){r=!0,e=t}finally{try{!a&&n.return&&n.return()}finally{if(r)throw e}}},getYears:function(t){var a=!0,r=!1,e=void 0;try{for(var o,n=s()(this.areaData.rows);!(a=(o=n.next()).done);a=!0){var i=o.value;t.includes(i["标准"])?i["总数"]++:this.areaData.rows[7]["总数"]++}}catch(t){r=!0,e=t}finally{try{!a&&n.return&&n.return()}finally{if(r)throw e}}},setSex:function(t,a){var r=0,e=!0,o=!1,n=void 0;try{for(var i,l=s()(a);!(e=(i=l.next()).done);e=!0){t==i.value.sex&&r++}}catch(t){o=!0,n=t}finally{try{!e&&l.return&&l.return()}finally{if(o)throw n}}return r},getBook:function(){var t=this;Object(o.i)().then(function(a){t.bookCount=a.length;var r=!0,e=!1,o=void 0;try{for(var n,i=s()(a);!(r=(n=i.next()).done);r=!0){var l=n.value;t.getByStyle(l.style)}}catch(t){e=!0,o=t}finally{try{!r&&i.return&&i.return()}finally{if(e)throw o}}})},getWriter:function(){var t=this;Object(o.k)().then(function(a){t.writerCount=a.length,t.sexData.rows[0]["总数"]=t.setSex(0,a),t.sexData.rows[1]["总数"]=t.setSex(1,a),t.sexData.rows[2]["总数"]=t.setSex(2,a),t.sexData.rows[3]["总数"]=t.setSex(3,a);var r=!0,e=!1,o=void 0;try{for(var n,i=s()(a);!(r=(n=i.next()).done);r=!0){var l=n.value;t.getByCountry(l.location),t.getYears(l.birth.slice(0,4))}}catch(t){e=!0,o=t}finally{try{!r&&i.return&&i.return()}finally{if(e)throw o}}t.areaData.rows[7]["总数"]-=7*a.length})},getByStyle:function(t){var a=!0,r=!1,e=void 0;try{for(var o,n=s()(this.bookStyle.rows);!(a=(o=n.next()).done);a=!0){var i=o.value;t.includes(i["风格"])&&i["总数"]++}}catch(t){r=!0,e=t}finally{try{!a&&n.return&&n.return()}finally{if(r)throw e}}},getByCountry:function(t){var a=!0,r=!1,e=void 0;try{for(var o,n=s()(this.country.rows);!(a=(o=n.next()).done);a=!0){var i=o.value;t.includes(i["国籍"])&&i["总数"]++}}catch(t){r=!0,e=t}finally{try{!a&&n.return&&n.return()}finally{if(r)throw e}}}}},i={render:function(){var t=this,a=t.$createElement,r=t._self._c||a;return r("div",[r("el-row",{staticClass:"mgb20",attrs:{gutter:20}},[r("el-col",{attrs:{span:8}},[r("el-card",{staticClass:"card"},[r("div",{staticClass:"grid-content"},[r("div",{staticClass:"grid-cont-center"},[r("div",{staticClass:"grid-num"},[t._v(t._s(t.consumerCount))]),t._v(" "),r("div",[t._v(t._s(t.$t("common.consumerCount")))])])])])],1),t._v(" "),r("el-col",{attrs:{span:8}},[r("el-card",{staticClass:"card"},[r("div",{staticClass:"grid-content"},[r("div",{staticClass:"grid-cont-center"},[r("div",{staticClass:"grid-num"},[t._v(t._s(t.bookCount))]),t._v(" "),r("div",[t._v(t._s(t.$t("common.bookCount")))])])])])],1),t._v(" "),r("el-col",{attrs:{span:8}},[r("el-card",{staticClass:"card"},[r("div",{staticClass:"grid-content"},[r("div",{staticClass:"grid-cont-center"},[r("div",{staticClass:"grid-num"},[t._v(t._s(t.writerCount))]),t._v(" "),r("div",[t._v(t._s(t.$t("common.writerCount")))])])])])],1)],1),t._v(" "),r("el-row",{staticClass:"mgb20",attrs:{gutter:20}},[r("el-col",{attrs:{span:12}},[r("h3",{staticClass:"mgb20 txt"},[t._v(t._s(t.$t("common.t1")))]),t._v(" "),r("div",{staticClass:"p20 card",staticStyle:{"background-color":"white"}},[r("ve-pie",{attrs:{data:t.consumerSex,theme:t.options}})],1)]),t._v(" "),r("el-col",{attrs:{span:12}},[r("h3",{staticClass:"mgb20 txt"},[t._v(t._s(t.$t("common.t2")))]),t._v(" "),r("div",{staticClass:"p20 card",staticStyle:{"background-color":"white"}},[r("ve-histogram",{attrs:{data:t.bookStyle}})],1)])],1),t._v(" "),r("el-row",{staticClass:"mgb20",attrs:{gutter:20}},[r("el-col",{attrs:{span:12}},[r("h3",{staticClass:"mgb20 txt"},[t._v(t._s(t.$t("common.t3")))]),t._v(" "),r("div",{staticClass:"p20 card",staticStyle:{"background-color":"white"}},[r("ve-ring",{attrs:{"legend-visible":!0,extend:t.sexExtend,resizeable:!0,settings:t.sexSettings,data:t.sexData}})],1)]),t._v(" "),r("el-col",{attrs:{span:12}},[r("h3",{staticClass:"mgb20 txt"},[t._v(t._s(t.$t("common.t4")))]),t._v(" "),r("div",{staticClass:"p20 card",staticStyle:{"background-color":"white"}},[r("ve-histogram",{attrs:{data:t.country,theme:t.options1}})],1)])],1),t._v(" "),r("el-row",{staticClass:"mgb20",attrs:{gutter:20}},[r("el-col",{attrs:{span:12}},[r("h3",{staticClass:"mgb20 txt"},[t._v(t._s(t.$t("common.t5")))]),t._v(" "),r("div",{staticClass:"p20 card",staticStyle:{"background-color":"white"}},[r("ve-pie",{attrs:{data:t.year}})],1)]),t._v(" "),r("el-col",{attrs:{span:12}},[r("h3",{staticClass:"mgb20 txt"},[t._v(t._s(t.$t("common.t6")))]),t._v(" "),r("div",{staticClass:"p20 card",staticStyle:{"background-color":"white"}},[r("ve-bar",{staticClass:"barStyle",attrs:{data:t.areaData,"legend-visible":!1,colors:t.colors,settings:t.areaSettings,extend:t.areaExtend,grid:{containLabel:!0,right:40,top:30,left:60}}})],1)])],1)],1)},staticRenderFns:[]};var l=r("VU/8")(n,i,!1,function(t){r("7Udu")},"data-v-0834c8df",null);a.default=l.exports}});
//# sourceMappingURL=9.6a9fa23cad9b4a657b4d.js.map