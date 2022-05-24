import {get,post} from "./http";

// 判断管理员是否登录成功
export const getLoginStatus = (params) => post(`admin/login/status`,params);

//============作家相关===============
//添加作家
export const setWriter = (params) => post(`writer/add`,params);
//查询作家
export const getAllWriter = () => get(`writer/allWriter`);
//编辑作家
export const updateWriter = (params) => post(`writer/update`,params);
//删除作家
export const delWriter = (id) => get(`writer/delete?id=${id}`);
//冻结作家账号
export const updateWriterState = (params) => post(`writer/update/state`, params);

//============书籍相关================
//根据作家id查询书籍
export const bookOfWriterId =(id) => get(`book/writer/detail?writerId=${id}`);
//添加书籍 
export const setBook = (params) => post(`book/add`,params);
//查询书籍
export const getAllBook = () => get(`book/allBook`);
//编辑书籍
export const updateBook = (params) => post(`book/update`,params);
//删除书籍
export const delBook = (id) => get(`book/delete?id=${id}`);
//根据书籍id查询书籍对象
export const bookOfBookId =(id) => get(`book/detail?bookId=${id}`); 

//============目录相关================
//根据书籍id查询目录
export const directoryOfBookId =(id) => get(`directory/book/detail?bookId=${id}`);
//添加目录
export const setDirectory = (params) => post(`directory/add`,params);
//查询目录
export const getAllDirectory = () => get(`directory/allDirectory`);
//编辑目录
export const updateDirectory = (params) => post(`directory/update`,params);
//删除目录
export const delDirectory = (id) => get(`directory/delete?id=${id}`);

//============用户相关================
//查询用户
export const getAllConsumer =() => get(`consumer/allConsumer`);
//添加用户
export const setConsumer = (params) => post(`consumer/add`,params);
//编辑用户
export const updateConsumer = (params) => post(`consumer/update`,params);
//冻结/解冻用户
export const updateConsumerState = (params) => post(`consumer/update/state`,params);
//删除用户
export const delConsumer = (id) => get(`consumer/delete?id=${id}`);
//根据用户id查询该用户的详细信息
export const getUserOfId =(id) => get(`/consumer/selectByPrimaryKey?id=${id}`);

//===============收藏===================
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`);
//删除用户收藏的歌曲
export const deleteCollection = (userId,bookId) => get(`collect/delete?userId=${userId}&bookId=${bookId}`);