如何成为 Collaborator
---

[English](./CONTRIBUTING-en.md)

本库没有的语言，自己提PR新建子模块，参考`python-leetcode`新建模块，该操作不允许直接push，请尽可能的支持 `GitHub action workflows`（不会就忽略）

- 在子模块下每个人为自己建一个package，用以区分不同人。第一次以PR形式提交，merge后你将获得Collaborator权限
- 在子模块下每个人在自己package中新建一个`packageName.md`，增加题目索引再链接到当前项目的README.md

如果你要创建新的语言的子模块，应该以下面的python-leetcode项目为例

```
python-leetcode  当然，前面可能还有通用包名io.github.xxx
    /laozhang             每个人的包
       /leetcode_12.py    每个人刷的题，这里还能创建自己的子包，区分专题
       /laozhang.md       每个人的题目索引
    /dreamylost
       /leetcode_12.py
       /dreamylost.md
    README.md             聚合每个人的.md文件
```

将`packageName.md`这么链接到python-leetcode的`README.md`中

```
[laozhang](./laozhang/laozhang.md)
[dreamylost](./dreamylost/dreamylost.md)
```

## 要求

* 使用非脚本语言时，必须本地编译通过，JVM平台统一使用`gradle spotlessApply`格式化代码，脚本语言不作要求
* 没有通过GitHub action workflows的代码，请立刻回滚！（目前本库只支持对JVM语言和Rust的检查）
* 没有通过sonarcloud的，请立刻回滚！
* 没有通过lgtm的，请抽空解决即可
* 解决冲突: 只允许append不能delete
* 默认Java代码的版权是: `all Collaborators`