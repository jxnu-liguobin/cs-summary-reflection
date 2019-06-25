## 常见的Git操作

### 1、仓库从无到有

本地生成ssh秘钥，如果不嫌麻烦当然可以不设置，使用HTTPS即可

我的配置是Eclipse是HTTPS，使用GitHub，用来编写笔记，但是HTTPS需要记住密码，不然每次都需要填

IDEA使用SSH，用来工作提交代码，使用Gitlab。

ssh-keygen -t rsa -C "注册邮箱"

id_rsa.pub文件添加到Git的秘钥管理处，实际任何遵守Git协议的分布式版本控制基本流程基本是相同的，不必纠结用的是哪家产品。

如果是长期使用的电脑你可以配置全局

	git config --global user.name "账号名" 
	git config --global user.email 邮箱 
	git config --list 查看配置结果

当然你也可以只配置临时变量 去掉--global即可，因为我需要往牛客，码云，GitHub，Gitlab提交，所以这种是很有用的。因此我只需要修改提交的临时地址。

### 2、从本地开始

1. touch README.md 添加一个文件，用于注释、说明，在仓库的顶级目录下则会被显示作为该仓库或文件的说明。
2. git init  初始化本地仓库。
3. git add README.md 将上述文件添加到暂存区
4. git commit -m "first commit" 将暂存区的文件进行添加到本地分支。
5. git remote add origin ```https://github.com/jxnu-liguobin/SpringBoot-Seckill.git``` 添加远程分支，一般名称同本地分支，
已经存在则绑定本地与远程，该操作只能在init后执行，且只能执行一次。
6. git push -u origin master 提交本地到master主分支，默认本地master，实际开发时这里是开发的sprint分支，意为迭代。

上面的暂存区，分支，工作区的存储模型[借鉴自CyC2018](https://github.com/CyC2018/Interview-Notebook/blob/master/notes/Git.md)

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/git%20%E5%AD%98%E5%82%A8%E6%A8%A1%E5%9E%8B.png)

详细的Git流程

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/git-1.jpg)

### 3、从远程开始

当然你也可以从远处拉下一个项目，就像这样

git clone ```git@github.com:jxnu-liguobin/cs-summary-reflection.git``` 此处的连接可以是SSH也可以是HTTPS，取决于你的爱好，推荐SSH。

如果是别人的仓库则你需要有权限否则可以拉取，无法提交，权限是指你的公钥必须要被添加到远程仓库的秘钥管理中心，公司一般有统一管理SSH的平台。

如果你有权限，你可以在本地创建一个自己的仓库和一个开发分支，并将master合并到本地这个分支上来只需要：

git checkout -b newBrach origin/master  在origin/master的基础上创建newBrach分支，其中-b表示创建并切换到newBrach分支，origin/master表示源分支master。
需要注意的是，这种方法使得当前分支与远程的master分支相关联了，在IDEA提交的时候一不小心就会提交到master分支了。具体看下面“处理本地分支与远程绑定的分支不同名”部分

其次你还可以先创建一个本地分支 git checkout -b newBrach

再更新或合并 git merge master 表示使用master更新当前分支，因为是第一次创建所以是没有冲突的，实际如果存在冲突则需要解决冲突。

如果觉得Git不方便你可以使用图形化界面分支管理工具 Sourcetree。

你也可以更加暴力直接在本地某一个空分支下pull拉取 master。

接下来你只需push，如果没有配置默认的提交分支，则提交的时候还需声明提交分支。

像这样 git push -u  origin newBrach 如果你不填origin 则默认是master 。

你还可以强制提交

像这样 git push -u origin master --force 但并不建议使用，慎用。

### 4、其他分支处理命令

分支修改

1. git branch 分支名称 在本地新建一个分支
2. git checkout 分支名称 切换到你的新分支:
3. git push origin 分支名称 将新分支发布在github上
4. git branch -d 分支名称 在本地删除一个分支
5. git push origin :分支名称   (分支名前的冒号代表删除) 在github远程端删除一个分支

如果你需要直接使用git pull和git push，则你需要设置以下

1. git branch --set-upstream-to=origin/master master 
2. git branch --set-upstream-to=origin/分支名称 分支名称
3. git config --global push.default matching
以上建议忽略，实际开发这里每个月都需要修改开发的迭代分支，还不如别设置了。

Arcanist 命令

1. arc diff master --preview 创建diff语句
2. arc patch D11973 将pha上的revision变更patch到本地工作拷贝上
3. arc land --revision D10653 --onto master pha上评审完毕后，将本地分支做git push

### 5、解决提交错误

如果你是commit提交错误了你可以这样 ：

git reset 分支回滚到暂存区 （--hard 版本号，即回滚最后一次提交commit，--files）

如果你是add添加错了你可以这样：

git checkout 暂存区回滚到工作区（回滚最后一次add操作，--files） -b branch  在本地创建并切换到branch分支，前面以前提到到，-d是删除，切记

如果需要删除索引中的myfile文件但不删除文件本身，你可以：git rm --cached myfile 然后提交即可

PS:使用IDEA时，在未进行add时，执行IDEA提供的revert菜单也可以回退修改，还可以手动选择需要回退的文件，很方便。


### 6、git rebase 和 git merge的区别

* git pull = git fetch + git merge
* git pull --rebase = git fetch + git rebase 重点是不会产生新的commit请求。

远程跟踪分支已更新(Git术语叫做commit)，需要将这些更新取回本地，这时就要用到git fetch 上面已经提及到了。

如果你想让"mywork"分支历史看起来像没有经过任何合并一样（看不到合并的路径，不留提交痕迹），可以用 git rebase。

而 git merge的合并会出现痕迹，造成菱形依赖，看起来很困惑，所以推荐使用 git fetch + git rebase。

![](../../practice/picture/git%20rebase.png)

git rebase --abort参数来终止rebase的行动，并且"mywork" 分支会回到rebase开始前的状态。

多人提交自己的代码，必须先更新本地为最新，否则再次提交将会被拒绝，如果拉取下的代码和本地自己的有冲突则需要自己去解决了。

你可以这样切换分支： git checkout mywork

然后执行 ：git rebase origin

这些命令会把你的"mywork"分支里的每个提交（commit）取消掉，并且把它们临时保存为补丁（patch）（这些补丁放到".git/rebase"目录中），
然后把"mywork"分支更新到最新的"origin"分支，最后把保存的这些补丁应用到"mywork"分支上。
当'mywork'分支更新之后，它会指向这些新创建的提交(commit)，而那些老的提交会被丢弃。 如果运行垃圾收集命令(pruning garbage collection)， 
这些被丢弃的提交就会删除。（请查看 git gc）
 
 在rebase的过程中，也许会出现冲突（conflict）。 在这种情况，Git会停止rebase并会让你去解决 冲突；
在解决完冲突后，用"git-add"命令去更新这些内容的索引（index）， 然后，你无需执行 git-commit，你只要执行:

git rebase --continue  git 便会自动继续合并

如果你需要查看仓库修改状态， 你可以使用：git status   

你还可以查看git的日志： git log -p 

你可以查看对比两次文件内容具体修改了什么 ：git diff

更加具体的对比： git diff HEAD -- filename

可以查看工作区和版本库里面最新版本的区别	
		HEAD 表示当前版本，也就是最新的提交。上一个版本就是 ```HEAD^ ```，上上一个版本就是``` HEAD^^ ```，
往上100个版本写100个 “ ^ ” 比较容易数不过来，所以写成````` HEAD~100 `````。```HEAD~2``` 相当于 ```HEAD^^```


如果你需要取回远程的某些分支你可以这样： git fetch origin master 取回origin主机的master分支

如果你需要取回所有分支你可以这样： git fetch 

你只有将远程分支fetch下来后才可以checkout切换分支
	
	git checkout master     #//取出master版本的head。
	git checkout tag_name    #//在当前分支上 取出 tag_name 的版本
	git checkout  master file_name  #//放弃当前对文件file_name的修改
	git checkout  commit_id file_name  #//取文件file_name的 在commit_id的版本。
	#//其中commit_id为 git commit 时的sha值，每次提交都有唯一值。
	git checkout -- hello.rb #//这条命令把hello.rb从HEAD中签出。


如果你搞错了很多文件，可能需要恢复所有，你可以这样： git checkout .

这条命令把当前目录所有修改的文件 从HEAD中签出并且把它恢复成未修改时的样子。慎用

其他参数

* git rebase --abort 会回到rebase操作之前的状态，之前的提交的不会丢弃；
* git rebase --skip 则会将引起冲突的commits丢弃掉；
* git rebase --continue 用于修复冲突，提示开发者，一步一步地有没有解决冲突，fix conflicts and then run "git rebase --continue"


### 7、重要的mvn命令

* mvn clean deploy 清理并发布   
* mvn install -Dmaven.test.skip=true 跳过测试打包
* mvn dependency:tree 查看依赖树 IDE的依赖没有成功下载的时候，可以使用该命令进行查看，此命令将自动下载本地没有的依赖，
如果此处下载成功，则说明是本地仓库缓存问题，导致找不到依赖
* mvn clean compile -U  编译 该参数能强制让Maven检查所有SNAPSHOT依赖更新，确保集成基于最新的状态，如果没有该参数，
Maven默认以天为单位检查更新，而持续集成的频率应该比这高很多。

### 8、解决冲突的方式之一
	
	合并分支到本开发分支，或者git pull --rebase拉去下来
	查看冲突文件，并去除冲突提示注释和冲突部分代码
	执行git add 将修改完后的冲突文件添加进索引【stage 暂存区，位于工作区和当前分支之间】
	执行git rebase --continue 继续合并
 
### 9、处理本地分支与远程绑定的分支不同名

本地是branchA分支，提交代码时却是和远程的branchB分支绑定的，此时提交会更新branchB分支，但是希望直接更新branchA分支的代码。
经过测试，使用IDEA的checkout as 与 git checkout -b branchA origin/master命令是相同效果：导致branchA分支与远程master绑定。
但实际一般我们需要branchA与远程branchA绑定。此时可以解除绑定，执行：

解除关联库 git remote remove origin
            
重新关联库 git remote add origin sshUrl

提交本地分支至远程 git push origin branchA 

或者在push的时候在IDEA弹窗上方修改为origin/branchA，这个点击的时候需要自己输入至少一个首字母才会提示可选分支，不智能。
  
### 10、git revert 和 git reset

git reset 主要用法：

git reset (–mixed) ```HEAD~1 ```
回退一个版本，且会将暂存区的内容和本地已提交的内容全部恢复到未暂存的状态，不影响原来本地文件（未提交的也不受影响，更新 INDEX 索引区数据为当前 HEAD 所指提交对应的快照内容） 
git reset –soft ```HEAD~1 ```
回退一个版本，不清空暂存区，将已提交的内容恢复到暂存区，不影响原来本地的文件（未提交的也不受影响，不更新 INDEX 索引区） 
git reset –hard ```HEAD~1 ```
回退一个版本，清空暂存区，将已提交的内容的版本恢复到本地，本地的文件也将被恢复的版本替换（更新工作目录和索引一样）

必须注意，--hard 标记是 reset 命令唯一的危险用法，它也是 Git 会真正地销毁数据的仅有的几个操作之一。
其他任何形式的 reset 调用都可以轻松撤消，但是 --hard 选项不能，因为它强制覆盖了工作目录中的文件。
我们可以通过 reflog 来找回它。但是若该文件还未提交，Git 仍会覆盖它从而导致无法恢复。

git revert 主要用法：

    git revert HEAD                撤销前一次 commit
    git revert HEAD^               撤销前前一次 commit    
    git revert commit_id （比如:fa042ce57ebbe5bb9c8db709f719cec2c58ee7ff）

git revert是提交一个新的版本，将需要revert的版本的内容再反向修改回去，版本会递增，不影响之前提交的内容.
Tip : 通常情况下，上面这条revert命令会让程序员修改注释，这时候程序员应该标注revert的原因，假设程序员就想使用默认的注释，
可以在命令中加上-n或者--no-commit，应用这个参数会让revert 改动只限于程序员的本地仓库，而不自动进行commit，如果程序员想在revert之前进行更多的改动，
或者想要revert多个commit。

当有多个commit需要撤销，有可能是连续的：

使用该命令可以将提交撤回到commit_id_start的位置：git revert -n commit_id_start..commit_id_end

或是不连续的：git revert -n commit_id_1   git revert -n commit_id_3

git revert 和 git reset的主要区别：
    
    1.git revert是用一次新的commit来回滚之前的commit，git reset是直接删除指定的commit。 
    2.在回滚这一操作上看，效果差不多。但是在日后继续merge以前的老版本时有区别。因为git revert是用一次逆向的commit“中和”之前的提交，
    因此日后合并老的branch时，导致这部分改变不会再次出现，但是git reset是之间把某些commit在某个branch上删除，因而和老的branch再次merge时，
    这些被回滚的commit应该还会被引入。 
    3.git reset 是把HEAD向后移动了一下，而git revert是HEAD继续前进，只是新的commit的内容和要revert的内容正好相反，能够抵消要被revert的内容。          


### 11、git在MAC的terminator(命令行)下自动显示当前分支

1.修改的文件（任选其一）：.bash_profile、bashrc （全局 ~/.bash_profile）<br>
2.在最后一行新增以下代码：
```
function git_branch {
   branch="`git branch 2>/dev/null | grep "^\*" | sed -e "s/^\*\ //"`"
   if [ "${branch}" != "" ];then
       if [ "${branch}" = "(no branch)" ];then
           branch="(`git rev-parse --short HEAD`...)"
       fi
       echo " ($branch)"
   fi
}

export PS1='\u@\h \[\033[01;36m\]\W\[\033[01;32m\]$(git_branch)\[\033[00m\] \$ '

```
3.执行命令：source .bash_profile
 


欢迎指出提出建议和意见


参考:
[简书](https://www.jianshu.com/p/9299e32faa62) | [博客园](https://www.cnblogs.com/0616--ataozhijia/p/3709917.html) 
| [CSDN1](https://blog.csdn.net/leonliu06/article/details/79747301) | [CSDN2](https://blog.csdn.net/alphapersonality/article/details/80581730)
| [Cyc2018](https://github.com/CyC2018/Interview-Notebook/blob/master/notes/Git.md)