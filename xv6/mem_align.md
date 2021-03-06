预备知识：基本类型占用字节
　　在32位操作系统和64位操作系统上，基本数据类型分别占多少字节呢？

　　32位操作系统：

　　char : 1    int ：4    short : 2    unsigned int : 4    long : 4    unsigned long : 4    long long : 8     float : 4    double : 8    指针 : 4

　　64位操作系统

　　char : 1    int ：4    short : 2    unsigned int : 4    long : 8    unsigned long : 8    long long : 8     float : 4    double : 8    指针 : 8

内存对齐：
　　成员对齐有一个重要的条件，即每个成员按自己的方式对齐。其对齐的规则是：每个成员按其类型的对齐参数(通常是这个类型的大小)和指定对齐参数(这里默认是8字节)中较小的一个对齐。并且结构的长度必须为所用过的所有对齐参数的整数倍。不够就补空字节。

　　举例：

复制代码
struct t{
    long a;
    short b;
    int c;
    int *d; 
    char e;
}
复制代码
 

　　在64位操作系统中的大小。

　　分析：

　　　　按照声明的顺序一个一个分配内存空间。

　　　　首先 long 型变量a，在64位地址空间中，long型占8个字节，所以按照上面的对齐条件，这个成员应该按照对其参数 min(sizeof(long), 8) = 8字节来对齐，所以把这个成员存放在 0~7 内存单元中。

　　　　然后 short型变量b，在64位地址空间中，short型占2个字节，所以按照上面的对齐条件，这个成员应该按照对其参数 min(sizeof(short), 8) = 2字节来对齐，所以把这个成员存放在 8~9 内存单元中。

　　　　然后 int型变量c，在64位地址空间中，int型占4个字节，所以按照上面的对齐条件，这个成员应该按照对其参数 min(sizeof(int), 8) = 4字节来对齐，所以把这个成员存放在 12~15 内存单元中（10，11单元都不能被4整除）。

　　　　然后 int*型变量d，在64位地址空间中，指针型占8个字节，所以按照上面的对齐条件，这个成员应该按照对其参数 min(sizeof(int*), 8) = 8字节来对齐，所以把这个成员存放在 16~23 内存单元中。

　　　　然后 char型变量e，在64位地址空间中，char型占1个字节，所以按照上面的对齐条件，这个成员应该按照对其参数 min(sizeof(char), 8) = 1字节来对齐，所以把这个成员存放在 24 内存单元中。

　　　　然后整个结构体的长度必须为所有对齐参数的整数倍，当前长度为25，不是所有对齐参数整数倍，必须调整为32，才是所有参数整数倍。

　　　　所以这个结构体的长度为32。

　　

　　如果结构体中出现子结构体怎么办？我们在确定子结构体的对齐参数时，应该就是它的所有成员使用的对齐参数中最大的一个。

　　举例：

复制代码
struct t{
    char a;
    int b;
};
struct s{
   char c;
   struct t d;
   char e;
};
复制代码
　　在32位操作系统下的长度。

　　首先确定t的大小为8，它的所有成员使用的对齐参数最大为4。

　　再考察s：

　　　　首先 char 型变量c，在32位地址空间中，char型占1个字节，所以按照上面的对齐条件，这个成员应该按照对其参数 min(sizeof(char), 8) = 1字节来对齐，所以把这个成员存放在 0 内存单元中。

　　　　然后 struct t型变量d，在32位地址空间中，struct t占8个字节，所以按照上面的对齐条件，这个成员应该按照对其参数 4 字节来对齐，所以把这个成员存放在 4~11 内存单元中。

　　　　然后 char型变量e，在32位地址空间中，char型占1个字节，所以按照上面的对齐条件，这个成员应该按照对其参数 min(sizeof(char), 8) = 1字节来对齐，所以把这个成员存放在 12 内存单元中。

　　　　然后整个结构体的长度必须为所有对齐参数的整数倍，当前长度为13，不是所有对齐参数整数倍，必须调整为16，才是所有参数整数倍。

　　所以此结构体大小为16.