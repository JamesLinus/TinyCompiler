TinyCompiler
============

TinyCompiler是一个TINY语言的翻译/编译器。它能够将TINY语言翻译成C语言的中间代码并调用gcc来编译生成最终的机器码。

Usage
=====
1. 编译 tiny.jar
2. 执行

        java -jar tiny.jar file
3. 输入中间代码文件、可执行文件、列表文件和语法树文件的文件名，或者留空使用默认值
4. 如果一切正常则会生成可执行文件，否则会输出错误信息

Developed By
============

* Noisyfox - <noisyfox@foxmail.com>

License
=======

    Copyright 2014 FoxTeam

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
