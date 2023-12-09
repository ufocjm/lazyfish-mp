# Mybatis-Plus 个性化

### 简介

> 在日常工作中，很多时候使用mybatis-plus的时候都是单表操作，总有业务需求需要联表查询。经常性的需要写sql，干脆直接封装起来，使用lambda语法进行使用。



## 使用方式

```xml
<dependency>
	<groupId>fun.nibaba</groupId>
	<artifactId>lazyfish-mp-join</artifactId>
  <version>0.0.4-SNAPSHOT</version>
</dependency>
```





## 计划

- [x] 联表查询sql构建器 v0.0.3-SNAPSHOT
- [x] join on 支持嵌套 v0.0.4-SNAPSHOT
- [x] 支持函数查询字段
- [x] 删除对hutool工具包的依赖



## 例子

联表查询:

```java
    Wrappers.<User>lambdaQuery().select(User::getId, User::getAge);
    LazyTable<User> user = new LazyTable<>(User.class, "yayaya");
    LazyTable<UserChild> userChild = new LazyTable<>(UserChild.class, "lueluelue");
    LazyWrapper build = LazyWrapper.builder(user)
        .select(lazySelect -> lazySelect.select(User::getId, User::getAge, User::getTitle).count(User::getId,"lalala"))
        .leftJoin(user, userChild, lazyJoin -> {
            lazyJoin.select(lazySelect -> {
                lazySelect.select(UserChild::getId, "hahahahah");
            }).on(lazyOn -> {
                lazyOn.eq(User::getAge, UserChild::getTitle);
                lazyOn.eq(User::getAge, UserChild::getTitle);
            }).where(lazyWhere -> {
                lazyWhere.ne(UserChild::getEmail, "112");
            });
        })
        .where(lazyWhere ->
                lazyWhere.eq(User::getId, "1")
                    .and(subLazyWhere -> subLazyWhere.eq(User::getAge, "3"))
                        .in(User::getId, Lists.newArrayList("1", "2", "3"))
                        .in(User::getId, Lists.newArrayList("1"))
                    .notIn(User::getId, Lists.newArrayList("1", "2", "3"))
                    .notIn(User::getId, Lists.newArrayList("3"))
                )
        .group(lazyGroup -> lazyGroup.groupBy(User::getTitle).groupBy(userChild, UserChild::getId))
        .order(lazyGroup -> lazyGroup.orderByDesc(User::getCreateId).orderByDesc(userChild, UserChild::getEmail))
        .build();
    List<User> list = userService.joinList(build, User.class);
```

