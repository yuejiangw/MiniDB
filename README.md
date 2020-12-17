# MiniDB

### 1. 环境依赖

* IntelliJ IDEA 2020.2
* Java 11.0.8 2020-07-14 LTS



### 2. 运行方法

可以通过两种方式来运行此程序：

* 将整个项目以IntelliJ IDEA project的格式打开之后，直接运行`Main`即可。使用这种方法时，数据文件和命令行文件都应放在项目根目录下，数据文件的命名没有要求，但命令行文件的名字必须为"commands.txt"
* 若是想将输出定向到文件中，需要将目录切换至"MinDB/out/production/MiniDB"下。该目录下存有编译好的`class`文件，同时应该将数据文件和命令行文件复制到该目录下，命名要求同上。之后执行如下命令：

```java
cat commands.txt | java main > output.txt
```

​		则结果会被输出至同目录下的`output.txt`中，当然输出文件的名字可以是任意。



### 2. 指令集

* R := inputfromfile(filename)                       从文件中导入数据，建表
* R := select(S, CONDITION)                          按照CONDITION指定的条件选取表S中的行
* R := project(S, Clist)                                      对S中的Clist做投影
* R := sum(S, C1)                                             对C1中的元素加和
* R := avg(S, C1)                                               对C1中的元素取均值
* R := sumgroup(S, C1, Clist)                         基于S表中的Clist列进行分组，并求每组中C1列的sum
* R := avggroup(S, C1, Clist)                          基于S表中的Clist列进行分组，并求每组中C1列的avg
* T := join(R, S, JOIN_CONDITION)                按照JOIN_CONDITION对R和S两张表做笛卡尔积
* R := sort(S, C1)                                              按照C1对S表排序
* R := movavg(S, C1, k)                                   对S表中的C1列求步长为k的移动平均数
* R := movsum(S, C1, k)                                  对S表中的C1列求步长为k的移动加和
* Btree(R, C1)                                                   给R表中的C1列加上BTree索引
* Hash(R, C1)                                                   给R表中的C1列加上Hash索引
* T := concat(R, S)                                           连接R和S两张表
* outputtofile(R, bar)                                     输出R表中的内容，分隔符为bar



### 3. 进度

| 是否完成 |     指令      |
| :------: | :-----------: |
|    y     | inputfromfile |
|    y     |    select     |
|    y     |    project    |
|    y     |      sum      |
|    y     |      avg      |
|    y     |   sumgroup    |
|    y     |   avggroup    |
|    y     |     join      |
|    y     |     sort      |
|    y     |    movavg     |
|    y     |    movsum     |
|    y     |     Btree     |
|    y     |     Hash      |
|    y     |    concat     |
|    y     | outputtofile  |



### 4. 补充说明

The possible commands are all specified in the assignment and my subsequent announcement. If still unclear, here's a complete list. Below: COP means a comparison operation given in the assignment. R, S and T represent any table, C1 and C2 for a single column, Clist for a non-empty comma separated list of columns. In operations with a single input table (e.g., "select"), column names are always assumed to be from that table. In operations of two input tables (like "join"), column names are always prefixed with the table name they belong to, like, R.C1, S.C2. I also specify the requirement on the order of the rows after each operation. 

- R := inputfromfile(filename) // import vertical bar delimited file, first line has column headers (and each column is of type int). Create table R. The order of the rows in R is exactly like what is given in the file.
- R := select(S, CONDITION), where CONDITION takes the form of: C1 COP CONSTANT or C1 COP C2. The order of the rows in R is the same as those in S (of course, some of the rows are not selected and hence dropped).
- R := project(S, Clist), where Clist is a list of comma separated columns. The order of the rows of R is the same as that of S.
- R := sum(S, C1) : this is for "select sum(C1) from S;". This gives a single row table.
- R := avg(S, C1) : this is for "select avg(C1) from S;". This gives a single row table.
- R := sumgroup(S, C1, Clist), this is for "select Clist, sum(C1) from S group by Clist". The order of the rows in R can be arbitrary. 
- R := avggroup(S, C1, Clist), this is for "select Clist, avg(C1) from S group by Clist". The order of the rows in R can be arbitrary.4
- T := join(R, S, JOIN_CONDITION) , where JOIN_CONDITION is of the form R.C1 COP S.C2, where R.C1 is a column of R and S.C2 is a column of S. **(Note that columns in join condition are alwasy prefixed with the table they belong to.)** The order of the rows in R can be arbitrary. 
- R := sort(S, C1), this is to sort S by C1 in increasing order (you may assume always a single column sort: **this is another simplification**) 
- R := movavg(S, C1, k), this is to perform the k item moving average of S on column C1 (single column). The order of the rows in R is the same as that in S.
- R := movsum(S, C1, k), this is to perform the k item moving sum of S on column C1 (single column). The order of the rows in R is the same as that in S.
- Btree(R, C1), this is to create a B+tree index on R based on column C1.
- Hash(R, C1), this is to create a hash structure on on R based on column C1.
- T := concat(R, S), this is to concatenate the two tables R and S (R goes first, and S follows). 
- outputtofile(R, bar), this is to print out table R to standardout (in this case, "bar" is simply ingored). The printed rows should be in the same order as that in R.

