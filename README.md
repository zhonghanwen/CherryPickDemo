> 在实际的项目开发中（使用Git版本控制），在所难免会遇到没有切换分支开发、需要在另一个分支修改bug然后合并到当前分支的情况。之前遇到这种第一反应就是将分支合并过去来解决问题。如果你那些提交当中也穿插了其他人的提交而且他们的提交不可以合并到另一个分支，那么使用分支的合并将明显变得困难。下面分享给大家一个非常好用Git的命令*Cherry-Pick*来处理这些情况，从而提高开发的效率。

### What is *Cherry-Pick* ?
git *Cherry-Pick*命令可以选择某一个分支中的一个或几个commit(s)来进行操作。你可以理解merge的个性定制版本，哈哈~~

### How use *Cherry-Pick* ？
假设我们有两个分支，一个是master分支，一个是从master分支Git Commit Log为“修改侧栏内容”新建出来的dev分支。

![master分支](http://7xrnko.com1.z0.glb.clouddn.com/git-cherry-pick/1.png)

![dev分支](http://7xrnko.com1.z0.glb.clouddn.com/git-cherry-pick/2.png) 

现在需要将master分支下的git Log commit "修改侧栏的点击跳转方式"合并到dev分支。也许你可能想到将这个Log上面的Logs先Revert，然后再将master分支合并到dev分支。下面记录一下怎么使用*Cherry-Pick*来合并一个或者多个提交。

- 先将当前分支切换到dev分支。

    ![切换到dev分支](http://7xrnko.com1.z0.glb.clouddn.com/git-cherry-pick/4.png)

- 打开master的所有提交Log,找到需要合并到dev分支的提交，这里我们找到“修改侧栏的点击跳转方式”这个提交，然后点击右击选择菜单弹出*Cherry-Pick*命令。

    ![](http://7xrnko.com1.z0.glb.clouddn.com/git-cherry-pick/8.png)

- 接着，IDE会弹出熟悉的提交提示框架，这时我们点击*Commit*或者*Commit and Push...*就可以将需要合并的提交合并到dev分支了。
这里我选择了*Commit and Push...*。

    ![](http://7xrnko.com1.z0.glb.clouddn.com/git-cherry-pick/6.png)

查看dev分支的Log可以看到那个提交已合并到dev。

    ![](http://7xrnko.com1.z0.glb.clouddn.com/git-cherry-pick/7.png)

### Note
当你需要将某些提交合并到另一分支的时候，**一定一定一定**要按提交的顺序进行合并，不然会导致某些文件发生冲突。这也是我实际项目中踩过的坑。

### End
1. 当你的需求还没有完成的时候，其他人应该切换到另一分支开发的时候，你可以先在当前分支继续开发完，然后再选择*Cherry-Pick*命令合并过去就可以了。
2. 当你需要将某个人的commits合并到另一开分时候，可以选择*Cherry-Pick*命令。（在实际的项目开发中，在所难免有人会提交错分支）
3. 当你切换到某条分支修改Bug后，需要将修改提交合并另一分支，可以选择*Cherry-Pick*命令。

---
这是一个非常好用、有趣的命令，它提高了我的开发效率~~在此，分享给大伙，希望可以帮忙到更多的人！
[点击关注我](https://github.com/zhonghanwen)

# License

    Copyright 2017 zhonghanwen
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
