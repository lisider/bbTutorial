## 应该怎么做

```c
source env-init
bitbake world
```


---



## 其他操作

```c

1/		查看依赖关系
bitbake -g aud-misc  

task-depends.dot  任务之间的依赖关系
package-depends.dot 运行时的目标依赖
pn-depends.dot 构建时的依赖

可以通过 xdot打开。sudo apt-get install xdot 
xdot task-depends.dot 查看

  
2/ 		显示所有recipes 的 版本信息
bitbake -s

3/		执行 recipe名里面的任务
bitbake -c 任务名 任务集合名 

4/ 		仅分析 recipe
bitbke world -p  

```

---

## bitbake 的文件

```c
	build/tmp/cache 
		在 执行 bitbake target -p 的时候生成,这里面生成的是 一些 解析 配置文件之后的 转储
	build/tmp/stamp 里面创建时间戳
		这里面是每个任务运行时候的时间戳.
		有些任务运行不会产生时间戳,是因为 这些任务被标记为"nostamp"

	build/conf/bblayers.conf 
		里面会有 BBLAYERS 变量 , 记录了  要被编译时的 图层

	每个图层的根目录 位置
		例如
		meta/meta-mediatek
		meta/poky/meta-poky

	每个图层的根目录 假设为 Layer

	${Layer}/classes
		里面有很多 bbclass 文件,主要记录了 方法
	${Layer}/conf
		里面有很多 .conf 文件 ,主要记录了 变量
		其中 layer.conf 中
			BBFILES 很重要 ,记录了 该图层 的 recipes-xxx 在哪里
			BBFILE_PRIORITY 很重要,  记录了 该图层的优先级
		其中bitbake.conf
			include require  了其他文件
	${Layer}/recipes-xxx
		里面有很多 .bb 和 .bbappend 文件,主要记录了 变量,及方法
		这里面 的 最底层目录会有 .bb 文件 和 .bbapend 文件
		如果有 相似的 ,但是不同版本的.bb文件,如果没有指定(查询 PREFERRED_VERSION_ ) ,默认用最新版本,
```



## bitbake 管理工程的方式

```c
解析的所有文件的分类
    .conf  .bbclass .bb

文件相互作用的方式是
    全局 bblayers.conf
    局部 layer.conf
依赖关系是
    .bbclass 与 .bbclass 之间   inherit
    .bb 与 .bbclass  之间       inherit
    .bb 与 .bb 之间             DEPENDS RDEPENDS
  	.bb 与 .bbapend 之间        共同合成一个recipe
  	.bb 与 .inc 之间            include require
```



## bitbake 的构建流程

```c
1/
	按下 bitbake  world
2/
	解析当前目录下面的 conf/bblayers.conf

3/
	根据 BBLAYERS 依次 查找对应的图层(优先级)
		解析 layer.conf
		解析 bitbake.conf

4/
	生成 cache 文件夹
		这时候已经解决了依赖什么之类的,任务顺序已经确定

5/
	执行任务
		( 每一个 .bb 文件是一个 任务的集合, .bbapend 是对该任务集合的补充
		例如 mtk-image-aud-8516 的 do_rootfs 方法就是一个任务 
		mtk-image-aud-8516 对应一个 .bb 文件 和 .bbapend文件
		)

```

---


## 概念2

```c

任务集合名 也叫一个 recipe
    
哪些是 layer ,哪些 是 bb 任务集合
xxx/yyy/zzz/recipes-aaa/bbb/first_0.1.bbappend
xxx/yyy/zzz/recipes-aaa/bbb/first_0.1.bb
xxx/yyy/zzz/recipes-aaa/bbb/first_0.1.inc
zzz 是 layer   bbb 是一个任务 , 

first_0.1.bbappend 写了 这个任务 中的几个过程或者全部过程
first_0.1.bb 写了 这个任务 中的几个过程或者全部过程
first_0.1.inc 写了 这个任务 中的几个过程或者全部过程 ,被 first_0.1.bb include
```


## 另外

```c
如果你修改了 meta/base/myconf/conf/bblayers.conf 里面的内容

则需要 删掉 build 目录,重新

. env-init 


例如 ,修改了 BBLAYERS 变量,然后 . env-init ,
发现 build/conf/bblayers.conf 里面的内容是不变的
只需要 删掉 build 目录 ,重新 .env-init



```
