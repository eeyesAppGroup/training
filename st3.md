## sublime text 3配置教程
---
sublime text 3 是一款非常性感而且强大的编辑器，虽然被誉为前端开发神器，但是其功能并非只在前端领域，对于简单地编辑代码以及查看代码来说ide太笨重了，sublime则是更好的选择。下面给大家讲一下怎么配置sublime text 3让它成为我们平时编辑和查看代码时的利器。

* 安装package-control
    ctrl+~打开控制台，复制粘贴下面的代码后回车即可安装package-control，
```python
import urllib.request,os; pf = 'Package Control.sublime-package'; ipp = sublime.installed_packages_path(); urllib.request.install_opener( urllib.request.build_opener( urllib.request.ProxyHandler()) ); open(os.path.join(ipp, pf), 'wb').write(urllib.request.urlopen( 'http://sublime.wbond.net/' + pf.replace(' ','%20')).read())
```

有了package-control之后安装插件就简单了。使用快捷键`Ctrl+Shift+P`然后输入install选择install packages然后输入要安装的插件名即可选择并安装。

---

以下插件自己选择需要的安装

* ConvertToUTF8：
GBK编码兼容，虽然在ST3中能输入中文并正常显示，但是打开含有中文字符的文件时，ST3会显示乱码。解决中文乱码问题，需要安装ConvertToUTF8插件。OS X或Linux还需要插件Codecs33。
* Terminal：
在当前文件所在位置打开终端。
* IMESupport：
中文输入法跟随光标
* sidebarenhancement
侧边栏增强工具
* AdvancedNewFile
快速新建文件，快捷键Ctrl+Shift+N
* BracketHighlighter
高亮显示配对括号以及当前光标所在区域。
* sublimeTmpl
快速新建文件模板
Default key bindings
```
ctrl+alt+h html
ctrl+alt+j javascript
ctrl+alt+c css
ctrl+alt+p php
ctrl+alt+r ruby
ctrl+alt+shift+p python
```
* MarkdownPreview
顾名思义，markdown文件预览
* markdown Extended
高亮markdown
* sublimeGit
在sublime中使用命令行的git插件

---

下面是一些重量级设置

* sublime中运行java程序：
在jdk的bin目录下新建runJava.bat，内容如下：
```
@echo off
cd %~dp1
echo Compiling %~nx1......
if exist %~n1.class (
del %~n1.class
)
javac -encoding UTF-8 %~nx1
if exist %~n1.class (
echo ------Output------
java %~n1
)
```
然后sublime->preferences->browser packages，在打开的文件夹中新建java文件夹，然后在java文件夹新建名为JavaC.sublime-build的文件，复制粘贴进下面的代码
```
{
  "shell_cmd": "runJava.bat \"$file\"",
  "file_regex": "^(...*?):([0-9]*):?([0-9]*)",
  "selector": "source.java",
  "encoding": "GBK"
}
```
保存。

之后编写的单个java文件就可以用`ctrl+shift+b`在sublime中运行了。但是有个问题就是不能接受输入。

其他的比如python和cpp文件运行的配置自行百度。（我早就忘了怎么配置的了）

* sublimelinter代码错误提示工具，作为一个编辑器没有错误提示总觉得不是很满足，所幸有这款插件。
下面以c++文件为例讲下怎么配置。先安装[cppcheck](http://cppcheck.net/)，将cppcheck安装路径添加到系统环境变量path中，比如我的是：`H:\Program Files\cppcheck`,然后重启电脑。
然后用前面的方法依次安装安装SublimeLinter和SublimeLinter-cppcheck，安装完成后通过菜单Tools -> SublimeLinter -> Mark Style，选择No Column Highlights Line。
完成以上设置后，错误代码即可突出显示，效果如图所示：

![](http://blog.bboylin.com/pic/2016-09-23_211115.png)

快捷键：按Ctrl+K，然后按a 显示所有错误

---

sublime一些快捷键（不需要全记住，选择一些常用的记住就好，可以提高效率）：

* f12: go to definition，快速查找函数和类的定义
* ctrl+f:查找
* 多重选词（重命名比较方便）：Ctrl + D选择当前光标所在的词并高亮该词所有出现的位置，再次Ctrl + D选择该词出现的下一个位置，在多重选词的过程中，使用Ctrl + K进行跳过，使用Ctrl + U进行回退，使用Esc退出多重编辑。
* 屏幕（Screen）
    * F11：切换普通全屏
    * Shift + F11：切换无干扰全屏
    * Alt + Shift + 2：进行左右分屏
    * Alt + Shift + 8：进行上下分屏
    * Alt + Shift + 5：进行上下左右分屏
    * 分屏之后，使用Ctrl + 数字键跳转到指定屏，使用Ctrl + Shift + 数字键将当前屏移动到指定屏
* 通用（General）
    * ↑↓←→：上下左右移动光标，注意不是不是KJHL！
    * Alt：调出菜单
    * Ctrl + Shift + P：调出命令板（Command Palette）
    * Ctrl + `：调出控制台
* 编辑（Editing）
    * Ctrl + Enter：在当前行下面新增一行然后跳至该行
    * Ctrl + Shift + Enter：在当前行上面增加一行并跳至该行
    * Ctrl + ←/→：进行逐词移动
    * Ctrl + Shift + ←/→进行逐词选择
    * Ctrl + ↑/↓移动当前显示区域
    * Ctrl + Shift + ↑/↓移动当前行
* 选择（Selecting）
    * Ctrl + D：选择当前光标所在的词并高亮该词所有出现的位置，再次Ctrl + D选择该词出现的下一个位置，在多重选词的过程中，使用Ctrl + K进行跳过，使用Ctrl + U进行回退，使用Esc退出多重编辑
    * Ctrl + Shift + L：将当前选中区域打散
    * Ctrl + J：把当前选中区域合并为一行
    * Ctrl + M：在起始括号和结尾括号间切换
    * Ctrl + Shift + M：快速选择括号间的内容
    * Ctrl + Shift + J：快速选择同缩进的内容
    * Ctrl + Shift + Space：快速选择当前作用域（Scope）的内容
* 窗口（Window）
    * Ctrl + Shift + N：创建一个新窗口
    * Ctrl + N：在当前窗口创建一个新标签
    * Ctrl + W：关闭当前标签，当窗口内没有标签时会关闭该窗口
    * Ctrl + Shift + T：恢复刚刚关闭的标签

---
写在最后的话：sublime虽然是一款不错的编辑器，也能直接运行一些小程序，但是还是建议正式开发时使用一款好的ide，比如java用eclipse，android用android studio，其他的编辑器比如vs code ， vim之类的反正自己用着爽就行。用什么工具不重要，效率最重要。
