#### Android 6.0 动态权限申请

##### 现状：

[九月Android版本分布图公布：朝Android Nougat靠拢](http://www.cnbeta.com/articles/tech/651119.htm)



##### 动态权限申请：

###### 单个权限：

动态申请的权限必须在 manifest 中声明，否则请求时系统直接返回未授权结果（不与用户交互）。

###### [权限组](https://developer.android.com/guide/topics/security/permissions.html?hl=zh-cn#perm-groups)：

以同属于 STORAGE 权限组的 READ_EXTERNAL_STORAGE 和 WRITE_EXTERNAL_STORAGE （以下简称读权限和写权限）两个权限举例来说：

- 常规情况：manifest 文件中**同时声明**了 READ_EXTERNAL_STORAGE 和 WRITE_EXTERNAL_STORAGE
  1. 运行时向用户请求**读权限**，如果获得授权，则属于同一权限组的**写权限**会自动获得授权，直接检查其状态为 PERMISSION_GRANTED
  2. 但官方表示权限组的内容可能会有变动，因此在使用时不能做**某几个权限一直属于同一权限组**的假设。也就是**当请求读权限并获得授权时，仍然需要判断写权限的状态并决定是否需要请求，不可直接使用写权限。**
- 非常规情况：manifest 文件中**只声明**了读权限（READ_EXTERNAL_STORAGE）
  1. 运行时向用户请求**读权限**并获得授权，当再次请求**写权限（没有在 manifest 中声明）**时，在 onRequestPermissionsResult() 回调中直接收到未授权的结果（不与用户交互）。
  2. 在 manifest 中**增加写权限的声明**并覆盖安装后，写权限的状态依然为未授权，需要重新请求。当调用 ActivityCompat.requestPermissions() 时会弹出对话框由用户决定是否授予权限。
  3. 当再次从 manifest 中移除写权限的声明后，写权限的状态再次回到未授权状态。这也证明**“动态申请的权限必须在 manifest 中声明”**。



##### 实现方式对比：

| 实现方式                                     | 优点                                      | 缺点                                       |
| ---------------------------------------- | --------------------------------------- | ---------------------------------------- |
| 系统原生 API                                 | 不用引入其他库                                 | 1. 只能在 Activity 的 onRequestPermissionsResult() 系统回调中接收权限申请的结果；2. 大量的重复处理请求结果的代码 |
| 自定义 Listener 的方式请求权限（checkPermission(listener, desc, mPermissions)） | 接收请求结果的方法不限于系统回调                        | 增加大量的回调代码，可读性较差                          |
| 注解方式                                     | 1. 接收请求结果的方法不限于系统回调；2. 简化大量的处理请求结果的模版代码 | -                                        |

综上，选择注解方式来实现是较优的做法。



##### 参考：

[在运行时请求权限](https://developer.android.com/training/permissions/requesting.html?hl=zh-cn)

[正常权限和危险权限](https://developer.android.com/guide/topics/security/permissions.html?hl=zh-cn#normal-dangerous)

[Android 6.0权限管理及最佳实践](http://www.jianshu.com/p/cdcbd3038902)

[Android 6.0 - 动态权限管理的解决方案](http://www.jianshu.com/p/dbe4d37731e6)

[聊一聊Android 6.0的运行时权限](http://droidyue.com/blog/2016/01/17/understanding-marshmallow-runtime-permission/)

[Android 开发者必知必会的权限管理知识](https://mp.weixin.qq.com/s/OQRHEufCUXBA3d3DMZXMKQ)



##### GitHub Library：

[HeiPermission](https://github.com/forJrking/HeiPermission)

[easypermissions](https://github.com/googlesamples/easypermissions)

[PermissionsDispatcher](https://github.com/permissions-dispatcher/PermissionsDispatcher)

[AndPermission](https://github.com/yanzhenjie/AndPermission)

