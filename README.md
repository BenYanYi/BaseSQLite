# BaseSQLite
SQLite封装
## 使用
### module 下添加

    compile 'com.yanyi.benyanyi:sqlitelib:1.0.0'
    
### 使用介绍
#### 初始化

     TableDao dao = new TableDao.Builder()
                .setVersion(your version)
                .builder(context);
                
#### 注解声明
* @TableBean &nbsp;声明当前类为表结构类，表名为默认为类名，设置value值可更改自定义表明
* @ID &nbsp;声明属性名为表主键id，默认id不自增，设置increase为true则自增
* @NotNull &nbsp;声明当前属性对应的表列值不能为空

#### 添加数据
your data可以为一条数据，也可以为数据集
    
    dao.where(your TableBean).(your condition)[可不选].operation().insert().find(your data);
    
    
#### 删除数据
可删除全部(findAll)，也可只删除第一条(findFirst)或最后一条(findLast)
   
    dao.where(your TableBean).(your condition)[可不选].operation().delete().findAll();
   
#### 查询数据
可查询所有(findAll)，也可查询第一条(findFirst)或最后一条(findLast)

    dao.where(your TableBean).(your condition)[可不选].operation().query().findAll()
    
#### 更改数据
可更改所有(findAll(your change data))，也可更改第一条(findFirst(your change data))或最后一条(findLast(your change data))
    
    dao.where(your TableBean).(your condition)[可不选].operation().update().findAll(your change data);
    
#### 条件属性
* eq(ConditionMsg) 声明当条件中的列为何值时满足条件
* notEq(ConditionMsg) 声明当条件中的列不为何值满足条件
* greater(ConditionMsg) 声明当条件中的列大于何值满足条件
* less(ConditionMsg) 声明当条件中的列小于何值满足条件
* in(ConditionMsg) 声明当条件中的列包含何值满足条件
* sort(String field,TableSort sort) 排序方式(field表示依据的列明)
#### 配置类介绍
* ConditionMsg(条件配置类)：field(列字段名)、value(当前列对应的值)、TableNexus(多个时用于当前条件是and还是or)
* TableNexus(多条件并且和或者)：DETAILS(默认值(and))、AND(and并且)、OR(or或者)
* TableSort(排序方式)：DETAILS(默认排序)、ASCENDING(正序)、DESCENDING(倒序)

### 更新记录
* 2019/04/03 提交1.0.0版本,第一次更新

#### 下一版本预计添加内容
* 自定义列明
* 添加有数据时修改，没数据时添加操作

<br/>
若在使用过程中出现什么问题，可以联系作者<br/>
作者：演绎<br/>
QQ：1541612424<br/>
email： work@yanyi.red<br/>
微信公众号：benyanyi(演绎未来)&nbsp;&nbsp;&nbsp;将会不定期的更新关于android的一些文章
    

        