import Vue from 'vue'
import App from './App'
import router from './router/index'
import store from './store/index'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/main.css'
import 'babel-polyfill'
import VCharts from 'v-charts'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n)    //中英文切换
Vue.use(ElementUI)
Vue.use(VCharts)

const i18n = new VueI18n({
  locale: 'CN',//语言标识，中文
  messages:{
    /* 英文 */
    US: {
      // 公共
      common:{
        search: 'Filter keywords',
        t1: 'Reader gender ratio',
        t2: 'Distribution of Book Types',
        t3: 'Sex ratio of writers',
        t4: 'Nationality Distribution of Writers',
        t5: 'Reader age group ratio',
        t6: 'Age distribution and ranking of writers',
        consumerCount: 'Total number of Reader',
        bookCount: 'The total number of books',
        writerCount: 'The total number of authors',
        batchDel:'  Batch delete',
        confirm: 'Confirm', // 确定按钮
        cancel: 'Cancel', // 取消按钮
        operation: 'Operation',//操作
        updatePic: 'updatePic',//更新图片
        delText: 'Unrecoverable after deletion. Are you sure to delete?',
        freezeText: 'The account will be frozen. Are you sure you want to continue?',
        thawText: 'The account will be unfrozen. Are you sure you want to continue?',
        sex: 'gender',
        woman: 'Woman',
        man: 'Man',
        combination: 'Combination',
        unknown: 'Unknown',
        birth: 'Birth',
        selectDate: 'Select date',
        location: 'location',
        introduction: 'Introduction',
        style: 'Style',
        updatePic:'Update pic',
        isCharge: 'Charge',
        phone: 'Phone number',
        email: 'Email',
        signature:'Signature',
        pwd: 'password',
        state: 'state'
      },
      // 登录页面
      login:{
        loginTxt: 'login',
        username: 'please enter user name',
        password: 'Please enter password',
      },
      // 头部
      header:{
        manageName: 'Happy·Read Background management',
        signOut: 'sign out',
      },
      // 左侧导航
      leftNav:{
        Info: 'home',
        Consumer: 'reader ',
        Writer: 'writer ',
        Classify: 'classify'
      },
      // 作家管理页面
      writer:{
        enterName: 'Please enter author name search',
        addWriter: 'Add the writer',
        editWriter: 'Editor the writer',
        delWriter: 'Delete the writer',
        writerName: 'Writer name',
        pic:'Writer picture',
      },
      // 书籍管理页面
      book:{
        enterName: 'Please enter the book name search',
        add: 'Add the book',
        edit: 'Editor the book',
        del: 'Delete the book',
        Name: 'Book name',
        pic:'Book picture',
      },
      // 目录管理页面
      directory:{
        enterName: 'Please enter directory name search',
        add: 'Add the directory',
        edit: 'Editor the directory',
        del: 'Delete the directory',
        Name: 'Directory name',
        content: 'Content',
      },
      // 用户管理页面
      user:{
        enterName: 'Please enter the reader name search',
        add: 'Add the reader',
        edit: 'Editor the reader',
        del: 'Delete the reader',
        freeze: 'Frozen the user',
        thaw: 'Unfrozen the user',
        Name: 'User name',
        pic:'Reader picture',
      },
    },
    /* 简体 */
    CN:{
      // 公共
      common:{
        search: '筛选关键词',
        t1: '读者性别比例',
        t2: '书籍类型分布',
        t3: '作家性别比例',
        t4: '作家地区分布',
        t5: '读者年龄段比例',
        t6: '作家年龄段分布及排序',
        consumerCount: '读者总数',
        bookCount: '书籍总数',
        writerCount: '作家总数',
        batchDel:'批量删除',
        confirm: '确定', // 确定按钮
        cancel: '取消', // 取消按钮
        operation: '操作',
        updatePic:'更新图片',
        delText: '删除后不可恢复，是否确定删除？',
        freezeText: '用户账号将被冻结，是否确定继续？',
        thawText: '用户账号将解冻，是否确定继续？',
        sex: '性别',
        woman: '女',
        man: '男',
        combination: '组合',
        unknown: '不明',
        birth: '生日',
        selectDate: '选择日期',
        location: '地区',
        style: '风格',
        introduction: '简介',
        isCharge: '是否收费',
        phone: '电话号码',
        email: '电子邮箱',
        signature:'签名',
        pwd: '密码',
        state: '状态'
      },
      // 登录页面
      login:{
        loginTxt: '登录',
        username: '请输入用户名',
        password: '请输入密码',
      },
      // 头部
      header:{
        manageName: '悦·读后台管理',
        signOut: '退出登录',
      },
      // 左侧导航
      leftNav:{
        Info: '系统首页',
        Consumer: '读者管理',
        Writer: '作家管理',
        Classify: '分类管理'
      },
      // 作家管理页面
      writer:{
        enterName: '请输入作家名搜索',
        addWriter: '添加作家',
        editWriter: '编辑作家',
        delWriter: '删除作家',
        writerName: '作家名',
        pic:'作家图片',
      },
      // 书籍管理页面
      book:{
        enterName: '请输入书籍名搜索',
        add: '添加书籍',
        edit: '编辑书籍',
        del: '删除书籍',
        Name: '书籍名',
        pic:'书籍图片',
      },
      // 目录管理页面
      directory:{
        enterName: '请输入目录名搜索',
        add: '添加目录',
        edit: '编辑目录',
        del: '删除目录',
        Name: '目录名',
        content: '内容',
      },
      // 用户管理页面
      user:{
        enterName: '请输入读者名搜索',
        add: '添加读者',
        edit: '编辑读者',
        del: '删除读者',
        freeze: '冻结用户',
        thaw: '解冻用户',
        Name: '用户名',
        pic:'读者图片',
      },
    },
    /* 繁体 */
    HK:{
      // 公共
      common:{
        search: '篩選關鍵詞',
        t1: '讀者性別比例',
        t2: '書籍類型分布',
        t3: '作家性別比例',
        t4: '作家地區分布',
        t5: '用戶年齡段比例',
        t6: '作家年齡段分布及排序',
        consumerCount: '讀者總數',
        bookCount: '書籍總數',
        writerCount: '作家總數',
        batchDel:'  批量刪除',
        confirm: '確定', // 确定按钮
        cancel: '取消', // 取消按钮
        operation: '操作',
        updatePic: '更新圖片',//更新图片
        delText: '刪除後不可恢復，是否確定刪除？',
        freezeText: '用戶賬號將被凍結，是否確定繼續?',
        thawText: '用戶賬號將被解凍，是否確定繼續?',
        sex: '性別',
        woman: '女',
        man: '男',
        combination: '組合',
        unknown: '不明',
        birth: '生日',
        selectDate: '選擇日期',
        location: '地區',
        introduction: '簡介',
        style: '風格',
        updatePic:'更新圖片',
        isCharge: '是否收費',
        phone: '電話號碼',
        email: '電子郵箱',
        signature:'簽名',
        pwd: '密碼',
        state: '狀態'
      },
      // 登录页面
      login:{
        loginTxt: '登錄',
        username: '請輸入用戶名',
        password: '請輸入密碼',
      },
      // 头部
      header:{
        manageName: '悅·讀後臺管理',
        signOut: '退出登錄',
      },
      // 左侧导航
      leftNav:{
        Info: '系統首頁',
        Consumer: '讀者管理',
        Writer: '作家管理',
        Classify: '分类管理'
      },
      // 作家管理页面
      writer:{
        enterName: '請輸入作家名搜索',
        addWriter: '添加作家',
        editWriter: '編輯作家',
        delWriter: '刪除作家',
        writerName: '作家名',
        pic:'作家圖片',
      },
      // 书籍管理页面
      book:{
        enterName: '請輸入書籍名搜索',
        add: '添加書籍',
        edit: '編輯書籍',
        del: '刪除書籍',
        Name: '書籍名',
        pic:'書籍圖片',
      },
      // 目录管理页面
      directory:{
        enterName: '請輸入目錄名搜索',
        add: '添加目錄',
        edit: '编辑目錄',
        del: '删除目錄',
        Name: '目錄名',
        content: '内容',
      },
      // 用户管理页面
      user:{
        enterName: '請輸入讀者名搜索',
        add: '添加讀者',
        edit: '編輯讀者',
        del: '删除讀者',
        freeze: '凍結用戶',
        thaw: '解凍用戶',
        Name: '用戶名',
        pic:'讀者圖片',
      },

    },
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)
})
