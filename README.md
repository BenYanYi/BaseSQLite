# BaseSQLite

![](https://img.shields.io/badge/basesqlite-1.1.5-green)

介绍
---
##### 旨在将sqlite使用变得简单化，处理数据时链式调用
##### 注意1.1.1版本开始将只支持androidx项目
<!--##### [javadoc](http://benyanyi.gitee.io/basesqlite/)-->

使用
---
### 根目录下build.gradle添加Maven地址
~~~
repositories {
        maven {
            url "http://maven.benyanyi.com/nexus/content/repositories/mylove/"
        }
    }
~~~
### module 下添加
~~~
implementation 'com.yanyi.benyanyi:basesqlite:1.1.5'
~~~

或者
~~~
<dependency>
  <groupId>com.yanyi.benyanyi</groupId>
  <artifactId>basesqlite</artifactId>
  <version>1.1.5</version>
  <type>aar</type>
</dependency>
~~~

推荐一个查看数据库的开源库[Android-Debug-Database](https://github.com/amitshekhariitbhu/Android-Debug-Database)<br/>
### 使用介绍
#### 配置数据库信息
~~~
TableDaoImpl dao = new TableDao.Builder().setVersion(your version).setClasses(your table class).builder(context);
~~~
#### 配置表信息
~~~
TableSessionImpl<your table bean> session = dao.getSession(your TableBean.class);
~~~
#### 条件及逻辑处理
~~~
ConditionImpl<T> conditionImpl = session.where();//获取条件及逻辑处理类
conditionImpl = conditionImpl.sort(条件);//排序条件，可不选
OperationImpl<T> operation = conditionImpl.operation((your condition)[可不填])//获取逻辑处理类
~~~
##### 条件属性(your condition)
* sort(String field,TableSort sort) 排序方式(field表示依据的列明)

##### 配置类介绍
* ConditionMsg(条件配置类)：field(列字段名)、value(当前列对应的值)、Condition(条件判断)、TableNexus(多个时用于当前条件是and还是or)
* Condition(条件判断)：DETAILS(默认为eq相等)、EQ(相等)、NOT_EQ(不等)、GREATER(大于)、LESS(小于)、LIKE(包含)
* TableNexus(多条件并且和或者)：DETAILS(默认值(and))、AND(and并且)、OR(or或者)
* TableSort(排序方式)：DETAILS(默认排序)、ASCENDING(正序)、DESCENDING(倒序)

##### 添加数据(insert)
~~~
operation.insert().find(your data);
~~~
##### 删除数据(delete)
可删除全部(findAll)，也可只删除第一条(findFirst)或最后一条(findLast)
~~~
operation.delete().findAll();
~~~
##### 查询数据(query)
可查询所有(findAll)，也可查询第一条(findFirst)或最后一条(findLast)
~~~
operation.query().findAll()
~~~
##### 更改数据(update)
可更改所有(findAll(your change data))，也可更改第一条(findFirst(your change data))或最后一条(findLast(your change data))
~~~
operation.update().findAll(your change data);
~~~
##### 修改或者添加(changeOrAdd),必须设置your condition
可在不确定数据是否存在时修改数据，数据存在则进行修改，数据不存在是则进行添加,可处理所有数据(findAll(your changeOrAdd data)),也可处理第一条数据(findFirst(your changeOrAdd data))或最后一条数据(findLast(your changeOrAdd data))
~~~
operation.changeOrAdd().findAll(your changeOrAdd data);
~~~

#### 注解声明
* @TableBean &nbsp;声明当前类为表结构类，表名为默认为类名，设置value值可更改自定义表明。<!--<br/><font color=#ff0000>**特别注意，使用TableBean注解的类中属性需要添加set和get方法**</font>-->
* @ID &nbsp;声明属性名为表主键id，默认id不自增，设置increase为true则自增
* @NotNull &nbsp;声明当前属性对应的表列值不能为空
* @ColumnName &nbsp;声明表中列名，列名为空时使用变量名，不能为null
* @NotColumn &nbsp;声明当前变量不设置能列，默认使用为true值（不为列），设置notColumn值变更是否不为列


#### 回调类介绍
##### TableSessionImpl<T>(数据库操作类）
* ConditionCallBack<T> where();返回条件处理类，用于设置增删查改前的条件添加，会出现条件缓存的问题，除非重新获取TableSessionCallBack
* ConditionCallBack<T> where(boolean isCleanCondition);返回条件处理类，用于设置增删查改前的条件添加，isCleanCondition(是否清空缓存的条件)
* SQLiteDatabase getSQLiteDatabase();返回数据库操作base
* boolean tableIsExist(String tableName);判断某张表是否存在
* String getDBPath();获取数据库路径
* void close();关闭数据库

##### OperationImpl<T>(增删查改类)
* cleanCondition() 清除条件(包括缓存)
* insert() 插入数据
* delete() 删除数据
* query() 查询数据
* update() 修改数据
* changeOrAdd() 根据条件修改数据，如果没有则添加数据

更新记录
----
* 2020-08-07 提交1.1.5版本，使表在TableDao的初始化阶段即完成创建升级操作
* 2020-06-04 提交1.1.4版本，修改条件属性方法，将多个方法统一调用，删除是否要清除缓存判断及方法，不会产生缓存条件
* 2020-05-18 提交1.1.3版本，添加创建多表方法
* 2020-05-16 提交1.1.2版本，修复1.1.1版本中内部类问题
* 2020-05-15 提交1.1.1版本，修复条件缓存问题，添加是否要清除缓存判断及方法,将android support转成androidx支持,并将Maven库存放到个人服务器上
* 2020-01-10 提交1.1.0版本，优化代码，修复个别问题(该版本被误删)
* 2019-12-20 提交1.0.8版本，修复条件问题
* 2019-12-03 提交1.0.7版本，修复修改数据时报缺少set和get方法问题
* 2019-06-26 提交1.0.5版本，删除重复方法
* 2019-06-12 提交1.0.4版本，优化方法，更改包名，用于统一开源库包名
* 2019-05-20 提交1.0.3版本，优化数据处理方法，添加获取数据库地址方法，添加获取SQLiteDatabase方法，添加数据库关闭方法，添加判断某张表是否存在方法,使用TableBean注解的类中变量不再强制需要添加set和get方法
* 2019-04-04 提交1.0.1版本，添加有数据时修改，没数据时添加操作
* 2019-04-03 提交1.0.0版本，第一次更新

<!--#### 下一版本预计添加内容-->
<!--优化条件方法，将条件方法全部归入一个方法中-->

<!--## 联系-->
<!--若在使用过程中出现什么问题，可以联系作者<br/>-->
<!--作者：演绎<br/>-->
<!--QQ：1541612424<br/>-->
<!--email： work@yanyi.red<br/>-->
<!--微信公众号：benyanyi(演绎未来)&nbsp;&nbsp;&nbsp;将会不定期的更新关于android的一些文章-->

<!--生成javadoc 防止混淆 加入  -encoding utf-8 -charset utf-8-->
    

        