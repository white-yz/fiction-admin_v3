import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import MyBook from '@/pages/MyBook'
import Writer from '@/pages/Writer'
import BookList from '@/pages/BookList'
import VipBookList from '@/pages/VipBookList'
import Search from '@/pages/Search'
import Book from '@/pages/Book'
import Content from '@/pages/Content'
import LoginIn from '@/pages/LoginIn'
import SignUp from '@/pages/SignUp'
import Setting from '@/pages/Setting'
import WriterAlbum from '@/pages/WriterAlbum'
import Agreement from '@/pages/Agreement'
import WriteBook from '@/pages/WriteBook'


Vue.use(Router)


const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}


export default new Router({
  routes: [    
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/my-book',
      name: 'my-book',
      component: MyBook
    },
    {
      path: '/writer',
      name: 'writer',
      component: Writer
    },
    {
      path: '/book-list',
      name: 'book-list',
      component: BookList
    },
    {
      path: '/vip-book-list',
      name: 'vip-book-list',
      component: VipBookList
    },
    {
      path: '/search',
      name: 'search',
      component: Search
    },
    {
      path: '/book',
      name: 'book',
      component: Book
    },
    {
      path: '/content',
      name: 'content',
      component: Content
    },
    {
        path: '/write-book',
        name: 'book',
        component: WriteBook
      },
    {
      path: '/sign-up',
      name: 'sign-up',
      component: SignUp
    },
    {
      path: '/login-in',
      name: 'login-in',
      component: LoginIn
    },
    {
      path: '/setting',
      name: 'setting',
      component: Setting
    },
    {
      path: '/writer-album',
      name: 'writer-album',
      component: WriterAlbum
    },
    {
      path: '/agreement',
      name: 'agreement',
      component: Agreement
    },
    
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
